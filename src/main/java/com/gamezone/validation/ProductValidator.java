package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Product;

public class ProductValidator {

    /**
     * Validates the product data.
     * @param id The product identifier.
     * @param title The product title.
     * @param price The product price.
     * @param stock The product stock quantity.
     * @throws InvalidDataException if any of the parameters are invalid.
     */
    public static void validateProductData(String id, String title, double price, int stock){

        if(id == null || id.isBlank()){
            throw new InvalidDataException("identificador", "no puede ser nulo o vacío");
        }
        if(title == null || title.isBlank()){
            throw new InvalidDataException("titulo", "no puede ser nulo o vacío");
        }
        if(price <= 0){
            throw new InvalidDataException("precio", "debe ser mayor a cero");
        }
        if(stock < 0){
            throw new InvalidDataException("stock", "no puede ser negativo");
        }
    }

    /**
     * Validates if the requested quantity of a product is available in stock.
     * @param product The product to check.
     * @param requestedQuantity The quantity requested.
     */
    public static void validateStockAvailability(Product product, int requestedQuantity){

        if(product.getStockQuantity() < requestedQuantity){
            throw new BusinessRuleException("Stock insuficiente para el producto: " + product.getTitle() + ". Stock disponible: "
                    + product.getStockQuantity() + ", cantidad solicitada: " + requestedQuantity);
        }
    }

    /**
     * Validates if the product exists.
     * @param product The product to check.
     * @param productId The product identifier.
     */
    public static void validateProductExists(Product product, String productId){
        if(product == null) {
            throw new ResourceNotFoundException("Producto con ID ", productId);
        }
    }
}
