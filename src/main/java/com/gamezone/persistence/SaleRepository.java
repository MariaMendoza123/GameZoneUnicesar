package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing sales in the game zone.
 */
public class SaleRepository {

    private static final String FILE_PATH = "data/sales.txt";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";

    /**
     * Saves all sales to the sales data file.
     *
     * @param sales List of sales to save.
     */
    public void saveAll(List<Sale> sales) {
        File file = new File(FILE_PATH);

        try {
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Sale sale : sales) {
                    writer.write(toLine(sale));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving sales.", e);
        }
    }

    /**
     * Retrieves all sales stored in the sales data file.
     *
     * @return List of stored sales.
     */
    public List<Sale> findAll() {
        List<Sale> sales = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return sales;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    sales.add(fromLine(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading sales.", e);
        }

        return sales;
    }

    /**
     * Converts a stored text line into a Sale object.
     *
     * @param line Text representation of a sale.
     * @return Sale created from the stored information.
     */
    private Sale fromLine(String line) {
        String[] parts = line.split(DELIMITER);

        String id = parts[0];
        String date = parts[1];

        return new Sale(
                id,
                date,
                null,
                null,
                new ArrayList<>()
        );
    }

    /**
     * Converts a sale into a text line for file storage.
     *
     * @param sale Sale to convert.
     * @return Text representation of the sale.
     */
    private String toLine(Sale sale) {
        StringBuilder productIds = new StringBuilder();

        for (Product product : sale.getProducts()) {
            if (productIds.length() > 0) {
                productIds.append(",");
            }

            productIds.append(product.getId());
        }

        return sale.getId() + SEPARATOR
                + sale.getDate() + SEPARATOR
                + sale.getClient().getId() + SEPARATOR
                + sale.getSeller().getId() + SEPARATOR
                + productIds + SEPARATOR
                + sale.getTotalAmount();
    }
}