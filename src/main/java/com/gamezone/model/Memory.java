package com.gamezone.model;

import java.util.List;
/**
 * Represents a memory accessory in the game zone.
 */
public class Memory extends Accessory{

    private int capacityInGb;
    private String memoryType;
    /**
     * Constructs a new Memory instance.
     *
     * @param id                   Unique identifier for the memory.
     * @param title                Title or name of the memory.
     * @param price                Base price of the memory.
     * @param stockQuantity        Available quantity in stock.
     * @param compatibleConsoleIds List of console IDs that are compatible with this memory.
     * @param capacityInGb         Capacity of the memory in gigabytes (GB).
     * @param memoryType           Type of memory (e.g., DDR4, DDR5).
     */
    public Memory(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds, int capacityInGb, String memoryType) {
        super(id, title, price, stockQuantity, compatibleConsoleIds);
        this.capacityInGb = capacityInGb;
        this.memoryType = memoryType;
    }
    /**
     * Returns the capacity of the memory in gigabytes (GB).
     *
     * @return Capacity in GB.
     */
    public int getCapacityInGb() {
        return capacityInGb;
    }
    /**
     * Sets the capacity of the memory in gigabytes (GB).
     *
     * @param capacityInGb Capacity in GB.
     */
    public void setCapacityInGb(int capacityInGb) {
        this.capacityInGb = capacityInGb;
    }
    /**
     * Returns the type of memory (e.g., DDR4, DDR5).
     *
     * @return Memory type.
     */
    public String getMemoryType() {
        return memoryType;
    }
    /**
     * Sets the type of memory (e.g., DDR4, DDR5).
     *
     * @param memoryType Memory type.
     */
    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }
    /**
     * Returns a string representation of the memory, including its capacity and type.
     *
     * @return a string representation of the memory
     */
    @Override
    public String getDescription() {
        return super.getDescription() + ", Capacity: " + capacityInGb + "GB, Memory Type: " + memoryType;
    }
}
