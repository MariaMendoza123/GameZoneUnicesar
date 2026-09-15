package com.gamezone.ui;

import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console menu for interacting with the GameZone application.
 */
public class ConsoleMenu {

    private final Scanner scanner;
    private final PersonService personService;
    private final ProductService productService;
    private final SaleService saleService;

    /**
     * Constructs the console menu with the required services.
     *
     * @param personService service for managing persons
     * @param productService service for managing products
     * @param saleService service for managing sales
     */
    public ConsoleMenu(
            PersonService personService,
            ProductService productService,
            SaleService saleService
    ) {
        scanner = new Scanner(System.in);
        this.personService = personService;
        this.productService = productService;
        this.saleService = saleService;
    }

    /**
     * Displays the main menu options.
     */
    public void showMainMenu() {
        System.out.println("=================================");
        System.out.println("        GAMEZONE UNICESAR");
        System.out.println("=================================");
        System.out.println("1. Gestión de productos");
        System.out.println("2. Gestión de personas");
        System.out.println("3. Gestión de ventas");
        System.out.println("4. Salir");
        System.out.println("=================================");
    }

    /**
     * Reads an option selected by the user.
     *
     * @return selected menu option
     */
    public int readOption() {
        System.out.print("Seleccione una opción: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Handles the main menu options.
     */
    public void handleMainMenu() {

        int option;

        do {
            showMainMenu();
            option = readOption();

            switch (option) {
                case 1:
                    handleProductMenu();
                    break;

                case 2:
                    handlePersonMenu();
                    break;

                case 3:
                    handleSaleMenu();
                    break;

                case 4:
                    System.out.println(
                            "Saliendo del sistema..."
                    );
                    break;

                default:
                    System.out.println(
                            "Opción inválida."
                    );
            }

        } while (option != 4);
    }

    /**
     * Displays the product management menu.
     */
    public void showProductMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("      GESTIÓN DE PRODUCTOS");
        System.out.println("=================================");
        System.out.println("1. Registrar videojuego");
        System.out.println("2. Registrar consola");
        System.out.println("3. Consultar productos");
        System.out.println("4. Volver al menú principal");
        System.out.println("=================================");
    }

    /**
     * Handles the product management menu.
     */
    public void handleProductMenu() {

        int option;

        do {
            showProductMenu();
            option = readOption();

            switch (option) {
                case 1:
                    System.out.println(
                            "Registrar videojuego."
                    );
                    break;

                case 2:
                    System.out.println(
                            "Registrar consola."
                    );
                    break;

                case 3:
                    System.out.println(
                            "Consultar productos."
                    );
                    break;

                case 4:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println(
                            "Opción inválida."
                    );
            }

        } while (option != 4);
    }

    /**
     * Displays the person management menu.
     */
    public void showPersonMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("       GESTIÓN DE PERSONAS");
        System.out.println("=================================");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar vendedor");
        System.out.println("3. Consultar personas");
        System.out.println("4. Volver al menú principal");
        System.out.println("=================================");
    }

    /**
     * Handles the person management menu.
     */
    public void handlePersonMenu() {

        int option;

        do {
            showPersonMenu();
            option = readOption();

            switch (option) {
                case 1:
                    System.out.println(
                            "Registrar cliente."
                    );
                    break;

                case 2:
                    System.out.println(
                            "Registrar vendedor."
                    );
                    break;

                case 3:
                    System.out.println(
                            "Consultar personas."
                    );
                    break;

                case 4:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println(
                            "Opción inválida."
                    );
            }

        } while (option != 4);
    }

    /**
     * Displays the sales management menu.
     */
    public void showSaleMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("        GESTIÓN DE VENTAS");
        System.out.println("=================================");
        System.out.println("1. Registrar venta");
        System.out.println("2. Consultar historial de ventas");
        System.out.println("3. Consultar compras de un cliente");
        System.out.println("4. Consultar ventas de un vendedor");
        System.out.println("5. Volver al menú principal");
        System.out.println("=================================");
    }

    /**
     * Handles the sales management menu.
     */
    public void handleSaleMenu() {

        int option;

        do {
            showSaleMenu();
            option = readOption();

            switch (option) {
                case 1:
                    registerSale();
                    break;

                case 2:
                    showSalesHistory();
                    break;

                case 3:
                    showClientPurchases();
                    break;

                case 4:
                    showSellerSales();
                    break;

                case 5:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println(
                            "Opción inválida."
                    );
            }

        } while (option != 5);
    }

    /**
     * Registers a new sale through the console.
     */
    private void registerSale() {

        System.out.println();
        System.out.println("===== REGISTRAR VENTA =====");

        System.out.print("ID de la venta: ");
        String id = scanner.nextLine();

        System.out.print("Fecha de la venta: ");
        String date = scanner.nextLine();

        System.out.print("ID del cliente: ");
        String clientId = scanner.nextLine();

        System.out.print("ID del vendedor: ");
        String sellerId = scanner.nextLine();

        System.out.print("ID del producto: ");
        String productId = scanner.nextLine();

        Product product =
                productService.findProduct(productId);

        if (product == null) {
            System.out.println(
                    "Producto no encontrado con ID: "
                            + productId
            );
            return;
        }

        List<Product> products = new ArrayList<>();
        products.add(product);

        try {

            Sale sale = saleService.registerSale(
                    id,
                    date,
                    clientId,
                    sellerId,
                    products
            );

            System.out.println();
            System.out.println(
                    "Venta registrada correctamente."
            );
            System.out.println(
                    "ID: " + sale.getId()
            );
            System.out.println(
                    "Total: $" + sale.getTotalAmount()
            );

        } catch (IllegalArgumentException
                 | IllegalStateException e) {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la venta."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the complete sales history.
     */
    private void showSalesHistory() {

        System.out.println();
        System.out.println(
                "===== HISTORIAL DE VENTAS ====="
        );

        List<Sale> sales =
                saleService.findAllSales();

        if (sales.isEmpty()) {
            System.out.println(
                    "No hay ventas registradas."
            );
            return;
        }

        for (Sale sale : sales) {
            printSale(sale);
        }
    }

    /**
     * Displays purchases made by a specific client.
     */
    private void showClientPurchases() {

        System.out.println();
        System.out.println(
                "===== COMPRAS DEL CLIENTE ====="
        );

        System.out.print("ID del cliente: ");
        String clientId = scanner.nextLine();

        List<Sale> sales =
                saleService.findSalesByClient(clientId);

        if (sales.isEmpty()) {
            System.out.println(
                    "No se encontraron compras para este cliente."
            );
            return;
        }

        for (Sale sale : sales) {
            printSale(sale);
        }
    }

    /**
     * Displays sales handled by a specific seller.
     */
    private void showSellerSales() {

        System.out.println();
        System.out.println(
                "===== VENTAS DEL VENDEDOR ====="
        );

        System.out.print("ID del vendedor: ");
        String sellerId = scanner.nextLine();

        List<Sale> sales =
                saleService.findSalesBySeller(sellerId);

        if (sales.isEmpty()) {
            System.out.println(
                    "No se encontraron ventas para este vendedor."
            );
            return;
        }

        for (Sale sale : sales) {
            printSale(sale);
        }
    }

    /**
     * Prints the information of a sale.
     *
     * @param sale sale to display
     */
    private void printSale(Sale sale) {

        System.out.println();
        System.out.println("-----------------------------");
        System.out.println(
                "Venta: " + sale.getId()
        );
        System.out.println(
                "Fecha: " + sale.getDate()
        );
        System.out.println(
                "Cliente: "
                        + sale.getClient().getName()
        );
        System.out.println(
                "Vendedor: "
                        + sale.getSeller().getName()
        );

        System.out.println("Productos:");

        for (Product product : sale.getProducts()) {
            System.out.println(
                    "- " + product.getTitle()
                            + " | $" + product.getPrice()
            );
        }

        System.out.println(
                "Total: $" + sale.getTotalAmount()
        );
        System.out.println("-----------------------------");
    }
}
