package com.gamezone.exceptions;
/**
 * Base class for all exceptions in the GameZone application.
 * This class extends RuntimeException and provides a mechanism to include an error code.
 */
public abstract class GameZoneException extends RuntimeException {

    private String errorCode;
    /**
     * Constructs a new GameZoneException with the specified detail message.
     *
     * @param message the detail message
     */
    public GameZoneException(String message) {
        super(message);
    }
    /**
     * Constructs a new GameZoneException with the specified detail message and error code.
     *
     * @param message   the detail message
     * @param errorCode the error code associated with this exception
     */
    public GameZoneException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    /**
     * Constructs a new GameZoneException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public GameZoneException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new GameZoneException with the specified detail message, error code, and cause.
     *
     * @param message   the detail message
     * @param errorCode the error code associated with this exception
     * @param cause     the cause of the exception
     */
    public GameZoneException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code associated with this exception.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }


}
