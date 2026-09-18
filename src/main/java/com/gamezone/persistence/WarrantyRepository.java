package com.gamezone.persistence;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
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
 * Repository class for managing warranties in the game zone.
 * This class provides methods to save and load all warranties from a CSV file,
 * resolving references to their associated Sale and Product objects.
 */
public class WarrantyRepository {

    private static final String FILE_PATH = "data/warranties.csv";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";
    private static final String BASIC_TAG = "BASIC";
    private static final String EXTENDED_TAG = "EXTENDED";

    private final SaleService saleService;
    private final ProductService productService;

    /**
     * Constructs a WarrantyRepository with the dependencies needed to resolve
     * Sale and Product references when loading stored warranties.
     *
     * @param saleService    the service used to resolve the associated sale by ID
     * @param productService the service used to resolve the associated product by ID
     */
    public WarrantyRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    /**
     * Persists the full list of warranties to the CSV file, overwriting its content.
     *
     * @param warranties the complete list of warranties to persist
     */
    public void saveAll(List<Warranty> warranties) {
        List<String> lines = new ArrayList<>();
        for (Warranty warranty : warranties) {
            lines.add(toLine(warranty));
        }
        writeLines(lines);
    }

    /**
     * Loads all warranties stored in the CSV file.
     *
     * @return the list of warranties found, or an empty list if the file does not exist
     */
    public List<Warranty> loadAll() {
        List<Warranty> warranties = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                warranties.add(fromLine(line));
            }
        }
        return warranties;
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

    private String toLine(Warranty warranty) {
        String tag = (warranty instanceof BasicWarranty) ? BASIC_TAG : EXTENDED_TAG;
        return tag + SEPARATOR + warranty.getId() + SEPARATOR
                + warranty.getSale().getId() + SEPARATOR
                + warranty.getProduct().getId() + SEPARATOR
                + warranty.getStartDate();
    }

    private Warranty fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String type = fields[0];
        String id = fields[1];
        String saleId = fields[2];
        String productId = fields[3];
        LocalDate startDate = LocalDate.parse(fields[4]);

        Sale sale = findSaleById(saleId);
        if (sale == null) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + saleId);
        }

        Product product = findProductById(sale, productId);
        if (product == null) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + productId);
        }

        if (BASIC_TAG.equals(type)) {
            return new BasicWarranty(id, product, sale, startDate);
        } else if (EXTENDED_TAG.equals(type)) {
            return new ExtendedWarranty(id, product, sale, startDate);
        }
        throw new IllegalArgumentException("Unknown warranty type in file: " + type);
    }

    private Sale findSaleById(String saleId) {
        for (Sale sale : saleService.findAllSales()) {
            if (sale.getId().equals(saleId)) {
                return sale;
            }
        }
        return null;
    }

    private Product findProductById(Sale sale, String productId) {
        for (Product product : sale.getProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return productService.findProduct(productId);
    }
}