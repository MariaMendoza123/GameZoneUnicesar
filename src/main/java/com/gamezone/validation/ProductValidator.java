package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Product;

/**
 * Instantiable validator component implementing Validator<Product>.
 * Asserts structural boundaries, inventory availability, product existence, and specific domain attributes.
 */
public class ProductValidator implements Validator<Product> {

    /**
     * Polymorphic validation contract for Product entity instances.
     *
     * @param product The product entity to validate.
     */
    @Override
    public void validate(Product product) {
        if (product == null) {
            throw new InvalidDataException("producto", "no puede ser nulo");
        }
        validateData(product.getId(), product.getTitle(), product.getPrice(), product.getStockQuantity());
    }

    /**
     * Instance method to validate raw product field boundary parameters.
     */
    public void validateData(String id, String title, double price, int stock) {
        if (id == null || id.isBlank()) {
            throw new InvalidDataException("identificador", "no puede ser nulo o vacío");
        }
        if (title == null || title.isBlank()) {
            throw new InvalidDataException("titulo", "no puede ser nulo o vacío");
        }
        if (price <= 0) {
            throw new InvalidDataException("precio", "debe ser mayor a cero");
        }
        if (stock < 0) {
            throw new InvalidDataException("stock", "no puede ser negativo");
        }
    }

    /**
     * Instance method to validate stock availability using Product domain model (Information Expert V-26).
     */
    public void validateStock(Product product, int requestedQuantity) {
        if (product == null) {
            throw new InvalidDataException("producto", "no puede ser nulo");
        }
        if (!product.hasStock(requestedQuantity)) {
            throw new BusinessRuleException("Stock insuficiente para el producto: " + product.getTitle()
                    + ". Stock disponible: " + product.getStockQuantity()
                    + ", cantidad solicitada: " + requestedQuantity);
        }
    }

    /**
     * Instance method to validate that a product exists.
     */
    public void validateExists(Product product, String productId) {
        if (product == null) {
            throw new ResourceNotFoundException("Producto con ID ", productId);
        }
    }

    /**
     * Instance method to validate video game specific attributes.
     */
    public void validateVideoGame(String platform, String genre, String classification) {
        if (platform == null || platform.isBlank()) {
            throw new InvalidDataException("plataforma", "es obligatoria");
        }
        if (genre == null || genre.isBlank()) {
            throw new InvalidDataException("El género", "es obligatorio");
        }
        if (classification == null || classification.isBlank()) {
            throw new InvalidDataException("La clasificación", "es obligatoria");
        }
    }

    /**
     * Instance method to validate console specific attributes.
     */
    public void validateConsole(String brand, String model, String generation) {
        if (brand == null || brand.isBlank()) {
            throw new InvalidDataException("La marca", "es obligatoria");
        }
        if (model == null || model.isBlank()) {
            throw new InvalidDataException("El modelo", "es obligatorio");
        }
        if (generation == null || generation.isBlank()) {
            throw new InvalidDataException("La generación", "es obligatoria");
        }
    }

    // Static delegate methods for backward compatibility with pre-existing caller code
    private static final ProductValidator DEFAULT_INSTANCE = new ProductValidator();

    public static void validateProductData(String id, String title, double price, int stock) {
        DEFAULT_INSTANCE.validateData(id, title, price, stock);
    }

    public static void validateStockAvailability(Product product, int requestedQuantity) {
        DEFAULT_INSTANCE.validateStock(product, requestedQuantity);
    }

    public static void validateProductExists(Product product, String productId) {
        DEFAULT_INSTANCE.validateExists(product, productId);
    }

    public static void validateVideoGameData(String platform, String genre, String classification) {
        DEFAULT_INSTANCE.validateVideoGame(platform, genre, classification);
    }

    public static void validateConsoleData(String brand, String model, String generation) {
        DEFAULT_INSTANCE.validateConsole(brand, model, generation);
    }
}
