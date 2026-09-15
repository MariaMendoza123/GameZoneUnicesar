package com.gamezone.service;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;
import com.gamezone.persistence.ProductRepository;

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
        validateCommonAttributes(title, price, stockQuantity);
        if (platform == null || platform.isBlank()) {
            throw new IllegalArgumentException("La plataforma del videojuego es obligatoria.");
        }

        if(genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("El género del videojuego es obligatorio.");
        }

        if(classification == null || classification.isBlank()) {
            throw new IllegalArgumentException("La clasificación del videojuego es obligatoria.");
        }

        String id = generateNextId("VG");
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
        validateCommonAttributes(title, price, stockQuantity);
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("La marca de la consola es obligatoria.");
        }

        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException("El modelo de la consola es obligatorio.");
        }

        if(generation == null || generation.isBlank()) {
            throw new IllegalArgumentException("La generación de la consola es obligatoria.");
        }

        String id = generateNextId("CN");
        Console console = new Console(id, title, price, stockQuantity,
                brand, model, generation);
        productRepository.save(console);
        return console;
    }

    /**
     * Updates the stock quantity of a product.
     *
     * @param productId     the ID of the product
     * @param quantitySold  the quantity of the product sold
     */
    public void updateStock(String productId, int quantitySold) {
        Product product = findProduct(productId);
        if (product == null) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + productId);
        }
        if (quantitySold <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser mayor que cero.");
        }
        if (product.getStockQuantity() < quantitySold) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + product.getTitle());
        }

        int newStock = product.getStockQuantity() - quantitySold;
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

    private void validateCommonAttributes(String title, double price, int stockQuantity) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El titulo del producto es obligatorio.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("La cantidad en stock no puede ser negativa.");
        }
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
