package com.example.lab4secondtry.Domain;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;

}
