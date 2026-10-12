package com.gamezone.service;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.validation.ProductValidator;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;
    /**
     * Constructs a ProductService with the specified ProductRepository.
     *
     * @param productRepository the repository for managing products
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    /**
     * Registers a new video game in the system.
     *
     * @param title          the title of the video game
     * @param price          the price of the video game
     * @param stockQuantity  the stock quantity of the video game
     * @param platform       the platform of the video game
     * @param genre          the genre of the video game
     * @param classification the classification of the video game
     * @return the registered VideoGame object
     */
    public VideoGame registerVideoGame(String title, double price, int stockQuantity,
                                       String platform, String genre, String classification) {

        String id = generateNextId("VG");   // "CN" en registerConsole
        ProductValidator.validateProductData(id, title, price, stockQuantity);
        if (platform == null || platform.isBlank()) {
            throw new InvalidDataException("plataforma", "es obligatoria");
        }

        if(genre == null || genre.isBlank()) {
            throw new InvalidDataException("El género", "es obligatorio");
        }

        if (classification == null || classification.isBlank()) {
            throw new InvalidDataException("La clasificación", "es obligatoria");
        }

        ;
        VideoGame videoGame = new VideoGame(id, title, price, stockQuantity,
                platform, genre, classification);
        productRepository.save(videoGame);
        return videoGame;
    }

    /**
     * Registers a new console in the system.
     *
     * @param title          the title of the console
     * @param price          the price of the console
     * @param stockQuantity  the stock quantity of the console
     * @param brand          the brand of the console
     * @param model          the model of the console
     * @param generation     the generation of the console
     * @return the registered Console object
     */
    public Console registerConsole(String title, double price, int stockQuantity,
                                   String brand, String model, String generation) {

        String id = generateNextId("CN");
        ProductValidator.validateProductData(id, title, price, stockQuantity);
        if (brand == null || brand.isBlank()) {
            throw new InvalidDataException("La marca", "es obligatoria");
        }

        if(model == null || model.isBlank()) {
            throw new InvalidDataException("El modelo", "es obligatorio");
        }

        if(generation == null || generation.isBlank()) {
            throw new InvalidDataException("La generación", "es obligatoria");
        }


        Console console = new Console(id, title, price, stockQuantity,
                brand, model, generation);
        productRepository.save(console);
        return console;
    }


    /**
     * Updates the stock quantity of a product.
     *
     * @param productId the ID of the product
     * @param quantitySold the quantity of the product sold
     */
    public void updateStock(String productId, int quantitySold) {

        Product product = findProduct(productId);

        ProductValidator.validateProductExists(product, productId);

        if (quantitySold <= 0) {
            throw new InvalidDataException(
                    "cantidad vendida",
                    "debe ser mayor que cero"
            );
        }

        ProductValidator.validateStockAvailability(product, quantitySold);

        int newStock = product.getStockQuantity() - quantitySold;
        product.setStockQuantity(newStock);

        productRepository.update(product);
    }

    /**
     * Restores the stock quantity of a product.
     *
     * @param productId the ID of the product
     * @param quantity the quantity to restore
     */
    public void restoreStock(String productId, int quantity) {

        Product product = findProduct(productId);

        ProductValidator.validateProductExists(product, productId);

        if (quantity <= 0) {
            throw new InvalidDataException(
                    "cantidad a restaurar",
                    "debe ser mayor que cero"
            );
        }

        int newStock = product.getStockQuantity() + quantity;
        product.setStockQuantity(newStock);

        productRepository.update(product);
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product to find
     * @return the found Product object, or null if not found
     */
    public Product findProduct(String id) {
        return productRepository.findById(id);

    }
    /**
     * Retrieves all products from the repository.
     *
     * @return a list of all products
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    private String generateNextId(String prefix) {
        int maxId = 0;
        for (Product product : productRepository.findAll()) {
            if (product.getId() != null && product.getId().startsWith(prefix + "-")) {
                try {
                    int numericPart = Integer.parseInt(product.getId().replace(prefix + "-", ""));
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
