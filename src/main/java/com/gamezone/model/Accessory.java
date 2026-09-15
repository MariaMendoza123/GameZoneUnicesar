package com.gamezone.model;

import java.util.List;
/**
 * Represents an accessory product in the game zone.
 */
public abstract class Accessory extends Product {

 private List<String> compatibleConsoleIds;
    /**
     * Constructs a new Accessory instance.
     *
     * @param id                   Unique identifier for the accessory.
     * @param title                Title or name of the accessory.
     * @param price                Base price of the accessory.
     * @param stockQuantity        Available quantity in stock.
     * @param compatibleConsoleIds List of console IDs that are compatible with this accessory.
     */
    public Accessory(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds) {
        super(id, title, price, stockQuantity);
        this.compatibleConsoleIds = compatibleConsoleIds;
    }
    /**
     * Returns the list of compatible console IDs for this accessory.
     *
     * @return List of compatible console IDs.
     */
    public List<String> getCompatibleConsoleIds() {
        return compatibleConsoleIds;
    }
    /**
     * Sets the list of compatible console IDs for this accessory.
     *
     * @param compatibleConsoleIds List of compatible console IDs.
     */
    public void setCompatibleConsoleIds(List<String> compatibleConsoleIds) {
        this.compatibleConsoleIds = compatibleConsoleIds;
    }
    /**
     * Checks if the accessory is compatible with a specific console ID.
     *
     * @param consoleId The console ID to check compatibility against.
     * @return true if compatible, false otherwise.
     */
    public boolean isCompatibleWith(String consoleId) {
        return compatibleConsoleIds.contains(consoleId);
    }
    /**
     * Adds a console ID to the list of compatible consoles if it's not already present.
     *
     * @param consoleId The console ID to add.
     */
    public void addCompatibleConsole(String consoleId) {
        if (!compatibleConsoleIds.contains(consoleId)) {
            compatibleConsoleIds.add(consoleId);
        }
    }
    /**
     * Removes a console ID from the list of compatible consoles.
     *
     * @param consoleId The console ID to remove.
     */
    public void removeCompatibleConsole(String consoleId) {
        compatibleConsoleIds.remove(consoleId);
    }
    /**
     * Returns a string representation of the accessory, including its title, price, stock quantity, and compatible consoles.
     *
     * @return a string representation of the accessory
     */
    @Override
    public String getDescription() {
        String consoles = (compatibleConsoleIds == null || compatibleConsoleIds.isEmpty())
                ? "None"
                : String.join(", ", compatibleConsoleIds);

        return "Accessory: " + getTitle() + ", Price: $" + getPrice() + ", Stock: " + getStockQuantity() +
                ", Compatible Consoles: " + consoles;
    }
}
