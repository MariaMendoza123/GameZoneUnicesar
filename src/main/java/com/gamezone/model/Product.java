package com.gamezone.model;
/**
 * Represents a generic product in the game zone.
 */
public abstract class Product {

     private String id;
     private String title;
     private double price;
     private int stockQuantity;
     /**
      * Constructs a new Product instance.
      *
      * @param id            Unique identifier for the product.
      * @param title         Title or name of the product.
      * @param price         Base price of the product.
      * @param stockQuantity Available quantity in stock.
      */
     public Product(String id, String title, double price, int stockQuantity) {
          this.id = id;
          this.title = title;
          this.price = price;
          this.stockQuantity = stockQuantity;
     }

     /**
      * Returns a string representation of the product.
      *
      * @return a string representation of the product
      */
     public abstract String getDescription();

     // Getters and setters
     /**
      * Returns the unique identifier of the product.
      *
      * @return the unique identifier of the product
      */
     public String getId() {
          return id;
     }
     /**
      * Sets the unique identifier of the product.
      *
      * @param id the unique identifier of the product
      */
     public void setId(String id) {
          this.id = id;
     }
     /**
      * Returns the title of the product.
      *
      * @return the title of the product
      */
     public String getTitle() {
          return title;
     }
        /**
        * Sets the title of the product.
        *
        * @param title the title of the product
        */
     public void setTitle(String title) {
          this.title = title;
     }
     /**
      * Returns the price of the product.
      *
      * @return the price of the product
      */
     public double getPrice() {
          return price;
     }
        /**
        * Sets the price of the product.
        *
        * @param price the price of the product
        */
     public void setPrice(double price) {
          this.price = price;
     }
     /**
      * Returns the available stock quantity of the product.
      *
      * @return the available stock quantity of the product
      */
     public int getStockQuantity() {
          return stockQuantity;
     }
        /**
        * Sets the available stock quantity of the product.
        *
        * @param stockQuantity the available stock quantity of the product
        */
     public void setStockQuantity(int stockQuantity) {
          this.stockQuantity = stockQuantity;
     }
     /**
      * Returns a string representation of the product.
      *
      * @return a string representation of the product
      */
     @Override
     public String toString() {
          return getDescription();
     }
}
