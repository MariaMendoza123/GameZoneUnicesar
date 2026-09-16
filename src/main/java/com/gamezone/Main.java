package com.gamezone;

import com.gamezone.persistence.AccessoryRepository;
import com.gamezone.persistence.PersonRepository;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.ui.ConsoleMenu;

/**
 * Main class of the GameZone application.
 */
public class Main {

    /**
     * Starts the GameZone application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {

        // Create repositories
        PersonRepository personRepository = new PersonRepository();
        ProductRepository productRepository = new ProductRepository();
        AccessoryRepository accessoryRepository = new AccessoryRepository();

        SaleRepository saleRepository = new SaleRepository(
                personRepository,
                productRepository
        );

        // Create services
        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);
        AccessoryService accessoryService = new AccessoryService(accessoryRepository);

        SaleService saleService = new SaleService(
                saleRepository,
                productService,
                personService,
                accessoryService
        );

        // Create and start the console menu
        ConsoleMenu menu = new ConsoleMenu(
                personService,
                productService,
                accessoryService,
                saleService
        );

        menu.handleMainMenu();
    }
}