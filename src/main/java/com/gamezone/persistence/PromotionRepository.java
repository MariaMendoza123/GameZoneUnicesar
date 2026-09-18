package com.gamezone.persistence;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing promotions in the game zone.
 * This class provides methods to save and load all promotions from a CSV file.
 */
public class PromotionRepository {

    private static final String FILE_PATH = "data/promotions.csv";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";
    private static final String PERCENTAGE_TAG = "PERCENTAGE";
    private static final String CATEGORY_TAG = "CATEGORY";
    private static final String BULK_TAG = "BULK";

    /**
     * Persists the full list of promotions to the CSV file, overwriting its content.
     *
     * @param promotions the complete list of promotions to persist
     */
    public void saveAll(List<Promotion> promotions) {
        List<String> lines = new ArrayList<>();
        for (Promotion promotion : promotions) {
            lines.add(toLine(promotion));
        }
        writeLines(lines);
    }

    /**
     * Loads all promotions stored in the CSV file.
     *
     * @return the list of promotions found, or an empty list if the file does not exist
     */
    public List<Promotion> loadAll() {
        List<Promotion> promotions = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                promotions.add(fromLine(line));
            }
        }
        return promotions;
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

    private String toLine(Promotion promotion) {
        String common = promotion.getId() + SEPARATOR + promotion.getName() + SEPARATOR
                + promotion.getStartDate() + SEPARATOR + promotion.getEndDate();

        if (promotion instanceof PercentageDiscount) {
            PercentageDiscount p = (PercentageDiscount) promotion;
            return PERCENTAGE_TAG + SEPARATOR + common + SEPARATOR + p.getPercentage();
        } else if (promotion instanceof CategoryDiscount) {
            CategoryDiscount c = (CategoryDiscount) promotion;
            return CATEGORY_TAG + SEPARATOR + common + SEPARATOR
                    + c.getDiscountPercentage() + SEPARATOR + c.getTargetCategory();
        } else if (promotion instanceof BulkPurchaseDiscount) {
            BulkPurchaseDiscount b = (BulkPurchaseDiscount) promotion;
            return BULK_TAG + SEPARATOR + common + SEPARATOR
                    + b.getMinimumQuantity() + SEPARATOR + b.getDiscountPercentage();
        }
        throw new IllegalArgumentException(
                "Unsupported promotion type: " + promotion.getClass().getSimpleName());
    }

    private Promotion fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String type = fields[0];
        String id = fields[1];
        String name = fields[2];
        LocalDate startDate = LocalDate.parse(fields[3]);
        LocalDate endDate = LocalDate.parse(fields[4]);

        if (PERCENTAGE_TAG.equals(type)) {
            double percentage = Double.parseDouble(fields[5]);
            return new PercentageDiscount(id, name, startDate, endDate, percentage);
        } else if (CATEGORY_TAG.equals(type)) {
            double discountPercentage = Double.parseDouble(fields[5]);
            String targetCategory = fields[6];
            return new CategoryDiscount(id, name, startDate, endDate, discountPercentage, targetCategory);
        } else if (BULK_TAG.equals(type)) {
            int minimumQuantity = Integer.parseInt(fields[5]);
            double discountPercentage = Double.parseDouble(fields[6]);
            return new BulkPurchaseDiscount(id, name, startDate, endDate, minimumQuantity, discountPercentage);
        }
        throw new IllegalArgumentException("Unknown promotion type in file: " + type);
    }
}