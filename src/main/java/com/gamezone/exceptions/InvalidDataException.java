package com.gamezone.exceptions;

/**
 * Exception thrown when invalid data is encountered in the GameZone application.
 * This exception extends GameZoneException and provides a specific message indicating the field with the invalid data and the problem with it.
 */
public class InvalidDataException extends GameZoneException{
    /**
     * Constructs a new InvalidDataException with the specified field name and problem.
     *
     * @param fieldName the name of the field that contains invalid data
     * @param problem   the problem with the field
     */
    public InvalidDataException(String fieldName, String problem) {
        super("el campo " + fieldName + " es inválido: " + problem, "INVALID_DATA");
    }
}
