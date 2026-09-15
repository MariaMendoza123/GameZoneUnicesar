package com.gamezone;

import com.gamezone.persistence.PersonRepository;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {

        PersonRepository personRepository = new PersonRepository();
        ProductRepository productRepository = new ProductRepository();
        SaleRepository saleRepository = new SaleRepository();

        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);

        SaleService saleService = new SaleService(
                saleRepository,
                productService,
                personService
        );

        ConsoleMenu menu = new ConsoleMenu(
                personService,
                productService,
                saleService
        );

        menu.handleMainMenu();
    }
}
