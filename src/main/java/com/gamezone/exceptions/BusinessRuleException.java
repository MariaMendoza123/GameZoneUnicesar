package com.gamezone.exceptions;
/**
 * Exception thrown when a business rule is violated in the GameZone application.
 * This exception extends GameZoneException and provides a specific message indicating the nature of the violation.
 */
public class BusinessRuleException extends GameZoneException{
    /**
     * Constructs a new BusinessRuleException with the specified detail message.
     *
     * @param message the detail message
     */
    public BusinessRuleException(String message){
        super(message, "BUSINESS_RULE_VIOLATION");
    }
}
