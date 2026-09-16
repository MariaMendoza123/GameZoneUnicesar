package com.gamezone.model;

import java.time.LocalDate;
/**
 * Represents a category-specific discount promotion in the GameZone system.
 * This class extends the abstract Promotion class and provides functionality
 * to calculate discounts based on a specified percentage for a specific product category.
 */
public class CategoryDiscount extends Promotion {

    private double discountPercentage;
    private String targetCategory;
    /**
     * Constructs a new CategoryDiscount with the specified details.
     *
     * @param id                 the unique identifier for the promotion
     * @param name               the name of the promotion
     * @param startDate          the start date of the promotion
     * @param endDate            the end date of the promotion
     * @param discountPercentage the percentage of discount to be applied
     * @param targetCategory     the product category to which the discount applies
     */
    public CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory) {
        super(id, name, startDate, endDate);
        this.discountPercentage = discountPercentage;
        this.targetCategory = targetCategory;
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
     * Returns the target category for the discount.
     *
     * @return the product category to which the discount applies
     */
    public String getTargetCategory() {
        return targetCategory;
    }
    /**
     * Sets the target category for the discount.
     *
     * @param targetCategory the product category to which the discount applies
     */
    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }
    /**
     * Calculates the discount amount for a given sale based on the category-specific discount.
     * The discount is applied only to products that belong to the specified target category.
     *
     * @param sale the sale for which the discount is to be calculated
     * @return the discount amount
     */
    @Override
    public double calculateDiscount(Sale sale) {
        double categorySubtotal = 0;
        for (Product product : sale.getProducts()) {
            if ((product instanceof VideoGame && targetCategory.equals("VIDEOGAME")) ||
                    (product instanceof Console && targetCategory.equals("CONSOLE"))) {
                categorySubtotal += product.getPrice();
            }
        }
        return categorySubtotal * (discountPercentage / 100);
    }
}