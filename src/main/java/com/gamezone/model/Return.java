package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

public class Return {

    private String id;
    private LocalDate returnDate;
    private com.gamezone.model.Sale originalSale;
    private List<Product> returnedProducts;
    private String returnReason;
    private double refundAmount;

    public Return(String id, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String returnReason, double refundAmount) {
        this.id = id;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.returnReason = returnReason;
        this.refundAmount = refundAmount;
    }

    public String getId() {
        return id;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Sale getOriginalSale() {
        return originalSale;
    }

    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public double getRefundAmount() {
        return refundAmount;
    }
}
