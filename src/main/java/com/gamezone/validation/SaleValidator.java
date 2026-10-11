package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Client;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;

import java.util.List;

public class SaleValidator {

    /**
     * Validates the sale data.
     * @param client The client involved in the sale.
     * @param seller The seller involved in the sale.
     * @param products The list of products involved in the sale.
     * @throws InvalidDataException if any of the parameters are invalid.
     * @throws BusinessRuleException if there are business rule violations.
     */
    public static void validateSaleData(Client client, Seller seller, List<Product> products){
       if (client == null) {
           throw new InvalidDataException("cliente", "no puede ser nulo.");
       }
       if (seller == null) {
           throw new InvalidDataException("vendedor", "no puede ser nulo.");
       }
       if (products == null || products.isEmpty()) {
           throw new BusinessRuleException("Debe haber al menos un producto en la venta.");
       }
    }
    /**
     * Validates if the sale exists.
     * @param sale The sale to check.
     * @param saleId The sale identifier.
     * @throws ResourceNotFoundException if the sale does not exist.
     */
    public static void validateSaleExists(Sale sale, String saleId){
        if (sale == null) {
            throw new ResourceNotFoundException("venta", "no puede ser nula.");
        }
    }
}

