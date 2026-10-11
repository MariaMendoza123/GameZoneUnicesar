package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Person;

import java.util.List;

public class PersonValidator {
    /**
     * Validates the person data.
     * @param id The person identifier.
     * @param name The person name.
     * @param email The person email.
     * @param phone The person phone number.
     * @throws InvalidDataException if any of the parameters are invalid.
     */
    public static void validatePersonData(String id, String name, String email, String phone){

        if (id == null || id.isEmpty()) {
            throw new InvalidDataException("identificador", "no puede ser nulo o vacío");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidDataException("nombre", "no puede ser nulo o vacío");
        }
        if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new InvalidDataException("email", "no puede ser nulo, vacío y debe contener '@' y '.'");
        }
        // Validate phone number format (e.g., must be 10 digits)
        if (phone == null || phone.isEmpty() || !phone.matches("\\d{10}")) {
            throw new InvalidDataException("telefono", "no puede ser nulo, vacío y debe contener 10 dígitos");
        }
    }

    /**
     * Validates that a person exists.
     * @param person The person to validate.
     * @param personId The person identifier.
     * @throws ResourceNotFoundException if the person does not exist.
     */
    public static void validatePersonExists(Person person, String personId){
        if(person == null) {
            throw new ResourceNotFoundException("Persona con ID ", personId);
        }
    }

    /**
     * Validates that a person ID is unique in the existing list of persons.
     * @param existing The list of existing persons.
     * @param newId The new person identifier to validate.
     * @throws BusinessRuleException if a person with the same ID already exists.
     */
    public static void validateUniquePerson(List<Person> existing, String newId){
        for (Person person : existing) {
            if (person.getId().equals(newId)) {
                throw new BusinessRuleException("Ya existe una persona con el ID: " + newId + ".");
            }
        }
    }


}
