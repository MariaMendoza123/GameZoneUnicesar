package com.gamezone.exceptions;

/**
 * Exception thrown when invalid data is encountered in the GameZone application.
 * This exception extends GameZoneException and provides a specific message indicating the field with the invalid data and the problem with it.
 */
public class InvalidDataException extends GameZoneException{

    private final String fieldName;
    private final String problem;

    /**
     * Constructs a new InvalidDataException with the specified field name and problem.
     *
     * @param fieldName the name of the field that contains invalid data
     * @param problem   the problem with the field
     */
    public InvalidDataException(String fieldName, String problem) {
        super("el campo " + fieldName + " es inválido: " + problem, "INVALID_DATA");
        this.fieldName = fieldName;
        this.problem = problem;
    }

    /**
     * Constructs a new InvalidDataException with field name, problem details, and cause.
     *
     * @param fieldName the name of the field that contains invalid data
     * @param problem   the problem with the field
     * @param cause     the underlying cause exception
     */
    public InvalidDataException(String fieldName, String problem, Throwable cause) {
        super("el campo " + fieldName + " es inválido: " + problem, "INVALID_DATA", cause);
        this.fieldName = fieldName;
        this.problem = problem;
    }

    /**
     * Returns the name of the field that contains invalid data.
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the problem with the field.
     *
     * @return the problem
     */
    public String getProblem() {
        return problem;
    }
}
