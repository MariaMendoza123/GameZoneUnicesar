package com.gamezone.model;

import java.time.LocalDate;
/**
 * Represents a bulk purchase discount promotion in the GameZone system.
 * This class extends the abstract Promotion class and provides functionality
 * to calculate discounts based on a minimum quantity of products purchased.
 */
public class BulkPurchaseDiscount extends Promotion{

    private int minimumQuantity;
    private double discountPercentage;
    /**
     * Constructs a new BulkPurchaseDiscount with the specified details.
     *
     * @param id                 the unique identifier for the promotion
     * @param name               the name of the promotion
     * @param startDate          the start date of the promotion
     * @param endDate            the end date of the promotion
     * @param minimumQuantity    the minimum quantity required to qualify for the discount
     * @param discountPercentage the percentage of discount to be applied
     */
    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, int minimumQuantity, double discountPercentage) {
        super(id, name, startDate, endDate);
        this.minimumQuantity = minimumQuantity;
        this.discountPercentage = discountPercentage;
    }
    /**
     * Returns the minimum quantity required to qualify for the bulk purchase discount.
     *
     * @return the minimum quantity
     */
    public int getMinimumQuantity() {
        return minimumQuantity;
    }
    /**
     * Sets the minimum quantity required to qualify for the bulk purchase discount.
     *
     * @param minimumQuantity the minimum quantity to set
     */
    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }
    /**
     * Returns the percentage of the discount.
     *
     * @return the percentage of the discount
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    /**
     * Sets the percentage of the discount.
     *
     * @param discountPercentage the percentage to set
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    /**
     * Calculates the discount amount for a given sale based on the bulk purchase discount.
     * If the total quantity of products in the sale meets or exceeds the minimum quantity,
     * the discount is applied to the total sale amount.
     *
     * @param sale the sale for which the discount is to be calculated
     * @return the discount amount, or 0 if the minimum quantity is not met
     */
    @Override
    public double calculateDiscount(Sale sale) {
        int totalQuantity = sale.getProducts().size();
        if (totalQuantity >= minimumQuantity) {
            double total = sale.calculateTotal();
            return total * (discountPercentage / 100);
        }
        return 0;
    }
}
