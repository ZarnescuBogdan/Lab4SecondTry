package com.example.lab4secondtry.Domain;

public interface Validator<T> {
    /**
     * Validates an entity
     * @param entity - entity to be validated
     * @throws ValidationException - if entity is not valid, throws my own error
     */
    void validate(T entity) throws ValidationException;

}
