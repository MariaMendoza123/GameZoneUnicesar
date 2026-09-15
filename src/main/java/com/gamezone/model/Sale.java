package com.gamezone.model;

import com.gamezone.model.Client;
import com.gamezone.model.Product;
import com.gamezone.model.Seller;

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


    public double calculateTotal() {
        totalAmount = 0.0;

        for (Product product : products) {
            totalAmount += product.getPrice();
        }

        return totalAmount;
    }


}

