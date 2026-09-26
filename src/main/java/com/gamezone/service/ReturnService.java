package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business rules for registering and querying product returns,
 * and for generating the monthly sales/returns balance report.
 */
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final List<Return> returns;

    /**
     * Constructs a ReturnService with the required dependencies.
     *
     * @param returnRepository the repository for managing returns
     * @param saleService      the service used to validate and locate the original sale
     * @param productService   the service used to restore stock of returned products
     * @param accessoryService the service used to restore stock of returned accessories
     */
    public ReturnService(
            ReturnRepository returnRepository,
            SaleService saleService,
            ProductService productService,
            AccessoryService accessoryService
    ) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.returns = returnRepository.loadAll();
    }

    /**
     * Registers a new return for one or more products of an existing sale.
     *
     * @param saleId     the ID of the original sale
     * @param productIds the IDs of the products being returned
     * @param reason     the reason for the return
     * @return the registered Return object
     */
    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("El motivo de la devolución es obligatorio.");
        }
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Debe indicar al menos un producto a devolver.");
        }

        Sale sale = findSaleById(saleId);
        if (sale == null) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + saleId);
        }

        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException(
                    "No se puede registrar la devolución: han pasado más de 30 días desde la venta.");
        }

        List<Product> returnedProducts = new ArrayList<>();
        for (String productId : productIds) {
            Product product = findProductInSale(sale, productId);
            if (product == null) {
                throw new IllegalArgumentException(
                        "El producto con ID " + productId + " no pertenece a la venta indicada.");
            }
            returnedProducts.add(product);
        }

        String id = generateNextId();
        Return returnTransaction = new Return(id, LocalDate.now(), sale, returnedProducts, reason, 0.0);
        returnTransaction.calculateRefundAmount();

        for (Product product : returnedProducts) {
            if (product instanceof Accessory) {
                accessoryService.restoreStock(product.getId(), 1);
            } else if (productService.findProduct(product.getId()) != null) {
                productService.restoreStock(product.getId(), 1);
            }
        }

        returns.add(returnTransaction);
        returnRepository.saveAll(returns);
        return returnTransaction;
    }

    /**
     * Retrieves all registered returns.
     *
     * @return a list of all returns
     */
    public List<Return> viewAllReturns() {
        return returns;
    }

    /**
     * Retrieves all returns whose original sale belongs to the given customer.
     *
     * @param customerId the ID of the customer
     * @return a list of returns associated with that customer
     */
    public List<Return> viewReturnsByCustomer(String customerId) {
        List<Return> result = new ArrayList<>();
        for (Return r : returns) {
            if (r.getOriginalSale().getClient() != null
                    && r.getOriginalSale().getClient().getId().equals(customerId)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Retrieves all returns associated with a specific sale.
     *
     * @param saleId the ID of the sale
     * @return a list of returns associated with that sale
     */
    public List<Return> viewReturnsBySale(String saleId) {
        List<Return> result = new ArrayList<>();
        for (Return r : returns) {
            if (r.getOriginalSale().getId().equals(saleId)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Generates the net balance for a given month and year, subtracting
     * the total returns from the total sales of that period.
     *
     * @param month the month to evaluate (1-12)
     * @param year  the year to evaluate
     * @return the net balance (sales total minus returns total)
     */

    /**
     * Calculates the total sales for a given month and year.
     *
     * @param month the month to evaluate (1-12)
     * @param year the year to evaluate
     * @return the total sales amount
     */
    public double calculateMonthlySalesTotal(int month, int year) {
        validateMonth(month);

        double salesTotal = 0.0;

        for (Sale sale : saleService.findAllSales()) {
            LocalDate saleDate = parseSaleDate(sale.getDate());

            if (saleDate.getMonthValue() == month
                    && saleDate.getYear() == year) {
                salesTotal += sale.getTotalAmount();
            }
        }

        return salesTotal;
    }

    /**
     * Calculates the total refunds for a given month and year.
     *
     * @param month the month to evaluate (1-12)
     * @param year the year to evaluate
     * @return the total refund amount
     */
    public double calculateMonthlyReturnsTotal(int month, int year) {
        validateMonth(month);

        double returnsTotal = 0.0;

        for (Return returnTransaction : returns) {
            LocalDate returnDate = returnTransaction.getReturnDate();

            if (returnDate.getMonthValue() == month
                    && returnDate.getYear() == year) {
                returnsTotal += returnTransaction.getRefundAmount();
            }
        }

        return returnsTotal;
    }

    /**
     * Generates the net balance for a given month and year.
     *
     * @param month the month to evaluate (1-12)
     * @param year the year to evaluate
     * @return the net balance, calculated as sales minus refunds
     */
    public double generateMonthlyBalance(int month, int year) {
        double salesTotal = calculateMonthlySalesTotal(month, year);
        double returnsTotal = calculateMonthlyReturnsTotal(month, year);

        return salesTotal - returnsTotal;
    }

    /**
     * Parses a sale date stored in the application.
     *
     * @param date the stored sale date
     * @return the parsed date
     */
    private LocalDate parseSaleDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (java.time.format.DateTimeParseException e) {
            return LocalDate.parse(
                    date,
                    java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd")
            );
        }
    }

    /**
     * Validates a month value.
     *
     * @param month the month to validate
     */
    private void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "El mes debe estar entre 1 y 12."
            );
        }
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : saleService.findAllSales()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }

    private Product findProductInSale(Sale sale, String productId) {
        for (Product product : sale.getProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    private String generateNextId() {
        int maxId = 0;
        for (Return r : returns) {
            if (r.getId() != null && r.getId().startsWith("RET-")) {
                try {
                    int numericPart = Integer.parseInt(r.getId().replace("RET-", ""));
                    if (numericPart > maxId) {
                        maxId = numericPart;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return "RET-" + (maxId + 1);
    }
}