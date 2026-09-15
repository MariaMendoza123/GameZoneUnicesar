package com.gamezone.persistence;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing products in the game zone.
 * This class provides methods to save, retrieve, and update products in a text file.
 */
public class ProductRepository {

    private static final String FILE_PATH = "data/products.txt";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";
    private static final String VIDEOGAME_TAG = "VIDEOGAME";
    private static final String CONSOLE_TAG = "CONSOLE";

    /**
     * Saves a product to the repository.
     *
     * @param product the product to be saved
     */
    public void save(Product product) {
        List<String> lines = readLines();
        lines.add(toLine(product));
        writeLines(lines);
    }
    /**
     * Retrieves all products from the repository.
     *
     * @return a list of all products
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                products.add(fromLine(line));
            }
        }
        return products;
    }
    /**
     * Finds a product by its unique identifier.
     *
     * @param id the unique identifier of the product
     * @return the product with the specified id, or null if not found
     */
    public Product findById(String id) {
        for (Product product : findAll()) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
    /**
     * Updates an existing product in the repository.
     *
     * @param product the product with updated information
     */
    public void update(Product product) {
        List<Product> products = findAll();
        List<String> lines = new ArrayList<>();
        for (Product current : products) {
            Product toWrite = current.getId().equals(product.getId()) ? product : current;
            lines.add(toLine(toWrite));
        }
        writeLines(lines);
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

    private String toLine(Product product) {
        if (product instanceof VideoGame) {
            VideoGame videoGame = (VideoGame) product;
            return VIDEOGAME_TAG + SEPARATOR + videoGame.getId() + SEPARATOR
                    + videoGame.getTitle() + SEPARATOR + videoGame.getPrice() + SEPARATOR
                    + videoGame.getStockQuantity() + SEPARATOR + videoGame.getPlatform() + SEPARATOR
                    + videoGame.getGenre() + SEPARATOR + videoGame.getClassification();
        } else if (product instanceof Console) {
            Console console = (Console) product;
            return CONSOLE_TAG + SEPARATOR + console.getId() + SEPARATOR
                    + console.getTitle() + SEPARATOR + console.getPrice() + SEPARATOR
                    + console.getStockQuantity() + SEPARATOR + console.getBrand() + SEPARATOR
                    + console.getModel() + SEPARATOR + console.getGeneration();
        }
        throw new IllegalArgumentException(
                "Unsupported product type: " + product.getClass().getSimpleName());
    }

    private Product fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String type = fields[0];
        String id = fields[1];
        String title = fields[2];
        double price = Double.parseDouble(fields[3]);
        int stockQuantity = Integer.parseInt(fields[4]);

        if (VIDEOGAME_TAG.equals(type)) {
            return new VideoGame(id, title, price, stockQuantity, fields[5], fields[6], fields[7]);
        } else if (CONSOLE_TAG.equals(type)) {
            return new Console(id, title, price, stockQuantity, fields[5], fields[6], fields[7]);
        }
        throw new IllegalArgumentException("Unknown product type in file: " + type);
    }

}
