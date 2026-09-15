package com.gamezone;

import com.gamezone.persistence.PersonRepository;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
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
        SaleRepository saleRepository = new SaleRepository();

        // Create services
        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);

        SaleService saleService = new SaleService(
                saleRepository,
                productService,
                personService
        );

        // Create and start the console menu
        ConsoleMenu menu = new ConsoleMenu(
                personService,
                productService,
                saleService
        );

        menu.handleMainMenu();
    }
}

