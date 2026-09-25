package com.gamezone.service;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business rules for assigning warranties to sold products
 * and querying their validity and expiration status.
 * Depends on SaleRepository (not SaleService) and ProductService to
 * resolve the Sale and Product references of stored warranties, which
 * avoids a circular dependency between this class and SaleService.
 */
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final List<Warranty> warranties;

    /**
     * Constructs a WarrantyService with the dependencies needed to persist
     * warranties and resolve their Sale and Product references.
     *
     * @param warrantyRepository the repository for managing warranties
     * @param saleRepository     the repository used to resolve the associated sale by ID
     * @param productService     the service used to resolve the associated product by ID
     */
    public WarrantyService(
            WarrantyRepository warrantyRepository,
            SaleRepository saleRepository,
            ProductService productService
    ) {
        this.warrantyRepository = warrantyRepository;
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.warranties = resolveWarranties(warrantyRepository.loadAll());
    }

    private List<Warranty> resolveWarranties(List<WarrantyRepository.WarrantyRecord> records) {
        List<Warranty> resolved = new ArrayList<>();
        for (WarrantyRepository.WarrantyRecord record : records) {
            Sale sale = findSaleById(record.getSaleId());
            if (sale == null) {
                throw new IllegalArgumentException("Venta no encontrada con ID: " + record.getSaleId());
            }

            Product product = findProductById(sale, record.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + record.getProductId());
            }

            if ("BASIC".equals(record.getType())) {
                resolved.add(new BasicWarranty(record.getId(), product, sale, record.getStartDate()));
            } else {
                resolved.add(new ExtendedWarranty(record.getId(), product, sale, record.getStartDate()));
            }
        }
        return resolved;
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : saleRepository.findAll()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }

    private Product findProductById(Sale sale, String productId) {
        for (Product product : sale.getProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return productService.findProduct(productId);
    }

    /**
     * Assigns an automatic basic warranty to a product included in a sale.
     *
     * @param product   the product covered by the warranty
     * @param sale      the sale in which the product was sold
     * @param startDate the start date of the warranty coverage
     * @return the registered BasicWarranty object
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        String id = generateNextId("WB");
        BasicWarranty warranty = new BasicWarranty(id, product, sale, startDate);
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
        return warranty;
    }

    /**
     * Assigns an optional extended warranty to a product included in a sale.
     *
     * @param product   the product covered by the warranty
     * @param sale      the sale in which the product was sold
     * @param startDate the start date of the warranty coverage
     * @return the registered ExtendedWarranty object
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        String id = generateNextId("WE");
        ExtendedWarranty warranty = new ExtendedWarranty(id, product, sale, startDate);
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
        return warranty;
    }

    /**
     * Finds the warranty associated with a specific product within a specific sale.
     *
     * @param productId the ID of the product
     * @param saleId    the ID of the sale
     * @return the matching Warranty, or null if none exists
     */
    public Warranty findWarrantyByProduct(String productId, String saleId) {
        for (Warranty warranty : warranties) {
            if (warranty.getProduct().getId().equals(productId)
                    && warranty.getSale().getId().equals(saleId)) {
                return warranty;
            }
        }
        return null;
    }

    /**
     * Retrieves all registered warranties.
     *
     * @return a list of all warranties
     */
    public List<Warranty> listAllWarranties() {
        return warranties;
    }

    /**
     * Retrieves all warranties currently active on today's date.
     *
     * @return a list of active warranties
     */
    public List<Warranty> listActiveWarranties() {
        List<Warranty> active = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Warranty warranty : warranties) {
            if (warranty.isActive(today)) {
                active.add(warranty);
            }
        }
        return active;
    }

    /**
     * Retrieves all warranties whose end date falls within the given number
     * of days from today (inclusive), and that have not already expired.
     *
     * @param daysAhead the number of days to look ahead
     * @return a list of warranties expiring soon
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        List<Warranty> expiringSoon = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);

        for (Warranty warranty : warranties) {
            LocalDate endDate = warranty.getEndDate();
            if (!endDate.isBefore(today) && !endDate.isAfter(limit)) {
                expiringSoon.add(warranty);
            }
        }
        return expiringSoon;
    }

    private String generateNextId(String prefix) {
        int maxId = 0;
        for (Warranty warranty : warranties) {
            if (warranty.getId() != null && warranty.getId().startsWith(prefix + "-")) {
                try {
                    int numericPart = Integer.parseInt(warranty.getId().replace(prefix + "-", ""));
                    if (numericPart > maxId) {
                        maxId = numericPart;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return prefix + "-" + (maxId + 1);
    }
}