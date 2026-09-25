package com.gamezone.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Represents a sale made at the store.
 */
public class Sale {

    private String id;
    private String date;
    private Client client;
    private Seller seller;
    private List<Product> products;
    private double totalAmount;
    private String appliedPromotionName;
    private double discountAmount;
    private double extendedWarrantyCost;

    /**
     * Constructs a new Sale instance.
     *
     * @param id       Unique identifier for the sale.
     * @param date     Date of the sale.
     * @param client   Client who made the purchase.
     * @param seller   Seller who handled the sale.
     * @param products Products included in the sale.
     */
    public Sale(String id, String date, Client client, Seller seller, List<Product> products) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.seller = seller;
        this.products = products;
        this.totalAmount = 0.0;
        this.appliedPromotionName = null;
        this.discountAmount = 0.0;
        this.extendedWarrantyCost = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Client getClient() {
        return client;
    }

    public Seller getSeller() {
        return seller;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }

    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Returns the additional cost of the extended warranties in the sale.
     *
     * @return the extended warranty cost
     */
    public double getExtendedWarrantyCost() {
        return extendedWarrantyCost;
    }

    /**
     * Sets the additional cost of the extended warranties in the sale.
     *
     * @param extendedWarrantyCost the extended warranty cost
     */
    public void setExtendedWarrantyCost(double extendedWarrantyCost) {
        this.extendedWarrantyCost = extendedWarrantyCost;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Calculates the subtotal of the products included in the sale.
     *
     * @return the subtotal
     */
    public double calculateTotal() {
        totalAmount = 0.0;

        for (Product product : products) {
            totalAmount += product.getPrice();
        }

        return totalAmount;
    }

    /**
     * Calculates the final total after applying the discount.
     *
     * @return the final total
     */
    public double calculateFinalTotal() {
        double subtotal = calculateTotal();
        return Math.max(
                0.0,
                subtotal + extendedWarrantyCost - discountAmount
        );
    }
    /**
     * Generates a receipt containing the subtotal, promotion,
     * discount, and final total.
     *
     * @return formatted receipt
     */
    public String generateReceipt() {
        double subtotal = calculateTotal();
        double finalTotal = calculateFinalTotal();

        StringBuilder receipt = new StringBuilder();

        receipt.append("===== GAMEZONE RECEIPT =====\n");
        receipt.append("Sale ID: ").append(id).append("\n");
        receipt.append("Date: ").append(date).append("\n");
        receipt.append("Client: ").append(client.getName()).append("\n");
        receipt.append("Seller: ").append(seller.getName()).append("\n");
        receipt.append("----------------------------\n");
        receipt.append("Subtotal: ").append(subtotal).append("\n");

        if (appliedPromotionName != null && !appliedPromotionName.isBlank()
                && discountAmount > 0) {
            receipt.append("Promotion: ").append(appliedPromotionName).append("\n");
            receipt.append("Discount: ").append(discountAmount).append("\n");
        } else {
            receipt.append("Promotion: None\n");
            receipt.append("Discount: 0.0\n");
        }

        receipt.append("Extended warranty cost: ")
                .append(extendedWarrantyCost)
                .append("\n");

        receipt.append("Final total: ").append(finalTotal).append("\n");

        receipt.append("============================");

        return receipt.toString();
    }
    /**
     * Determines if the sale can be returned based on the date of the sale.
     * A sale can be returned if it was made within the last 30 days.
     *
     * @return true if the sale can be returned, false otherwise
     */
    public boolean canBeReturned() {
        LocalDate saleDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        long daysPassed = ChronoUnit.DAYS.between(saleDate, currentDate);
        return daysPassed <= 30;
    }
}