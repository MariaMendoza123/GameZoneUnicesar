package com.gamezone.model;

import java.util.List;
/**
 * Represents a controller accessory in the game zone.
 */
public class Controller extends Accessory {

    private String ConnectionType; // e.g., Wired, Wireless
    /**
     * Constructs a new Controller instance.
     *
     * @param id                   Unique identifier for the controller.
     * @param title                Title or name of the controller.
     * @param price                Base price of the controller.
     * @param stockQuantity        Available quantity in stock.
     * @param compatibleConsoleIds List of console IDs that are compatible with this controller.
     * @param connectionType       Type of connection (e.g., Wired, Wireless).
     */
    public Controller(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds, String connectionType) {
        super(id, title, price, stockQuantity, compatibleConsoleIds);
        ConnectionType = connectionType;
    }
    /**
     * Returns the connection type of the controller.
     *
     * @return Connection type (e.g., Wired, Wireless).
     */
    public String getConnectionType() {
        return ConnectionType;
    }
    /**
     * Sets the connection type of the controller.
     *
     * @param connectionType Connection type (e.g., Wired, Wireless).
     */
    public void setConnectionType(String connectionType) {
        ConnectionType = connectionType;
    }
    /**
     * Returns a string representation of the controller, including its connection type.
     *
     * @return a string representation of the controller
     */
    @Override
    public String getDescription() {
        return super.getDescription() + ", Connection Type: " + ConnectionType;
    }
}
