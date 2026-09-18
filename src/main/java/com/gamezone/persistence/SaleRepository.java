package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Client;
import com.gamezone.model.Person;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing sales in the game zone.
 */
public class SaleRepository {

    private static final String FILE_PATH = "data/sales.txt";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";

    private final PersonRepository personRepository;
    private final ProductRepository productRepository;
    private final AccessoryRepository accessoryRepository;

    /**
     * Constructs a SaleRepository with the required repositories.
     *
     * @param personRepository repository for managing persons
     * @param productRepository repository for managing products
     * @param accessoryRepository repository for managing accessories
     */
    public SaleRepository(
            PersonRepository personRepository,
            ProductRepository productRepository,
            AccessoryRepository accessoryRepository
    ) {
        this.personRepository = personRepository;
        this.productRepository = productRepository;
        this.accessoryRepository = accessoryRepository;
    }

    /**
     * Saves all sales to the persistence file.
     *
     * @param sales sales to save
     */
    public void saveAll(List<Sale> sales) {

        File file = new File(FILE_PATH);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Sale sale : sales) {
                writer.write(toLine(sale));
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error al guardar las ventas.",
                    e
            );
        }
    }

    /**
     * Loads all sales from the persistence file.
     *
     * @return list of stored sales
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
            throw new RuntimeException(
                    "Error al cargar las ventas.",
                    e
            );
        }

        return sales;
    }

    /**
     * Reconstructs a Sale object from a stored line.
     *
     * @param line stored sale information
     * @return reconstructed sale
     */
    private Sale fromLine(String line) {

        String[] parts = line.split(DELIMITER, -1);

        if (parts.length < 6) {
            throw new IllegalArgumentException(
                    "Formato inválido en el archivo de ventas."
            );
        }

        String id = parts[0];
        String date = parts[1];
        String clientId = parts[2];
        String sellerId = parts[3];
        String productIds = parts[4];

        Person clientPerson = personRepository.findById(clientId);
        Person sellerPerson = personRepository.findById(sellerId);

        if (!(clientPerson instanceof Client)) {
            throw new IllegalArgumentException(
                    "Cliente no encontrado con ID: " + clientId
            );
        }

        if (!(sellerPerson instanceof Seller)) {
            throw new IllegalArgumentException(
                    "Vendedor no encontrado con ID: " + sellerId
            );
        }

        Client client = (Client) clientPerson;
        Seller seller = (Seller) sellerPerson;

        List<Product> products = new ArrayList<>();

        if (!productIds.isEmpty()) {

            String[] ids = productIds.split(",");

            for (String productId : ids) {

                Product product = productRepository.findById(productId);

                if (product == null) {
                    Accessory accessory = findAccessoryById(productId);

                    if (accessory == null) {
                        throw new IllegalArgumentException(
                                "Producto o accesorio no encontrado con ID: " + productId
                        );
                    }

                    products.add(accessory);
                } else {
                    products.add(product);
                }
            }
        }

        Sale sale = new Sale(
                id,
                date,
                client,
                seller,
                products
        );

        sale.calculateTotal();

        if (parts.length >= 6) {
            sale.setTotalAmount(Double.parseDouble(parts[5]));
        }

        if (parts.length >= 8) {
            String promotionName = parts[6];

            if (!promotionName.isBlank()) {
                sale.setAppliedPromotionName(promotionName);
            }

            sale.setDiscountAmount(Double.parseDouble(parts[7]));
        }

        if (parts.length >= 9) {
            sale.setExtendedWarrantyCost(
                    Double.parseDouble(parts[8])
            );
        }
        return sale;
    }

    private Accessory findAccessoryById(String accessoryId) {
        List<Accessory> accessories = accessoryRepository.loadAll();

        for (Accessory accessory : accessories) {
            if (accessory.getId().equals(accessoryId)) {
                return accessory;
            }
        }

        return null;
    }

    /**
     * Converts a Sale object into its persistence representation.
     *
     * @param sale sale to convert
     * @return stored representation of the sale
     */
    private String toLine(Sale sale) {

        StringBuilder productIds = new StringBuilder();

        for (Product product : sale.getProducts()) {

            if (productIds.length() > 0) {
                productIds.append(",");
            }

            productIds.append(product.getId());
        }

        String promotionName = sale.getAppliedPromotionName() == null
                ? ""
                : sale.getAppliedPromotionName();

        return sale.getId()
                + SEPARATOR
                + sale.getDate()
                + SEPARATOR
                + sale.getClient().getId()
                + SEPARATOR
                + sale.getSeller().getId()
                + SEPARATOR
                + productIds
                + SEPARATOR
                + sale.getTotalAmount()
                + SEPARATOR
                + promotionName
                + SEPARATOR
                + sale.getDiscountAmount()
                + SEPARATOR
                + sale.getExtendedWarrantyCost();
    }
}