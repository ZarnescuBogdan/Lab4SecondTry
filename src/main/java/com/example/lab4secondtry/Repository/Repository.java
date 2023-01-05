package com.example.lab4secondtry.Repository;

import com.example.lab4secondtry.Domain.Entity;

import java.util.Optional;

public interface Repository<E extends Entity> {

    /**
     * Get entity by id
     * @param id id of entity
     * @return Optional of found entity
     */
    Optional<E> findOne(long id);

    /**
     * Get all entities
     * @return Iterable of all entities
     */
    Iterable<E> findAll();

    /**
     * Add entity
     * @param entity entity to be added
     */
    void save(E entity);

    /**
     * Delete entity
     * @param id id of entity
     * @return Optional of deleted entity
     */
    Optional<E> delete(long id);

    /**
     * Update entity
     * @param entity entity to be updated
     */
    void update(E entity);

}
