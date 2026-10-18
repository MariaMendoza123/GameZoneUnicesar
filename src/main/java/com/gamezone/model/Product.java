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
      * Returns a CSV representation of the product.
      *
      * @return a CSV representation of the product
      */
     public abstract String toCsvLine();
     /**
      * Creates a Product instance from a CSV line.
      *
      * @param line the CSV line representing the product
      * @return a Product instance
      */
     public static Product fromCsvLine(String line) {
          String[] fields = line.split("\\|", -1);
          String type = fields[0];
          String id = fields[1];
          String title = fields[2];
          double price = Double.parseDouble(fields[3]);
          int stockQuantity = Integer.parseInt(fields[4]);

          if ("VIDEOGAME".equals(type)) {
               return new VideoGame(id, title, price, stockQuantity, fields[5], fields[6], fields[7]);
          } else if ("CONSOLE".equals(type)) {
               return new Console(id, title, price, stockQuantity, fields[5], fields[6], fields[7]);
          }
          throw new IllegalArgumentException("Unknown product type in file: " + type);
     }
     /**
      * Reduces the stock quantity by the specified amount.
      *
      * @param quantity The amount of stock to reduce.
      */
     public void reduceStock(int quantity) {
          if (quantity > 0 && quantity <= this.stockQuantity) {
               this.stockQuantity -= quantity;
          }
     }

     /**
      * Increases the stock quantity by the specified amount.
      *
      * @param quantity The amount of stock to add.
      */
     public void increaseStock(int quantity) {
          if (quantity > 0) {
               this.stockQuantity += quantity;
          }
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
     /**
      * Returns the category name of the product.
      *
      * @return the category name of the product
      */
     public abstract String getCategoryName();

     /**
      * Checks if the product has sufficient stock for the requested quantity.
      *
      * @param requestedQuantity the quantity to verify
      * @return true if stock is greater than or equal to requestedQuantity and requestedQuantity is positive
      */
     public boolean hasStock(int requestedQuantity) {
          return requestedQuantity > 0 && this.getStockQuantity() >= requestedQuantity;
     }
}
