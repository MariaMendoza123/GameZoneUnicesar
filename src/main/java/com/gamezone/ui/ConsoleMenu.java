package com.gamezone.ui;

import java.util.Scanner;

/**
 * Represents the main console menu of the GameZone system.
 */
public class ConsoleMenu {

    private final Scanner scanner;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
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
     * Reads the option selected by the user.
     *
     * @return the selected menu option.
     */
    public int readOption() {
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    /**
     * Handles the main menu navigation.
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
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 4);
    }
    /**
     * Displays the product management submenu.
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
     * Handles the product management submenu.
     */
    public void handleProductMenu() {
        int option;

        do {
            showProductMenu();
            option = readOption();

            switch (option) {
                case 1:
                    System.out.println("Registrar videojuego.");
                    break;
                case 2:
                    System.out.println("Registrar consola.");
                    break;
                case 3:
                    System.out.println("Consultar productos.");
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 4);
    }

    /**
     * Displays the person management submenu.
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
     * Handles the person management submenu.
     */
    public void handlePersonMenu() {
        int option;

        do {
            showPersonMenu();
            option = readOption();

            switch (option) {
                case 1:
                    System.out.println("Registrar cliente.");
                    break;
                case 2:
                    System.out.println("Registrar vendedor.");
                    break;
                case 3:
                    System.out.println("Consultar personas.");
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 4);
    }

    /**
     * Displays the sale management submenu.
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
     * Handles the sale management submenu.
     */
    public void handleSaleMenu() {
        int option;

        do {
            showSaleMenu();
            option = readOption();

            switch (option) {
                case 1:
                    System.out.println("Registrar venta.");
                    break;
                case 2:
                    System.out.println("Consultar historial de ventas.");
                    break;
                case 3:
                    System.out.println("Consultar compras de un cliente.");
                    break;
                case 4:
                    System.out.println("Consultar ventas de un vendedor.");
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 5);
    }

}