package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class for managing accessories in the game zone.
 * This class provides methods to save and load all accessories from a CSV file.
 */
public class AccessoryRepository {

    private static final String FILE_PATH = "data/accessories.csv";
    private static final String SEPARATOR = ",";
    private static final String DELIMITER = ",";
    private static final String LIST_SEPARATOR = ";";
    private static final String CONTROLLER_TAG = "CONTROLLER";
    private static final String CABLE_TAG = "CABLE";
    private static final String MEMORY_TAG = "MEMORY";

    /**
     * Persists the full list of accessories to the CSV file, overwriting its content.
     *
     * @param accessories the complete list of accessories to persist
     */
    public void saveAll(List<Accessory> accessories) {
        List<String> lines = new ArrayList<>();
        for (Accessory accessory : accessories) {
            lines.add(toLine(accessory));
        }
        writeLines(lines);
    }

    /**
     * Loads all accessories stored in the CSV file.
     *
     * @return the list of accessories found, or an empty list if the file does not exist
     */
    public List<Accessory> loadAll() {
        List<Accessory> accessories = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                accessories.add(fromLine(line));
            }
        }
        return accessories;
    }

    private List<String> readLines() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + FILE_PATH, e);
        }
    }

    private void writeLines(List<String> lines) {
        Path path = Paths.get(FILE_PATH);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + FILE_PATH, e);
        }
    }

    private String joinConsoles(List<String> consoleIds) {
        return (consoleIds == null || consoleIds.isEmpty()) ? "" : String.join(LIST_SEPARATOR, consoleIds);
    }

    private List<String> splitConsoles(String field) {
        if (field == null || field.isBlank()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(field.split(LIST_SEPARATOR)));
    }

    private String toLine(Accessory accessory) {
        String consoles = joinConsoles(accessory.getCompatibleConsoleIds());
        if (accessory instanceof Controller) {
            Controller controller = (Controller) accessory;
            return CONTROLLER_TAG + SEPARATOR + controller.getId() + SEPARATOR
                    + controller.getTitle() + SEPARATOR + controller.getPrice() + SEPARATOR
                    + controller.getStockQuantity() + SEPARATOR + consoles + SEPARATOR
                    + controller.getConnectionType();
        } else if (accessory instanceof Cable) {
            Cable cable = (Cable) accessory;
            return CABLE_TAG + SEPARATOR + cable.getId() + SEPARATOR
                    + cable.getTitle() + SEPARATOR + cable.getPrice() + SEPARATOR
                    + cable.getStockQuantity() + SEPARATOR + consoles + SEPARATOR
                    + cable.getLengthInMeters() + SEPARATOR + cable.getConnectorType();
        } else if (accessory instanceof Memory) {
            Memory memory = (Memory) accessory;
            return MEMORY_TAG + SEPARATOR + memory.getId() + SEPARATOR
                    + memory.getTitle() + SEPARATOR + memory.getPrice() + SEPARATOR
                    + memory.getStockQuantity() + SEPARATOR + consoles + SEPARATOR
                    + memory.getCapacityInGb() + SEPARATOR + memory.getMemoryType();
        }
        throw new IllegalArgumentException(
                "Unsupported accessory type: " + accessory.getClass().getSimpleName());
    }

    private Accessory fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String type = fields[0];
        String id = fields[1];
        String title = fields[2];
        double price = Double.parseDouble(fields[3]);
        int stockQuantity = Integer.parseInt(fields[4]);
        List<String> consoles = splitConsoles(fields[5]);

        if (CONTROLLER_TAG.equals(type)) {
            return new Controller(id, title, price, stockQuantity, consoles, fields[6]);
        } else if (CABLE_TAG.equals(type)) {
            return new Cable(id, title, price, stockQuantity, consoles,
                    Double.parseDouble(fields[6]), fields[7]);
        } else if (MEMORY_TAG.equals(type)) {
            return new Memory(id, title, price, stockQuantity, consoles,
                    Integer.parseInt(fields[6]), fields[7]);
        }
        throw new IllegalArgumentException("Unknown accessory type in file: " + type);
    }
}