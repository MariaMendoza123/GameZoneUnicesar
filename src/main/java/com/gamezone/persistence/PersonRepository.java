package com.gamezone.persistence;

import com.gamezone.model.Client;
import com.gamezone.model.Person;
import com.gamezone.model.Seller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing persons in the game zone.
 * This class provides methods to save, retrieve, and update persons in a text file.
 */
public class PersonRepository {

    private static final String FILE_PATH = "data/persons.txt";
    private static final String SEPARATOR = "|";
    private static final String DELIMITER = "\\|";
    private static final String CLIENT_TAG = "CLIENT";
    private static final String SELLER_TAG = "SELLER";

    /**
     * Saves a person to the repository.
     *
     * @param person the person to be saved
     */
    public void save(Person person) {
        List<String> lines = readLines();
        lines.add(toLine(person));
        writeLines(lines);
    }

    /**
     * Retrieves all persons from the repository.
     *
     * @return a list of all persons
     */
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        for (String line : readLines()) {
            if (!line.isBlank()) {
                persons.add(fromLine(line));
            }
        }
        return persons;
    }

    /**
     * Finds a person by its unique identifier.
     *
     * @param id the unique identifier of the person
     * @return the person with the specified id, or null if not found
     */
    public Person findById(String id) {
        for (Person person : findAll()) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Updates an existing person in the repository.
     *
     * @param person the person with updated information
     */
    public void update(Person person) {
        List<Person> persons = findAll();
        List<String> lines = new ArrayList<>();
        for (Person current : persons) {
            Person toWrite = current.getId().equals(person.getId()) ? person : current;
            lines.add(toLine(toWrite));
        }
        writeLines(lines);
    }

    private List<String> readLines() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + FILE_PATH, e);
        }
    }

    private void writeLines(List<String> lines) {
        Path path = Paths.get(FILE_PATH);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + FILE_PATH, e);
        }
    }

    private String toLine(Person person) {
        if (person instanceof Client) {
            Client client = (Client) person;
            return CLIENT_TAG + SEPARATOR + client.getId() + SEPARATOR
                    + client.getName() + SEPARATOR + client.getPhone() + SEPARATOR
                    + client.getEmail();
        } else if (person instanceof Seller) {
            Seller seller = (Seller) person;
            return SELLER_TAG + SEPARATOR + seller.getId() + SEPARATOR
                    + seller.getName() + SEPARATOR + seller.getPhone() + SEPARATOR
                    + seller.getEmployeeCode() + SEPARATOR + seller.getShift();
        }
        throw new IllegalArgumentException(
                "Unsupported person type: " + person.getClass().getSimpleName());
    }

    private Person fromLine(String line) {
        String[] fields = line.split(DELIMITER, -1);
        String type = fields[0];
        String id = fields[1];
        String name = fields[2];
        String phone = fields[3];

        if (CLIENT_TAG.equals(type)) {
            return new Client(id, name, phone, fields[4]);
        } else if (SELLER_TAG.equals(type)) {
            return new Seller(id, name, phone, fields[4], fields[5]);
        }
        throw new IllegalArgumentException("Unknown person type in file: " + type);
    }

}