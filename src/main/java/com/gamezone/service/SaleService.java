package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Client;
import com.gamezone.model.Console;
import com.gamezone.model.Person;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.persistence.SaleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for managing sales.
 */
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final PersonService personService;
    private final AccessoryService accessoryService;
    private final PromotionService promotionService;
    private final WarrantyService warrantyService;

    /**
     * Constructs a SaleService with the required dependencies.
     *
     * @param saleRepository repository for managing sales
     * @param productService service for managing products
     * @param personService service for managing persons
     * @param accessoryService service for managing accessories
     * @param promotionService service for managing promotions
     * @param warrantyService service for managing product warranties
     */
    public SaleService(
            SaleRepository saleRepository,
            ProductService productService,
            PersonService personService,
            AccessoryService accessoryService,
            PromotionService promotionService,
            WarrantyService warrantyService
    ) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registers a new sale after validating the required information.
     *
     * @param id unique identifier of the sale
     * @param date date of the sale
     * @param clientId identifier of the client
     * @param sellerId identifier of the seller
     * @param products products and accessories included in the sale
     * @param productIdsWithExtendedWarranty product IDs selected for extended warranty
     * @return the registered sale
     */
    public Sale registerSale(
            String id,
            String date,
            String clientId,
            String sellerId,
            List<Product> products,
            List<String> productIdsWithExtendedWarranty
    ) {

        /*
         * 1. Validate that the sale contains at least one item.
         */
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException(
                    "La venta debe contener al menos un producto."
            );
        }

        Person clientPerson = personService.findPerson(clientId);

        if (!(clientPerson instanceof Client)) {
            throw new IllegalArgumentException(
                    "Cliente no encontrado con ID: " + clientId
            );
        }

        Person sellerPerson = personService.findPerson(sellerId);

        if (!(sellerPerson instanceof Seller)) {
            throw new IllegalArgumentException(
                    "Vendedor no encontrado con ID: " + sellerId
            );
        }

        Client client = (Client) clientPerson;
        Seller seller = (Seller) sellerPerson;

        /*
         * 2. Resolve every item and validate its stock.
         */
        for (Product product : products) {

            if (product instanceof Accessory) {

                Accessory accessory =
                        accessoryService.findById(product.getId());

                if (accessory == null) {
                    throw new IllegalArgumentException(
                            "Accesorio no encontrado con ID: "
                                    + product.getId()
                    );
                }

                if (accessory.getStockQuantity() <= 0) {
                    throw new IllegalStateException(
                            "Stock insuficiente para el accesorio: "
                                    + accessory.getTitle()
                    );
                }

            } else {

                Product storedProduct =
                        productService.findProduct(product.getId());

                if (storedProduct == null) {
                    throw new IllegalArgumentException(
                            "Producto no encontrado con ID: "
                                    + product.getId()
                    );
                }

                if (storedProduct.getStockQuantity() <= 0) {
                    throw new IllegalStateException(
                            "Stock insuficiente para el producto: "
                                    + storedProduct.getTitle()
                    );
                }
            }
        }

        /*
         * 3. Create the sale and calculate the subtotal.
         */
        Sale sale = new Sale(
                id,
                date,
                client,
                seller,
                products
        );

        double subtotal = sale.calculateTotal();

        /*
         * 4. Find the best promotion and calculate the discount.
         */
        Promotion bestPromotion =
                promotionService.findBestPromotionFor(sale);

        double discount = 0.0;

        if (bestPromotion != null) {
            discount = bestPromotion.calculateDiscount(sale);

            sale.setAppliedPromotionName(
                    bestPromotion.getName()
            );

            sale.setDiscountAmount(discount);
        }

        /*
         * 5. Generate basic warranties for consoles and
         *    assign requested extended warranties.
         */
        double extendedWarrantyCost = 0.0;

        if (warrantyService == null) {
            throw new IllegalStateException(
                    "WarrantyService no está configurado."
            );
        }

        LocalDate saleDate = LocalDate.parse(sale.getDate());

        List<String> extendedWarrantyIds =
                productIdsWithExtendedWarranty == null
                        ? Collections.emptyList()
                        : productIdsWithExtendedWarranty;

        for (Product product : products) {

            if (product instanceof Console) {

                warrantyService.assignBasicWarranty(
                        product,
                        sale,
                        saleDate
                );

                if (extendedWarrantyIds.contains(product.getId())) {

                    com.gamezone.model.ExtendedWarranty extendedWarranty =
                            warrantyService.assignExtendedWarranty(
                                    product,
                                    sale,
                                    saleDate
                            );

                    extendedWarrantyCost +=
                            extendedWarranty.getAdditionalCost();
                }
            }
        }

        sale.setExtendedWarrantyCost(
                extendedWarrantyCost
        );

        /*
         * 6. Calculate the final total.
         */
        sale.setTotalAmount(
                Math.max(
                        0.0,
                        subtotal + extendedWarrantyCost - discount
                )
        );

        /*
         * 7. Update inventory according to the item type.
         */
        for (Product product : products) {

            if (product instanceof Accessory) {
                accessoryService.updateStock(product.getId(), 1);
            } else {
                productService.updateStock(product.getId(), 1);
            }
        }

        /*
         * 8. Persist the sale.
         */
        List<Sale> sales = saleRepository.findAll();
        sales.add(sale);
        saleRepository.saveAll(sales);

        return sale;
    }

    /**
     * Returns all registered sales.
     *
     * @return list containing all registered sales
     */
    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    /**
     * Returns all sales made by a specific client.
     *
     * @param clientId identifier of the client
     * @return list of sales made by the client
     */
    public List<Sale> findSalesByClient(String clientId) {
        List<Sale> sales = saleRepository.findAll();
        List<Sale> clientSales = new ArrayList<>();

        for (Sale sale : sales) {
            if (sale.getClient() != null
                    && sale.getClient().getId().equals(clientId)) {
                clientSales.add(sale);
            }
        }

        return clientSales;
    }

    /**
     * Returns all sales handled by a specific seller.
     *
     * @param sellerId identifier of the seller
     * @return list of sales handled by the seller
     */
    public List<Sale> findSalesBySeller(String sellerId) {
        List<Sale> sales = saleRepository.findAll();
        List<Sale> sellerSales = new ArrayList<>();

        for (Sale sale : sales) {
            if (sale.getSeller() != null
                    && sale.getSeller().getId().equals(sellerId)) {
                sellerSales.add(sale);
            }
        }

        return sellerSales;
    }
}