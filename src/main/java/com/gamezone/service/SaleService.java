package com.gamezone.service;

import com.gamezone.model.Client;
import com.gamezone.model.Person;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.persistence.SaleRepository;

import java.util.List;

/**
 * Service class for managing sales.
 */
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final PersonService personService;

    /**
     * Constructs a SaleService with the required dependencies.
     *
     * @param saleRepository repository for managing sales
     * @param productService service for managing products
     * @param personService service for managing persons
     */
    public SaleService(SaleRepository saleRepository,
                       ProductService productService,
                       PersonService personService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
    }

    /**
     * Registers a new sale after validating the required information.
     *
     * @param id       unique identifier of the sale
     * @param date     date of the sale
     * @param clientId identifier of the client
     * @param sellerId identifier of the seller
     * @param products products included in the sale
     * @return the registered Sale
     */
    public Sale registerSale(String id,
                             String date,
                             String clientId,
                             String sellerId,
                             List<Product> products) {

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

        for (Product product : products) {
            Product storedProduct = productService.findProduct(product.getId());

            if (storedProduct == null) {
                throw new IllegalArgumentException(
                        "Producto no encontrado con ID: " + product.getId()
                );
            }

            if (storedProduct.getStockQuantity() <= 0) {
                throw new IllegalStateException(
                        "Stock insuficiente para el producto: "
                                + storedProduct.getTitle()
                );
            }
        }

        for (Product product : products) {
            productService.updateStock(product.getId(), 1);
        }

        Sale sale = new Sale(
                id,
                date,
                client,
                seller,
                products
        );

        sale.calculateTotal();

        List<Sale> sales = saleRepository.findAll();
        sales.add(sale);
        saleRepository.saveAll(sales);

        return sale;
    }
}