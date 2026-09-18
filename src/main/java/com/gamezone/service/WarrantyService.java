package com.gamezone.service;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business rules for assigning warranties to sold products
 * and querying their validity and expiration status.
 */
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;
    private final List<Warranty> warranties;

    /**
     * Constructs a WarrantyService with the specified WarrantyRepository.
     *
     * @param warrantyRepository the repository for managing warranties
     */
    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
        this.warranties = warrantyRepository.loadAll();
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