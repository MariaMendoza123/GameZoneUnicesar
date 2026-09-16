package com.gamezone.model;

import java.time.LocalDate;

public class CategoryDiscount extends Promotion {

    private double discountPercentage;
    private String targetCategory;

    public CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory) {
        super(id, name, startDate, endDate);
        this.discountPercentage = discountPercentage;
        this.targetCategory = targetCategory;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }
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