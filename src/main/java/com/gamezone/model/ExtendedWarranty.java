package com.gamezone.model;

import java.time.LocalDate;
/**
 * Represents an extended warranty in the GameZone system.
 * This class extends the Warranty class and provides specific details for extended warranties.
 */
public class ExtendedWarranty extends Warranty{
    /**
     * Constructs a new ExtendedWarranty with the specified details.
     *
     * @param id        the unique identifier for the extended warranty
     * @param product   the product associated with the extended warranty
     * @param sale      the sale associated with the extended warranty
     * @param startDate the start date of the extended warranty
     */
    public ExtendedWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }
    /**
     * Returns the duration of the extended warranty in months.
     *
     * @return the duration of the extended warranty in months
     */
    @Override
    public int getDurationInMonths() {
        return 12;
    }
    /**
     * Returns the type of the warranty.
     *
     * @return the type of the warranty
     */
    @Override
    public String getWarrantyType() {
        return "Garantía Extendida";
    }
    /**
     * Returns the additional cost of the extended warranty.
     *
     * @return the additional cost of the extended warranty
     */
    @Override
    public double getAdditionalCost() {
        return getProduct().getPrice() * 0.10; // 10% del precio del producto
    }
}
