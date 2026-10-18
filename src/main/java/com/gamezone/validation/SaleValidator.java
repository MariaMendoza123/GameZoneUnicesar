package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Client;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;

import java.util.List;

/**
 * Instantiable validator component implementing Validator<Sale>.
 * Verifies preconditions, participant presence, and persistence existence checks for sales.
 */
public class SaleValidator implements Validator<Sale> {

    /**
     * Polymorphic validation contract for Sale entity instances.
     *
     * @param sale The sale entity to validate.
     */
    @Override
    public void validate(Sale sale) {
        if (sale == null) {
            throw new InvalidDataException("venta", "no puede ser nula.");
        }
        validateData(sale.getClient(), sale.getSeller(), sale.getProducts());
    }

    /**
     * Instance method to validate raw sale participant data parameters.
     *
     * @param client   The client involved in the sale.
     * @param seller   The seller involved in the sale.
     * @param products The list of products involved in the sale.
     * @throws InvalidDataException  if any participant is null.
     * @throws BusinessRuleException if the products list is empty.
     */
    public void validateData(Client client, Seller seller, List<Product> products) {
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
     * Instance method to validate if a sale exists.
     *
     * @param sale   The sale to check.
     * @param saleId The sale identifier.
     * @throws ResourceNotFoundException if the sale does not exist.
     */
    public void validateExists(Sale sale, String saleId) {
        if (sale == null) {
            throw new ResourceNotFoundException("venta", "no puede ser nula.");
        }
    }

    private static final SaleValidator DEFAULT_INSTANCE = new SaleValidator();

    /**
     * @deprecated use {@link #validateData(Client, Seller, List)} on an injected instance.
     */
    @Deprecated
    public static void validateSaleData(Client client, Seller seller, List<Product> products) {
        DEFAULT_INSTANCE.validateData(client, seller, products);
    }

    /**
     * @deprecated use {@link #validateExists(Sale, String)} on an injected instance.
     */
    @Deprecated
    public static void validateSaleExists(Sale sale, String saleId) {
        DEFAULT_INSTANCE.validateExists(sale, saleId);
    }
}