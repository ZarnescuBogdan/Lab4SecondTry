package com.example.lab4secondtry.Repository;

import com.example.lab4secondtry.Domain.Entity;

import java.util.Optional;

public interface Repository<E extends Entity> {

    Optional<E> findOne(long id);

    Iterable<E> findAll();

    Optional<E> save(E entity);

    Optional<E> delete(long id);

    Optional<E> update(E entity);

}
