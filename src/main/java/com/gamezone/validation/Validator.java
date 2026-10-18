package com.gamezone.validation;

/**
 * Generic contract for domain entity validators.
 *
 * @param <T> Domain entity type to validate
 */
public interface Validator<T> {

    /**
     * Validates the specified domain target instance.
     *
     * @param target entity to validate
     */
    void validate(T target);
}