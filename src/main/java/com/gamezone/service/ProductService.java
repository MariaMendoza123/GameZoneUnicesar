package com.gamezone.service;

import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.VideoGame;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.validation.ProductValidator;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    public ProductService(ProductRepository productRepository) {
        this(productRepository, new ProductValidator());
    }

    public Product registerVideoGame(String title, double price, int stockQuantity,
                                     String platform, String genre, String classification) {

        String id = productRepository.generateNextId("VG");
        productValidator.validateData(id, title, price, stockQuantity);
        productValidator.validateVideoGame(platform, genre, classification);

        VideoGame videoGame = new VideoGame(id, title, price, stockQuantity,
                platform, genre, classification);
        productRepository.save(videoGame);
        return videoGame;
    }

    public Product registerConsole(String title, double price, int stockQuantity,
                                   String brand, String model, String generation) {

        String id = productRepository.generateNextId("CN");
        productValidator.validateData(id, title, price, stockQuantity);
        productValidator.validateConsole(brand, model, generation);

        Console console = new Console(id, title, price, stockQuantity,
                brand, model, generation);
        productRepository.save(console);
        return console;
    }

    public void updateStock(String productId, int quantitySold) {

        Product product = findProduct(productId);

        productValidator.validateExists(product, productId);

        if (quantitySold <= 0) {
            throw new InvalidDataException(
                    "cantidad vendida",
                    "debe ser mayor que cero"
            );
        }

        productValidator.validateStock(product, quantitySold);

        product.reduceStock(quantitySold);

        productRepository.update(product);
    }

    public void restoreStock(String productId, int quantity) {

        Product product = findProduct(productId);

        productValidator.validateExists(product, productId);

        if (quantity <= 0) {
            throw new InvalidDataException(
                    "cantidad a restaurar",
                    "debe ser mayor que cero"
            );
        }
        product.increaseStock(quantity);

        productRepository.update(product);
    }

    public Product findProduct(String id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}