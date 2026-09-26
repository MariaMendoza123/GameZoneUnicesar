package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;
import com.gamezone.persistence.AccessoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business rules for registering, listing, and querying accessories.
 */
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final List<Accessory> accessories;

    /**
     * Constructs an AccessoryService with the specified AccessoryRepository.
     *
     * @param accessoryRepository the repository for managing accessories
     */
    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
        this.accessories = accessoryRepository.loadAll();
    }

    /**
     * Registers a new controller in the system.
     *
     * @param title                the title of the controller
     * @param price                the price of the controller
     * @param stockQuantity        the stock quantity of the controller
     * @param compatibleConsoleIds the list of compatible console IDs
     * @param connectionType       the connection type (e.g. Wired, Wireless)
     * @return the registered Controller object
     */
    public Controller registerController(String title, double price, int stockQuantity,
                                         List<String> compatibleConsoleIds, String connectionType) {
        validateCommonAttributes(title, price, stockQuantity);
        if (connectionType == null || connectionType.isBlank()) {
            throw new IllegalArgumentException("El tipo de conexión del control es obligatorio.");
        }

        String id = generateNextId("CT");
        Controller controller = new Controller(id, title, price, stockQuantity, compatibleConsoleIds, connectionType);
        accessories.add(controller);
        accessoryRepository.saveAll(accessories);
        return controller;
    }

    /**
     * Registers a new cable in the system.
     *
     * @param title                the title of the cable
     * @param price                the price of the cable
     * @param stockQuantity        the stock quantity of the cable
     * @param compatibleConsoleIds the list of compatible console IDs
     * @param lengthInMeters       the length of the cable in meters
     * @param connectorType        the connector type (e.g. HDMI, USB)
     * @return the registered Cable object
     */
    public Cable registerCable(String title, double price, int stockQuantity,
                               List<String> compatibleConsoleIds, double lengthInMeters, String connectorType) {
        validateCommonAttributes(title, price, stockQuantity);
        if (lengthInMeters <= 0) {
            throw new IllegalArgumentException("La longitud del cable debe ser mayor que cero.");
        }
        if (connectorType == null || connectorType.isBlank()) {
            throw new IllegalArgumentException("El tipo de conector del cable es obligatorio.");
        }

        String id = generateNextId("CA");
        Cable cable = new Cable(id, title, price, stockQuantity, compatibleConsoleIds, lengthInMeters, connectorType);
        accessories.add(cable);
        accessoryRepository.saveAll(accessories);
        return cable;
    }

    /**
     * Registers a new memory in the system.
     *
     * @param title                the title of the memory
     * @param price                the price of the memory
     * @param stockQuantity        the stock quantity of the memory
     * @param compatibleConsoleIds the list of compatible console IDs
     * @param capacityInGb         the storage capacity in gigabytes
     * @param memoryType           the memory type (e.g. SD, microSD)
     * @return the registered Memory object
     */
    public Memory registerMemory(String title, double price, int stockQuantity,
                                 List<String> compatibleConsoleIds, int capacityInGb, String memoryType) {
        validateCommonAttributes(title, price, stockQuantity);
        if (capacityInGb <= 0) {
            throw new IllegalArgumentException("La capacidad de la memoria debe ser mayor que cero.");
        }
        if (memoryType == null || memoryType.isBlank()) {
            throw new IllegalArgumentException("El tipo de memoria es obligatorio.");
        }

        String id = generateNextId("ME");
        Memory memory = new Memory(id, title, price, stockQuantity, compatibleConsoleIds, capacityInGb, memoryType);
        accessories.add(memory);
        accessoryRepository.saveAll(accessories);
        return memory;
    }

    /**
     * Retrieves all accessories from the repository.
     *
     * @return a list of all accessories
     */
    public List<Accessory> listAllAccessories() {
        return accessories;
    }

    /**
     * Retrieves all accessories of a given type.
     *
     * @param type the accessory type ("CONTROLLER", "CABLE" or "MEMORY")
     * @return a list containing only accessories of the given type
     */
    public List<Accessory> listAccessoriesByType(String type) {
        List<Accessory> result = new ArrayList<>();
        for (Accessory accessory : accessories) {
            if (matchesType(accessory, type)) {
                result.add(accessory);
            }
        }
        return result;
    }

    /**
     * Retrieves all accessories compatible with a given console.
     *
     * @param consoleId the ID of the console to check compatibility against
     * @return a list of accessories compatible with the given console
     */
    public List<Accessory> findAccessoriesCompatibleWith(String consoleId) {
        List<Accessory> result = new ArrayList<>();
        for (Accessory accessory : accessories) {
            if (accessory.isCompatibleWith(consoleId)) {
                result.add(accessory);
            }
        }
        return result;
    }

    /**
     * Finds an accessory by its ID.
     *
     * @param id the ID of the accessory to find
     * @return the found Accessory object, or null if not found
     */
    public Accessory findById(String id) {
        for (Accessory accessory : accessories) {
            if (accessory.getId().equals(id)) {
                return accessory;
            }
        }
        return null;
    }

    /**
     * Updates the stock quantity of an accessory after a sale.
     *
     * @param accessoryId the ID of the accessory
     * @param quantity    the quantity sold
     */
    public void updateStock(String accessoryId, int quantity) {
        Accessory accessory = findById(accessoryId);
        if (accessory == null) {
            throw new IllegalArgumentException("Accesorio no encontrado con ID: " + accessoryId);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser mayor que cero.");
        }
        if (accessory.getStockQuantity() < quantity) {
            throw new IllegalStateException("Stock insuficiente para el accesorio: " + accessory.getTitle());
        }
        accessory.setStockQuantity(accessory.getStockQuantity() - quantity);
        accessoryRepository.saveAll(accessories);
    }

    /**
     * Restores the stock quantity of an accessory after a return is processed.
     *
     * @param accessoryId the ID of the accessory
     * @param quantity    the quantity to restore
     */
    public void restoreStock(String accessoryId, int quantity) {
        Accessory accessory = findById(accessoryId);
        if (accessory == null) {
            throw new IllegalArgumentException("Accesorio no encontrado con ID: " + accessoryId);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad a restaurar debe ser mayor que cero.");
        }
        accessory.setStockQuantity(accessory.getStockQuantity() + quantity);
        accessoryRepository.saveAll(accessories);
    }

    private boolean matchesType(Accessory accessory, String type) {
        if (type == null) return false;
        switch (type.toUpperCase()) {
            case "CONTROLLER": return accessory instanceof Controller;
            case "CABLE": return accessory instanceof Cable;
            case "MEMORY": return accessory instanceof Memory;
            default: return false;
        }
    }

    private void validateCommonAttributes(String title, double price, int stockQuantity) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título del accesorio es obligatorio.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("El precio del accesorio no puede ser negativo.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("La cantidad en stock no puede ser negativa.");
        }
    }

    private String generateNextId(String prefix) {
        int maxId = 0;
        for (Accessory accessory : accessories) {
            if (accessory.getId() != null && accessory.getId().startsWith(prefix + "-")) {
                try {
                    int numericPart = Integer.parseInt(accessory.getId().replace(prefix + "-", ""));
                    if (numericPart > maxId) {
                        maxId = numericPart;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return prefix + "-" + (maxId + 1);
    }
}