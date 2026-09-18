package com.gamezone.model;

import java.time.LocalDate;

public abstract class Warranty {

    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    public Warranty(String id, Product product, Sale sale, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public abstract int getDurationInMonths();

    public abstract String getWarrantyType();

    public abstract double getAdditionalCost();

    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

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
