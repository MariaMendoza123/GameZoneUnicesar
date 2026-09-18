package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing returns in the game zone.
 * This class provides methods to save and load all returns from a CSV file,
 * resolving references to their original Sale and returned Product objects.
 */
public class ReturnRepository {

    private static final String FILE_PATH = "data/returns.csv";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";
    private static final String LIST_SEPARATOR = ",";

    private final SaleService saleService;
    private final ProductService productService;

    /**
     * Constructs a ReturnRepository with the dependencies needed to resolve
     * Sale and Product references when loading stored returns.
     *
     * @param saleService    the service used to resolve the original sale by ID
     * @param productService the service used to resolve returned products by ID
     */
    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    /**
     * Persists the full list of returns to the CSV file, overwriting its content.
     *
     * @param returns the complete list of returns to persist
     */
    public void saveAll(List<Return> returns) {
        List<String> lines = new ArrayList<>();
        for (Return r : returns) {
            lines.add(toLine(r));
        }
        writeLines(lines);
    }

    /**
     * Loads all returns stored in the CSV file.
     *
     * @return the list of returns found, or an empty list if the file does not exist
     */
    public List<Return> loadAll() {
        List<Return> returns = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                returns.add(fromLine(line));
            }
        }
        return returns;
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

    private String toLine(Return r) {
        StringBuilder productIds = new StringBuilder();
        for (Product product : r.getReturnedProducts()) {
            if (productIds.length() > 0) {
                productIds.append(LIST_SEPARATOR);
            }
            productIds.append(product.getId());
        }

        return r.getId() + SEPARATOR
                + r.getReturnDate() + SEPARATOR
                + r.getOriginalSale().getId() + SEPARATOR
                + productIds + SEPARATOR
                + r.getReturnReason() + SEPARATOR
                + r.getRefundAmount();
    }

    private Return fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String id = fields[0];
        LocalDate returnDate = LocalDate.parse(fields[1]);
        String saleId = fields[2];
        String productIdsField = fields[3];
        String reason = fields[4];
        double refundAmount = Double.parseDouble(fields[5]);

        Sale originalSale = findSaleById(saleId);
        if (originalSale == null) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + saleId);
        }

        List<Product> returnedProducts = new ArrayList<>();
        if (!productIdsField.isBlank()) {
            for (String productId : productIdsField.split(LIST_SEPARATOR)) {
                Product product = findProductById(productId);
                if (product != null) {
                    returnedProducts.add(product);
                }
            }
        }

        return new Return(id, returnDate, originalSale, returnedProducts, reason, refundAmount);
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : saleService.findAllSales()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }

    private Product findProductById(String productId) {
        for (Sale sale : saleService.findAllSales()) {
            for (Product product : sale.getProducts()) {
                if (product.getId().equals(productId)) {
                    return product;
                }
            }
        }
        return productService.findProduct(productId);
    }
}