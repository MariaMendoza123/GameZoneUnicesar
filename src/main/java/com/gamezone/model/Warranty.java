package com.gamezone.model;

import java.time.LocalDate;
/**
 * Abstract class representing a warranty in the GameZone system.
 * This class serves as a base for different types of warranties, such as basic and extended warranties.
 */
public abstract class Warranty {

    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;
    /**
     * Constructs a new Warranty with the specified details.
     *
     * @param id        the unique identifier for the warranty
     * @param product   the product associated with the warranty
     * @param sale      the sale associated with the warranty
     * @param startDate the start date of the warranty
     */
    public Warranty(String id, Product product, Sale sale, LocalDate startDate) {
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(getDurationInMonths());
    }
    /**
     * Returns the unique identifier of the warranty.
     *
     * @return the warranty ID
     */
    public String getId() {
        return id;
    }
    /**
     * Returns the product associated with the warranty.
     *
     * @return the product associated with the warranty
     */
    public Product getProduct() {
        return product;
    }
    /**
     * Returns the sale associated with the warranty.
     *
     * @return the sale associated with the warranty
     */
    public Sale getSale() {
        return sale;
    }
    /**
     * Returns the start date of the warranty.
     *
     * @return the start date of the warranty
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * Returns the end date of the warranty.
     *
     * @return the end date of the warranty
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * Returns the duration of the warranty in months.
     *
     * @return the duration of the warranty in months
     */
    public abstract int getDurationInMonths();
    /**
     * Returns the type of the warranty.
     *
     * @return the type of the warranty
     */
    public abstract String getWarrantyType();
    /**
     * Returns the additional cost of the warranty.
     *
     * @return the additional cost of the warranty
     */
    public abstract double getAdditionalCost();
    /**
     * Checks if the warranty is active on a given date.
     *
     * @param date the date to check
     * @return true if the warranty is active on the given date, false otherwise
     */
    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    /**
     * Generates a warranty certificate in text format.
     *
     * @return the warranty certificate as a string
     */
    public String generateWarrantyCertificate() {
        String certificate = "Certificado de Garantía\n";
        certificate += "ID de garantía: " + id + "\n";
        certificate += "Tipo: " + getWarrantyType() + "\n";
        certificate += "Producto: " + product.getTitle() + "\n";
        certificate += "Fecha de inicio: " + startDate + "\n";
        certificate += "Fecha de fin: " + endDate + "\n";
        certificate += "Costo adicional: $" + getAdditionalCost() + "\n";
        return certificate;
    }
}
