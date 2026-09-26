package com.gamezone;

import com.gamezone.persistence.AccessoryRepository;
import com.gamezone.persistence.PersonRepository;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.PromotionRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.SaleService;
import com.gamezone.ui.ConsoleMenu;
import com.gamezone.persistence.ReturnRepository;
import com.gamezone.service.ReturnService;
import com.gamezone.persistence.WarrantyRepository;
import com.gamezone.service.WarrantyService;

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
        PromotionRepository promotionRepository = new PromotionRepository();


        SaleRepository saleRepository = new SaleRepository(
                personRepository,
                productRepository,
                accessoryRepository
        );

        // Create services
        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);
        AccessoryService accessoryService =
                new AccessoryService(accessoryRepository);
        PromotionService promotionService =
                new PromotionService(promotionRepository);

        WarrantyRepository warrantyRepository =
                new WarrantyRepository();

        WarrantyService warrantyService =
                new WarrantyService(
                        warrantyRepository,
                        saleRepository,
                        productService
                );

        SaleService saleService = new SaleService(
                saleRepository,
                productService,
                personService,
                accessoryService,
                promotionService,
                warrantyService
        );

        ReturnRepository returnRepository =
                new ReturnRepository(
                        saleService,
                        productService
                );

        ReturnService returnService =
                new ReturnService(
                        returnRepository,
                        saleService,
                        productService,
                        accessoryService
                );

        // Create and start the console menu
        ConsoleMenu menu = new ConsoleMenu(
                personService,
                productService,
                accessoryService,
                saleService,
                promotionService,
                returnService,
                warrantyService
        );
        menu.handleMainMenu();
    }
}