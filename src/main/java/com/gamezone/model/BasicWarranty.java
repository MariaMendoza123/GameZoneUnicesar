package com.gamezone.model;

import java.time.LocalDate;
/**
 * Class representing a basic warranty in the GameZone system.
 * This class extends the abstract Warranty class and provides specific details for a basic warranty.
 */
public class BasicWarranty extends Warranty{
   /**
     * Constructs a new BasicWarranty with the specified details.
     *
     * @param id        the unique identifier for the warranty
     * @param product   the product associated with the warranty
     * @param sale      the sale associated with the warranty
     * @param startDate the start date of the warranty
     */
    public BasicWarranty(String id, Product product, Sale sale, LocalDate startDate) {
        super(id, product, sale, startDate);
    }
    /**
     * Returns the duration of the basic warranty in months.
     *
     * @return the duration of the basic warranty in months
     */
    @Override
    public int getDurationInMonths() {
        return 6;
    }
    /**
     * Returns the type of the warranty.
     *
     * @return the type of the warranty
     */
    @Override
    public String getWarrantyType() {
        return "Garantía Básica";
    }
    /**
     * Returns the additional cost associated with the basic warranty.
     *
     * @return the additional cost associated with the basic warranty
     */
    @Override
    public double getAdditionalCost() {
        return 0.0;
    }
}
