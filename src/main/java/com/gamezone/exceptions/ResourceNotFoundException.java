package com.gamezone.exceptions;
/**
 * Exception thrown when a requested resource is not found.
 * This exception extends GameZoneException and provides a specific message indicating the type of resource and its identifier.
 */
public class ResourceNotFoundException extends GameZoneException {

    private final String resourceType;
    private final String resourceId;

    /**
     * Constructs a new ResourceNotFoundException with the specified resource type and resource ID.
     *
     * @param resourceType the type of the resource that was not found
     * @param resourceId   the identifier of the resource that was not found
     */
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super("El " + resourceType + " con identificador " + resourceId + " no fue encontrado",
                "RESOURCE_NOT_FOUND");
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    /**
     * Constructs a new ResourceNotFoundException with resource type, resource ID, and cause.
     *
     * @param resourceType the type of the resource that was not found
     * @param resourceId   the identifier of the resource that was not found
     * @param cause        the underlying cause exception
     */
    public ResourceNotFoundException(String resourceType, String resourceId, Throwable cause) {
        super("El " + resourceType + " con identificador " + resourceId + " no fue encontrado",
                "RESOURCE_NOT_FOUND", cause);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    /**
     * Returns the type of the resource that was not found.
     *
     * @return the resource type
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Returns the identifier of the resource that was not found.
     *
     * @return the resource ID
     */
    public String getResourceId() {
        return resourceId;
    }
}

