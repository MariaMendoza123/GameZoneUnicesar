package com.gamezone.model;
/**
 * Represents a console product in the game zone.
 */
public class Console extends Product {

    private String brand;
    private String model;
    private String generation;

    /**
     * Constructs a new Console instance.
     *
     * @param id            Unique identifier for the product.
     * @param title         Title or name of the console.
     * @param price         Base price of the console.
     * @param stockQuantity Available quantity in stock.
     * @param brand         Brand of the console (e.g., Sony, Microsoft).
     * @param model         Model of the console (e.g., Series S, PS5 Digital).
     * @param generation    Hardware generation of the console.
     */
    public Console(String id, String title, double price, int stockQuantity, String brand, String model, String generation) {
        super(id, title, price, stockQuantity);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }
    /**
     * Returns the brand of the console.
     *
     * @return the brand of the console
     */
    public String getBrand() {
        return brand;
    }
    /**
     * Sets the brand of the console.
     *
     * @param brand the brand of the console
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
/**
     * Returns the model of the console.
     *
     * @return the model of the console
     */
    public String getModel() {
        return model;
    }
    /**
     * Sets the model of the console.
     *
     * @param model the model of the console
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * Returns the generation of the console.
     *
     * @return the generation of the console
     */
    public String getGeneration() {
        return generation;
    }
    /**
     * Sets the generation of the console.
     *
     * @param generation the generation of the console
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * Returns a string representation of the console.
     *
     * @return a string representation of the console
     */
    @Override
    public String getDescription() {
        return "Console{" +
                "id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                ", stockQuantity=" + getStockQuantity() +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", generation='" + generation + '\'' +
                '}';
    }
    /**
     * Returns a string representation of the console.
     *
     * @return a string representation of the console
     */
    @Override
    public String toString() {
        return getDescription();
    }
}
