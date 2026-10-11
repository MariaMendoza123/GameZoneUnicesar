package com.gamezone.exceptions;
/**
 * Exception thrown when a persistence error occurs in the GameZone application.
 * This exception extends GameZoneException and provides a specific message indicating the persistence error.
 */
public class PersistenceException extends GameZoneException {
    /**
     * Constructs a new PersistenceException with the specified detail message.
     *
     * @param message the detail message
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, "PERSISTENCE_ERROR");
        initCause(cause);;
    }
}
