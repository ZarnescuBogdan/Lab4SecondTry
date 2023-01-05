package com.example.lab4secondtry.Domain;

public class Entity {
    private long id;

    /**
     * Constructor
     */
    public Entity() {
    }

    /**
     * Constructor with parameter
     * @param id
     */
    public Entity(long id) {
        this.id = id;
    }

    /**
     * Getter for id
     * @return id of entity
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id id of entity
     */
    public void setId(long id) {
        this.id = id;
    }
}
