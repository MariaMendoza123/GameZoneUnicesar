package com.gamezone.persistence;

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
     * Converts a sale into a text line for file storage.
     *
     * @param sale Sale to convert.
     * @return Text representation of the sale.
     */
    private String toLine(Sale sale) {
        return sale.getId() + SEPARATOR
                + sale.getDate() + SEPARATOR
                + sale.getClient().getId() + SEPARATOR
                + sale.getSeller().getId() + SEPARATOR
                + sale.getTotalAmount();
    }
}