package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Person;

import java.util.List;

/**
 * Instantiable validator component implementing Validator<Person>.
 * Provides format verification, existence checks, and uniqueness assertions for Person entities.
 */
public class PersonValidator implements Validator<Person> {

    public static final String DEFAULT_PHONE_REGEX = "\\d{10}";

    private final String phoneRegex;

    /**
     * Default constructor using standard 10-digit phone regex rule.
     */
    public PersonValidator() {
        this(DEFAULT_PHONE_REGEX);
    }

    /**
     * Parameterized constructor for custom phone regex rules.
     *
     * @param phoneRegex Custom regex pattern for phone validation.
     */
    public PersonValidator(String phoneRegex) {
        this.phoneRegex = (phoneRegex != null && !phoneRegex.isBlank()) ? phoneRegex : DEFAULT_PHONE_REGEX;
    }

    /**
     * Polymorphic validation contract for Person entity instances.
     * Validates id, name, and phone directly from the Person model.
     *
     * @param person The person entity to validate.
     */
    @Override
    public void validate(Person person) {
        if (person == null) {
            throw new InvalidDataException("persona", "no puede ser nula");
        }
        if (person.getId() == null || person.getId().isEmpty()) {
            throw new InvalidDataException("identificador", "no puede ser nulo o vacío");
        }
        if (person.getName() == null || person.getName().isEmpty()) {
            throw new InvalidDataException("nombre", "no puede ser nulo o vacío");
        }
        if (person.getPhone() == null || person.getPhone().isEmpty() || !person.getPhone().matches(phoneRegex)) {
            throw new InvalidDataException("telefono", "no puede ser nulo, vacío y debe contener 10 dígitos");
        }
    }

    /**
     * Instance method to validate raw person data parameters.
     */
    public void validateData(String id, String name, String email, String phone) {
        if (id == null || id.isEmpty()) {
            throw new InvalidDataException("identificador", "no puede ser nulo o vacío");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("nombre", "no puede ser nulo o vacío");
        }
        if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new InvalidDataException("email", "no puede ser nulo, vacío y debe contener '@' y '.'");
        }
        if (phone == null || phone.isEmpty() || !phone.matches(phoneRegex)) {
            throw new InvalidDataException("telefono", "no puede ser nulo, vacío y debe contener 10 dígitos");
        }
    }

    /**
     * Instance method to validate that a person exists.
     */
    public void validateExists(Person person, String personId) {
        if (person == null) {
            throw new ResourceNotFoundException("Persona con ID ", personId);
        }
    }

    /**
     * Instance method to validate that a person ID is unique.
     */
    public void validateUnique(List<? extends Person> existing, String newId) {
        if (existing == null || newId == null) {
            return;
        }
        for (Person person : existing) {
            if (person.getId() != null && person.getId().equals(newId)) {
                throw new BusinessRuleException("Ya existe una persona con el ID: " + newId + ".");
            }
        }
    }

    // Static delegate methods for backward compatibility with pre-existing caller code
    private static final PersonValidator DEFAULT_INSTANCE = new PersonValidator();

    public static void validatePersonData(String id, String name, String email, String phone) {
        DEFAULT_INSTANCE.validateData(id, name, email, phone);
    }

    public static void validatePersonExists(Person person, String personId) {
        DEFAULT_INSTANCE.validateExists(person, personId);
    }

    public static void validateUniquePerson(List<Person> existing, String newId) {
        DEFAULT_INSTANCE.validateUnique(existing, newId);
    }
}