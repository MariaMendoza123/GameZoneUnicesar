package com.gamezone.exceptions;
/**
 * Exception thrown when a requested resource is not found.
 * This exception extends GameZoneException and provides a specific message indicating the type of resource and its identifier.
 */
public class ResourceNotFoundException extends GameZoneException {

    /**
     * Constructs a new ResourceNotFoundException with the specified resource type and resource ID.
     *
     * @param resourceType the type of the resource that was not found
     * @param resourceId   the identifier of the resource that was not found
     */
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super("El " + resourceType + " con identificador " + resourceId, "no fue encontrado.");
    }
}
