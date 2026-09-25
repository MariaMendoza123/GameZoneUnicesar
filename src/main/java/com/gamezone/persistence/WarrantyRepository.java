package com.gamezone.persistence;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Warranty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing warranties in the game zone.
 * This class only persists and loads the raw identifiers of a warranty
 * (sale ID and product ID); it does not resolve them into domain objects.
 * Resolving those references is the responsibility of WarrantyService,
 * which avoids a circular dependency between SaleService and this class.
 */
public class WarrantyRepository {

    private static final String FILE_PATH = "data/warranties.csv";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";
    private static final String BASIC_TAG = "BASIC";
    private static final String EXTENDED_TAG = "EXTENDED";

    /**
     * Constructs a WarrantyRepository. This class has no dependencies on
     * other services, since it only reads and writes raw identifiers.
     */
    public WarrantyRepository() {
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
     * Loads all warranty records stored in the CSV file, without resolving
     * their Sale and Product references.
     *
     * @return the list of raw warranty records found, or an empty list if the file does not exist
     */
    public List<WarrantyRecord> loadAll() {
        List<WarrantyRecord> records = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                records.add(fromLine(line));
            }
        }
        return records;
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

    private WarrantyRecord fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String type = fields[0];
        String id = fields[1];
        String saleId = fields[2];
        String productId = fields[3];
        LocalDate startDate = LocalDate.parse(fields[4]);

        if (!BASIC_TAG.equals(type) && !EXTENDED_TAG.equals(type)) {
            throw new IllegalArgumentException("Unknown warranty type in file: " + type);
        }

        return new WarrantyRecord(type, id, saleId, productId, startDate);
    }

    /**
     * Raw representation of a stored warranty, holding only identifiers.
     * WarrantyService resolves the saleId and productId into actual
     * Sale and Product objects.
     */
    public static class WarrantyRecord {
        private final String type;
        private final String id;
        private final String saleId;
        private final String productId;
        private final LocalDate startDate;

        /**
         * Constructs a WarrantyRecord with the raw stored fields.
         *
         * @param type      the warranty type tag (BASIC or EXTENDED)
         * @param id        the unique identifier of the warranty
         * @param saleId    the identifier of the associated sale
         * @param productId the identifier of the associated product
         * @param startDate the start date of the warranty coverage
         */
        public WarrantyRecord(String type, String id, String saleId, String productId, LocalDate startDate) {
            this.type = type;
            this.id = id;
            this.saleId = saleId;
            this.productId = productId;
            this.startDate = startDate;
        }

        public String getType() {
            return type;
        }

        public String getId() {
            return id;
        }

        public String getSaleId() {
            return saleId;
        }

        public String getProductId() {
            return productId;
        }

        public LocalDate getStartDate() {
            return startDate;
        }
    }
}