package com.gamezone.model;

import java.time.LocalDate;
/**
 * Represents a percentage discount promotion in the GameZone system.
 * This class extends the abstract Promotion class and provides functionality
 * to calculate discounts based on a specified percentage.
 */
public class PercentageDiscount extends Promotion{

    private double percentage;
    /**
     * Constructs a new PercentageDiscount with the specified details.
     *
     * @param id        the unique identifier for the promotion
     * @param name      the name of the promotion
     * @param startDate the start date of the promotion
     * @param endDate   the end date of the promotion
     * @param percentage the percentage of discount to be applied
     */
    public PercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
    }
    /**
     * Returns the percentage of the discount.
     *
     * @return the percentage of the discount
     */
    public double getPercentage() {
        return percentage;
    }
    /**
     * Sets the percentage of the discount.
     *
     * @param percentage the percentage to set
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    /**
     * Calculates the discount amount for a given sale based on the percentage discount.
     *
     * @param sale the sale for which the discount is to be calculated
     * @return the discount amount
     */
    @Override
    public double calculateDiscount(Sale sale) {
        double total = sale.calculateTotal();
        return total * (percentage / 100);
    }
}
