package com.gamezone.model;

import java.util.List;
/**
 * Represents a cable used for connecting gaming consoles.
 */
public class Cable extends Accessory{

    private double lengthInMeters;
    private String connectorType;
    /**
     * Constructs a new Cable instance.
     *
     * @param id                   Unique identifier for the cable.
     * @param title                Title or name of the cable.
     * @param price                Base price of the cable.
     * @param stockQuantity        Available quantity in stock.
     * @param compatibleConsoleIds List of console IDs that are compatible with this cable.
     * @param lengthInMeters       Length of the cable in meters.
     * @param connectorType        Type of connector (e.g., HDMI, USB).
     */
    public Cable(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds, double lengthInMeters, String connectorType) {
        super(id, title, price, stockQuantity, compatibleConsoleIds);
        this.lengthInMeters = lengthInMeters;
        this.connectorType = connectorType;
    }
    /**
     * Returns the length of the cable in meters.
     *
     * @return Length of the cable in meters.
     */
    public double getLengthInMeters() {
        return lengthInMeters;
    }
    /**
     * Sets the length of the cable in meters.
     *
     * @param lengthInMeters Length of the cable in meters.
     */
    public void setLengthInMeters(double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }
    /**
     * Returns the type of connector for the cable.
     *
     * @return Type of connector (e.g., HDMI, USB).
     */
    public String getConnectorType() {
        return connectorType;
    }
    /**
     * Sets the type of connector for the cable.
     *
     * @param connectorType Type of connector (e.g., HDMI, USB).
     */
    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }
    /**
     * Returns a string representation of the cable, including its length and connector type.
     *
     * @return a string representation of the cable
     */
    @Override
    public String toString() {
        return super.getDescription() + ", Length: " + lengthInMeters + " meters, Connector Type: " + connectorType;
    }


}
