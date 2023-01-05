package com.example.lab4secondtry.Service;


public interface Service<E> {

    void add(E entity);
    void delete(long id);
    Iterable<E> getAll();
    E find(E entity);
}
