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

}