package com.gamezone.model;

import java.time.LocalDate;
/**
 * Abstract class representing a promotion in the GameZone system.
 * This class serves as a base for different types of promotions, such as percentage discounts,
 * category-specific discounts, and bulk purchase discounts.
 */
public abstract class Promotion {

    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    /**
     * Constructs a new Promotion with the specified details.
     *
     * @param id        the unique identifier for the promotion
     * @param name      the name of the promotion
     * @param startDate the start date of the promotion
     * @param endDate   the end date of the promotion
     */
    public Promotion(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns the unique identifier of the promotion.
     *
     * @return the promotion ID
     */
    public String getId() {
        return id;
    }
    /**
     * Sets the unique identifier of the promotion.
     *
     * @param id the promotion ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Returns the name of the promotion.
     *
     * @return the name of the promotion
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the promotion.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the start date of the promotion.
     *
     * @return the start date of the promotion
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * Sets the start date of the promotion.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    /**
     * Returns the end date of the promotion.
     *
     * @return the end date of the promotion
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * Sets the end date of the promotion.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    /**
     * Checks if the promotion is active on the given date.
     *
     * @param date the date to check
     * @return true if the promotion is active on the given date, false otherwise
     */
    public boolean isActive(LocalDate date) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
    }
    /**
     * Abstract method to calculate the discount for a given sale.
     * Subclasses must implement this method to provide specific discount calculation logic.
     *
     * @param sale the sale for which the discount is to be calculated
     * @return the calculated discount amount
     */
    public abstract double calculateDiscount(Sale sale);
}
