package com.gamezone.ui;

import com.gamezone.model.Accessory;
import com.gamezone.model.Controller;
import com.gamezone.model.Cable;
import com.gamezone.model.Memory;
import com.gamezone.model.Person;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.ReturnService;
import com.gamezone.model.Promotion;
import com.gamezone.model.Return;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Console menu for interacting with the GameZone application.
 */
public class ConsoleMenu {

    private final Scanner scanner;
    private final PersonService personService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final SaleService saleService;
    private final PromotionService promotionService;
    private final ReturnService returnService;

    /**
     * Constructs the console menu with the required services.
     *
     * @param personService service for managing persons
     * @param productService service for managing products
     * @param accessoryService service for managing accessories
     * @param saleService service for managing sales
     * @param promotionService service for managing promotions
     * @param returnService service for managing returns
     */
    public ConsoleMenu(
            PersonService personService,
            ProductService productService,
            AccessoryService accessoryService,
            SaleService saleService,
            PromotionService promotionService,
            ReturnService returnService

    ) {
        scanner = new Scanner(System.in);
        this.personService = personService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.saleService = saleService;
        this.promotionService = promotionService;
        this.returnService = returnService;
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
        System.out.println("3. Gestión de accesorios");
        System.out.println("4. Gestión de ventas");
        System.out.println("5. Gestión de promociones");
        System.out.println("6. Gestión de devoluciones");
        System.out.println("7. Salir");
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
                    handleAccessoryMenu();
                    break;

                case 4:
                    handleSaleMenu();
                    break;

                case 5:
                    handlePromotionMenu();
                    break;

                case 6:
                    handleReturnMenu();
                    break;

                case 7:
                    System.out.println("Saliendo del sistema");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 7);
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
                    registerVideoGame();
                    break;

                case 2:
                    registerConsole();
                    break;

                case 3:
                    showProducts();
                    break;

                case 4:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 4);
    }

    /**
     * Registers a new video game through the console.
     */
    private void registerVideoGame() {

        System.out.println();
        System.out.println("===== REGISTRAR VIDEOJUEGO =====");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Plataforma: ");
        String platform = scanner.nextLine();

        System.out.print("Género: ");
        String genre = scanner.nextLine();

        System.out.print("Clasificación: ");
        String classification = scanner.nextLine();

        try {
            Product product = productService.registerVideoGame(
                    title,
                    price,
                    stockQuantity,
                    platform,
                    genre,
                    classification
            );

            System.out.println();
            System.out.println(
                    "Videojuego registrado correctamente."
            );
            System.out.println("ID: " + product.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar el videojuego."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a new console through the console.
     */
    private void registerConsole() {

        System.out.println();
        System.out.println("===== REGISTRAR CONSOLA =====");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Marca: ");
        String brand = scanner.nextLine();

        System.out.print("Modelo: ");
        String model = scanner.nextLine();

        System.out.print("Generación: ");
        String generation = scanner.nextLine();

        try {
            Product product = productService.registerConsole(
                    title,
                    price,
                    stockQuantity,
                    brand,
                    model,
                    generation
            );

            System.out.println();
            System.out.println(
                    "Consola registrada correctamente."
            );
            System.out.println("ID: " + product.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar la consola."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all registered products.
     */
    private void showProducts() {

        System.out.println();
        System.out.println("===== PRODUCTOS REGISTRADOS =====");

        List<Product> products =
                productService.findAllProducts();

        if (products.isEmpty()) {
            System.out.println(
                    "No hay productos registrados."
            );
            return;
        }

        for (Product product : products) {
            System.out.println();
            System.out.println("-----------------------------");
            System.out.println("ID: " + product.getId());
            System.out.println("Título: " + product.getTitle());
            System.out.println("Precio: $" + product.getPrice());
            System.out.println(
                    "Stock: " + product.getStockQuantity()
            );
            System.out.println("-----------------------------");
        }
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
                    registerClient();
                    break;

                case 2:
                    registerSeller();
                    break;

                case 3:
                    showPersons();
                    break;

                case 4:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 4);
    }

    /**
     * Registers a new client through the console.
     */
    private void registerClient() {

        System.out.println();
        System.out.println("===== REGISTRAR CLIENTE =====");

        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Teléfono: ");
        String phone = scanner.nextLine();

        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine();

        try {
            Person person = personService.registerClient(
                    name,
                    phone,
                    email
            );

            System.out.println();
            System.out.println(
                    "Cliente registrado correctamente."
            );
            System.out.println("ID: " + person.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar el cliente."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a new seller through the console.
     */
    private void registerSeller() {

        System.out.println();
        System.out.println("===== REGISTRAR VENDEDOR =====");

        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Teléfono: ");
        String phone = scanner.nextLine();

        System.out.print("Código de empleado: ");
        String employeeCode = scanner.nextLine();

        System.out.print("Turno de trabajo: ");
        String shift = scanner.nextLine();

        try {
            Person person = personService.registerSeller(
                    name,
                    phone,
                    employeeCode,
                    shift
            );

            System.out.println();
            System.out.println(
                    "Vendedor registrado correctamente."
            );
            System.out.println("ID: " + person.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar el vendedor."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all registered persons.
     */
    private void showPersons() {

        System.out.println();
        System.out.println("===== PERSONAS REGISTRADAS =====");

        List<Person> persons =
                personService.findAllPersons();

        if (persons.isEmpty()) {
            System.out.println(
                    "No hay personas registradas."
            );
            return;
        }

        for (Person person : persons) {
            System.out.println();
            System.out.println("-----------------------------");
            System.out.println("ID: " + person.getId());
            System.out.println("Nombre: " + person.getName());
            System.out.println("Teléfono: " + person.getPhone());
            System.out.println("-----------------------------");
        }
    }

    /**
     * Displays the accessory management menu.
     */
    public void showAccessoryMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("      GESTIÓN DE ACCESORIOS");
        System.out.println("=================================");
        System.out.println("1. Registrar nuevo control");
        System.out.println("2. Registrar nuevo cable");
        System.out.println("3. Registrar nueva memoria");
        System.out.println("4. Listar todos los accesorios");
        System.out.println("5. Listar accesorios por tipo");
        System.out.println("6. Consultar accesorios compatibles con una consola");
        System.out.println("7. Volver al menú principal");
        System.out.println("=================================");
    }

    /**
     * Handles the accessory management menu.
     */
    public void handleAccessoryMenu() {

        int option;

        do {
            showAccessoryMenu();
            option = readOption();

            switch (option) {
                case 1:
                    registerController();
                    break;

                case 2:
                    registerCable();
                    break;

                case 3:
                    registerMemory();
                    break;

                case 4:
                    showAllAccessories();
                    break;

                case 5:
                    showAccessoriesByType();
                    break;

                case 6:
                    showCompatibleAccessories();
                    break;

                case 7:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 7);
    }

    /**
     * Registers a new controller through the console.
     */
    private void registerController() {

        System.out.println();
        System.out.println("===== REGISTRAR CONTROL =====");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        List<String> compatibleConsoleIds =
                readCompatibleConsoleIds();

        System.out.print("Tipo de conexión (Wired/Wireless): ");
        String connectionType = scanner.nextLine();

        try {
            Controller controller =
                    accessoryService.registerController(
                            title,
                            price,
                            stockQuantity,
                            compatibleConsoleIds,
                            connectionType
                    );

            System.out.println();
            System.out.println(
                    "Control registrado correctamente."
            );
            System.out.println("ID: " + controller.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar el control."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a new cable through the console.
     */
    private void registerCable() {

        System.out.println();
        System.out.println("===== REGISTRAR CABLE =====");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        List<String> compatibleConsoleIds =
                readCompatibleConsoleIds();

        System.out.print("Longitud en metros: ");
        double lengthInMeters =
                Double.parseDouble(scanner.nextLine());

        System.out.print("Tipo de conector: ");
        String connectorType = scanner.nextLine();

        try {
            Cable cable =
                    accessoryService.registerCable(
                            title,
                            price,
                            stockQuantity,
                            compatibleConsoleIds,
                            lengthInMeters,
                            connectorType
                    );

            System.out.println();
            System.out.println(
                    "Cable registrado correctamente."
            );
            System.out.println("ID: " + cable.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar el cable."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a new memory through the console.
     */
    private void registerMemory() {

        System.out.println();
        System.out.println("===== REGISTRAR MEMORIA =====");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad en stock: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        List<String> compatibleConsoleIds =
                readCompatibleConsoleIds();

        System.out.print("Capacidad en GB: ");
        int capacityInGb =
                Integer.parseInt(scanner.nextLine());

        System.out.print("Tipo de memoria: ");
        String memoryType = scanner.nextLine();

        try {
            Memory memory =
                    accessoryService.registerMemory(
                            title,
                            price,
                            stockQuantity,
                            compatibleConsoleIds,
                            capacityInGb,
                            memoryType
                    );

            System.out.println();
            System.out.println(
                    "Memoria registrada correctamente."
            );
            System.out.println("ID: " + memory.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println(
                    "No se pudo registrar la memoria."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads a list of compatible console IDs from the console.
     *
     * @return list of compatible console IDs
     */
    private List<String> readCompatibleConsoleIds() {

        System.out.print(
                "IDs de consolas compatibles separados por coma: "
        );

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                Arrays.asList(input.split("\\s*,\\s*"))
        );
    }

    /**
     * Displays all registered accessories.
     */
    private void showAllAccessories() {

        System.out.println();
        System.out.println(
                "===== ACCESORIOS REGISTRADOS ====="
        );

        List<Accessory> accessories =
                accessoryService.listAllAccessories();

        if (accessories.isEmpty()) {
            System.out.println(
                    "No hay accesorios registrados."
            );
            return;
        }

        for (Accessory accessory : accessories) {
            printAccessory(accessory);
        }
    }

    /**
     * Displays accessories filtered by type.
     */
    private void showAccessoriesByType() {

        System.out.println();
        System.out.println(
                "===== ACCESORIOS POR TIPO ====="
        );

        System.out.println("1. Controles");
        System.out.println("2. Cables");
        System.out.println("3. Memorias");

        int option = readOption();

        String type;

        switch (option) {
            case 1:
                type = "CONTROLLER";
                break;

            case 2:
                type = "CABLE";
                break;

            case 3:
                type = "MEMORY";
                break;

            default:
                System.out.println("Opción inválida.");
                return;
        }

        List<Accessory> accessories =
                accessoryService.listAccessoriesByType(type);

        if (accessories.isEmpty()) {
            System.out.println(
                    "No hay accesorios registrados de este tipo."
            );
            return;
        }

        for (Accessory accessory : accessories) {
            printAccessory(accessory);
        }
    }

    /**
     * Displays accessories compatible with a specific console.
     */
    private void showCompatibleAccessories() {

        System.out.println();
        System.out.println(
                "===== ACCESORIOS COMPATIBLES ====="
        );

        System.out.print("ID de la consola: ");
        String consoleId = scanner.nextLine();

        List<Accessory> accessories =
                accessoryService.findAccessoriesCompatibleWith(
                        consoleId
                );

        if (accessories.isEmpty()) {
            System.out.println(
                    "No se encontraron accesorios compatibles con la consola."
            );
            return;
        }

        for (Accessory accessory : accessories) {
            printAccessory(accessory);
        }
    }

    /**
     * Prints the information of an accessory.
     *
     * @param accessory accessory to display
     */
    private void printAccessory(Accessory accessory) {

        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("ID: " + accessory.getId());
        System.out.println("Título: " + accessory.getTitle());
        System.out.println("Precio: $" + accessory.getPrice());
        System.out.println(
                "Stock: " + accessory.getStockQuantity()
        );

        if (accessory instanceof Controller) {
            Controller controller =
                    (Controller) accessory;

            System.out.println(
                    "Tipo de conexión: "
                            + controller.getConnectionType()
            );

        } else if (accessory instanceof Cable) {
            Cable cable = (Cable) accessory;

            System.out.println(
                    "Longitud: "
                            + cable.getLengthInMeters()
                            + " metros"
            );
            System.out.println(
                    "Tipo de conector: "
                            + cable.getConnectorType()
            );

        } else if (accessory instanceof Memory) {
            Memory memory = (Memory) accessory;

            System.out.println(
                    "Capacidad: "
                            + memory.getCapacityInGb()
                            + " GB"
            );
            System.out.println(
                    "Tipo de memoria: "
                            + memory.getMemoryType()
            );
        }

        System.out.println(
                "Consolas compatibles: "
                        + String.join(
                        ", ",
                        accessory.getCompatibleConsoleIds()
                )
        );

        System.out.println("-----------------------------");
    }

/**
     * Displays the promotion management menu.
     */
    public void showPromotionMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("     GESTIÓN DE PROMOCIONES");
        System.out.println("=================================");
        System.out.println("1. Registrar promoción por porcentaje");
        System.out.println("2. Registrar promoción por categoría");
        System.out.println("3. Registrar promoción por volumen");
        System.out.println("4. Listar todas las promociones");
        System.out.println("5. Listar promociones vigentes");
        System.out.println("6. Volver al menú principal");
        System.out.println("=================================");
    }

    /**
     * Handles the promotion management menu.
     */
    public void handlePromotionMenu() {
        int option;

        do {
            showPromotionMenu();
            option = readOption();

            switch (option) {
                case 1:
                    registerPercentagePromotion();
                    break;

                case 2:
                    registerCategoryPromotion();
                    break;

                case 3:
                    registerBulkPromotion();
                    break;

                case 4:
                    showAllPromotions();
                    break;

                case 5:
                    showActivePromotions();
                    break;

                case 6:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 6);
    }

    /**
     * Registers a percentage promotion through the console.
     */
    private void registerPercentagePromotion() {
        System.out.println();
        System.out.println("===== REGISTRAR PROMOCIÓN POR PORCENTAJE =====");

        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Porcentaje de descuento: ");
        double percentage = Double.parseDouble(scanner.nextLine());

        try {
            Promotion promotion = promotionService.registerPercentageDiscount(
                    name,
                    startDate,
                    endDate,
                    percentage
            );

            System.out.println();
            System.out.println("Promoción registrada correctamente.");
            System.out.println("ID: " + promotion.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("No se pudo registrar la promoción.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a category promotion through the console.
     */
    private void registerCategoryPromotion() {
        System.out.println();
        System.out.println("===== REGISTRAR PROMOCIÓN POR CATEGORÍA =====");

        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Porcentaje de descuento: ");
        double percentage = Double.parseDouble(scanner.nextLine());

        System.out.print("Categoría (VIDEOGAME/CONSOLE): ");
        String targetCategory = scanner.nextLine().trim().toUpperCase();

        try {
            Promotion promotion = promotionService.registerCategoryDiscount(
                    name,
                    startDate,
                    endDate,
                    percentage,
                    targetCategory
            );

            System.out.println();
            System.out.println("Promoción registrada correctamente.");
            System.out.println("ID: " + promotion.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("No se pudo registrar la promoción.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registers a bulk purchase promotion through the console.
     */
    private void registerBulkPromotion() {
        System.out.println();
        System.out.println("===== REGISTRAR PROMOCIÓN POR VOLUMEN =====");

        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Cantidad mínima de productos: ");
        int minimumQuantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Porcentaje de descuento: ");
        double percentage = Double.parseDouble(scanner.nextLine());

        try {
            Promotion promotion = promotionService.registerBulkPurchaseDiscount(
                    name,
                    startDate,
                    endDate,
                    minimumQuantity,
                    percentage
            );

            System.out.println();
            System.out.println("Promoción registrada correctamente.");
            System.out.println("ID: " + promotion.getId());

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("No se pudo registrar la promoción.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all registered promotions.
     */
    private void showAllPromotions() {
        System.out.println();
        System.out.println("===== PROMOCIONES REGISTRADAS =====");

        List<Promotion> promotions = promotionService.listAllPromotions();

        if (promotions.isEmpty()) {
            System.out.println("No hay promociones registradas.");
            return;
        }

        for (Promotion promotion : promotions) {
            printPromotion(promotion);
        }
    }

    /**
     * Displays currently active promotions.
     */
    private void showActivePromotions() {
        System.out.println();
        System.out.println("===== PROMOCIONES VIGENTES =====");

        List<Promotion> promotions = promotionService.listActivePromotions();

        if (promotions.isEmpty()) {
            System.out.println("No hay promociones vigentes.");
            return;
        }

        for (Promotion promotion : promotions) {
            printPromotion(promotion);
        }
    }

    /**
     * Prints promotion information.
     *
     * @param promotion promotion to display
     */
    private void printPromotion(Promotion promotion) {
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("ID: " + promotion.getId());
        System.out.println("Nombre: " + promotion.getName());
        System.out.println("Tipo: " + promotion.getClass().getSimpleName());
        System.out.println("Fecha de inicio: " + promotion.getStartDate());
        System.out.println("Fecha de fin: " + promotion.getEndDate());
        System.out.println("-----------------------------");
    }
    /**
     * Displays the return management menu.
     */
    public void showReturnMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("      GESTIÓN DE DEVOLUCIONES");
        System.out.println("=================================");
        System.out.println("1. Registrar devolución");
        System.out.println("2. Consultar todas las devoluciones");
        System.out.println("3. Consultar devoluciones por cliente");
        System.out.println("4. Consultar devoluciones por venta");
        System.out.println("5. Consultar balance mensual");
        System.out.println("6. Volver al menú principal");
        System.out.println("=================================");
    }

    /**
     * Handles the return management menu.
     */
    public void handleReturnMenu() {

        int option;

        do {
            showReturnMenu();
            option = readOption();

            switch (option) {

                case 1:
                    registerReturn();
                    break;

                case 2:
                    showAllReturns();
                    break;

                case 3:
                    showReturnsByCustomer();
                    break;

                case 4:
                    showReturnsBySale();
                    break;

                case 5:
                    showMonthlyBalance();
                    break;

                case 6:
                    System.out.println(
                            "Volviendo al menú principal..."
                    );
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (option != 6);
    }

    /**
     * Registers a new return through the console.
     */
    private void registerReturn() {

        System.out.println();
        System.out.println("===== REGISTRAR DEVOLUCIÓN =====");

        System.out.print("ID de la venta original: ");
        String saleId = scanner.nextLine();

        System.out.print(
                "IDs de productos a devolver separados por coma: "
        );
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println(
                    "Debe indicar al menos un producto a devolver."
            );
            return;
        }

        List<String> productIds =
                new ArrayList<>(
                        Arrays.asList(
                                input.split("\\s*,\\s*")
                        )
                );

        System.out.print("Motivo de la devolución: ");
        String reason = scanner.nextLine();

        try {

            Return returnTransaction =
                    returnService.registerReturn(
                            saleId,
                            productIds,
                            reason
                    );

            System.out.println();
            System.out.println(
                    "Devolución registrada correctamente."
            );

            System.out.println(
                    returnTransaction.generateReturnReceipt()
            );

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "No se pudo registrar la devolución."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all registered returns.
     */
    private void showAllReturns() {

        System.out.println();
        System.out.println(
                "===== DEVOLUCIONES REGISTRADAS ====="
        );

        List<Return> returns =
                returnService.viewAllReturns();

        if (returns.isEmpty()) {
            System.out.println(
                    "No hay devoluciones registradas."
            );
            return;
        }

        for (Return returnTransaction : returns) {
            printReturn(returnTransaction);
        }
    }

    /**
     * Displays returns associated with a specific customer.
     */
    private void showReturnsByCustomer() {

        System.out.println();
        System.out.println(
                "===== DEVOLUCIONES DEL CLIENTE ====="
        );

        System.out.print("ID del cliente: ");
        String customerId = scanner.nextLine();

        List<Return> returns =
                returnService.viewReturnsByCustomer(customerId);

        if (returns.isEmpty()) {
            System.out.println(
                    "No se encontraron devoluciones para este cliente."
            );
            return;
        }

        for (Return returnTransaction : returns) {
            printReturn(returnTransaction);
        }
    }

    /**
     * Displays returns associated with a specific sale.
     */
    private void showReturnsBySale() {

        System.out.println();
        System.out.println(
                "===== DEVOLUCIONES DE LA VENTA ====="
        );

        System.out.print("ID de la venta: ");
        String saleId = scanner.nextLine();

        List<Return> returns =
                returnService.viewReturnsBySale(saleId);

        if (returns.isEmpty()) {
            System.out.println(
                    "No se encontraron devoluciones para esta venta."
            );
            return;
        }

        for (Return returnTransaction : returns) {
            printReturn(returnTransaction);
        }
    }

    /**
     * Displays the monthly sales, returns, and net balance.
     */
    private void showMonthlyBalance() {

        System.out.println();
        System.out.println(
                "===== BALANCE MENSUAL ====="
        );

        System.out.print("Mes (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.print("Año: ");
        int year = Integer.parseInt(scanner.nextLine());

        try {

            double salesTotal =
                    returnService.calculateMonthlySalesTotal(
                            month,
                            year
                    );

            double returnsTotal =
                    returnService.calculateMonthlyReturnsTotal(
                            month,
                            year
                    );

            double balance =
                    returnService.generateMonthlyBalance(
                            month,
                            year
                    );

            System.out.println();
            System.out.println(
                    "Total de ventas: $" + salesTotal
            );
            System.out.println(
                    "Total de devoluciones: $" + returnsTotal
            );
            System.out.println(
                    "Balance neto: $" + balance
            );

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "No se pudo generar el balance mensual."
            );
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the information of a return transaction.
     *
     * @param returnTransaction return transaction to display
     */
    private void printReturn(Return returnTransaction) {

        System.out.println();
        System.out.println("-----------------------------");
        System.out.println(
                "ID de devolución: "
                        + returnTransaction.getId()
        );
        System.out.println(
                "Fecha de devolución: "
                        + returnTransaction.getReturnDate()
        );
        System.out.println(
                "Venta original: "
                        + returnTransaction.getOriginalSale().getId()
        );
        System.out.println(
                "Motivo: "
                        + returnTransaction.getReturnReason()
        );
        System.out.println(
                "Monto reembolsado: $"
                        + returnTransaction.getRefundAmount()
        );

        System.out.println("Productos devueltos:");

        for (Product product :
                returnTransaction.getReturnedProducts()) {

            System.out.println(
                    "- "
                            + product.getTitle()
                            + " | $"
                            + product.getPrice()
            );
        }

        System.out.println("-----------------------------");
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
                    System.out.println("Opción inválida.");
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

        System.out.print("ID del producto o accesorio: ");
        String itemId = scanner.nextLine();

        List<Product> products = new ArrayList<>();

        Product product = productService.findProduct(itemId);

        if (product != null) {
            products.add(product);
        } else {
            Accessory accessory =
                    accessoryService.findById(itemId);

            if (accessory != null) {
                products.add(accessory);
            } else {
                System.out.println(
                        "Producto o accesorio no encontrado con ID: "
                                + itemId
                );
                return;
            }
        }

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
            System.out.println();
            System.out.println(sale.generateReceipt());

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

        double subtotal = 0.0;

        for (Product product : sale.getProducts()) {
            subtotal += product.getPrice();
        }

        System.out.println("Subtotal: $" + subtotal);

        if (sale.getAppliedPromotionName() != null
                && !sale.getAppliedPromotionName().isBlank()
                && sale.getDiscountAmount() > 0) {
            System.out.println("Promoción: "
                    + sale.getAppliedPromotionName());
            System.out.println("Descuento: $"
                    + sale.getDiscountAmount());
        } else {
            System.out.println("Promoción: Ninguna");
            System.out.println("Descuento: $0.0");
        }

        System.out.println("Total: $" + sale.getTotalAmount());
        System.out.println("-----------------------------");
    }
}

