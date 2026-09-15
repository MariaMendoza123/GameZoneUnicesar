package com.gamezone.service;

import com.gamezone.model.Client;
import com.gamezone.model.Person;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Constructs a PersonService with the specified PersonRepository.
     *
     * @param personRepository the repository for managing persons
     */
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Registers a new client in the system.
     *
     * @param name  the name of the client
     * @param phone the phone number of the client
     * @param email the email of the client
     * @return the registered Client object
     */
    public Client registerClient(String name, String phone, String email) {
        validateCommonAttributes(name, phone);
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico del cliente es obligatorio.");
        }

        String id = generateNextId("CL");
        Client client = new Client(id, name, phone, email);
        personRepository.save(client);
        return client;
    }

    /**
     * Registers a new seller in the system.
     *
     * @param name         the name of the seller
     * @param phone        the phone number of the seller
     * @param employeeCode the employee code of the seller
     * @param shift        the work shift of the seller
     * @return the registered Seller object
     */
    public Seller registerSeller(String name, String phone, String employeeCode, String shift) {
        validateCommonAttributes(name, phone);
        if (employeeCode == null || employeeCode.isBlank()) {
            throw new IllegalArgumentException("El código de empleado del vendedor es obligatorio.");
        }

        if (shift == null || shift.isBlank()) {
            throw new IllegalArgumentException("El turno de trabajo del vendedor es obligatorio.");
        }

        String id = generateNextId("SE");
        Seller seller = new Seller(id, name, phone, employeeCode, shift);
        personRepository.save(seller);
        return seller;
    }

    /**
     * Finds a person by its ID.
     *
     * @param id the ID of the person to find
     * @return the found Person object, or null if not found
     */
    public Person findPerson(String id) {
        return personRepository.findById(id);
    }

    /**
     * Retrieves all persons from the repository.
     *
     * @return a list of all persons
     */
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Retrieves all registered clients.
     *
     * @return a list containing only Client instances
     */
    public List<Client> findAllClients() {
        List<Client> clients = new ArrayList<>();
        for (Person person : personRepository.findAll()) {
            if (person instanceof Client) {
                clients.add((Client) person);
            }
        }
        return clients;
    }

    /**
     * Retrieves all registered sellers.
     *
     * @return a list containing only Seller instances
     */
    public List<Seller> findAllSellers() {
        List<Seller> sellers = new ArrayList<>();
        for (Person person : personRepository.findAll()) {
            if (person instanceof Seller) {
                sellers.add((Seller) person);
            }
        }
        return sellers;
    }

    private void validateCommonAttributes(String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre de la persona es obligatorio.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("El teléfono de contacto es obligatorio.");
        }
    }

    private String generateNextId(String prefix) {
        int maxId = 0;
        for (Person person : personRepository.findAll()) {
            if (person.getId() != null && person.getId().startsWith(prefix + "-")) {
                try {
                    int numericPart = Integer.parseInt(person.getId().replace(prefix + "-", ""));
                    if (numericPart > maxId) {
                        maxId = numericPart;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return prefix + "-" + (maxId + 1);
    }
}