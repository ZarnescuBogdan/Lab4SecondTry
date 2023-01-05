package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Repository.Repository;

public class ActiveRepoUser {
    private Repository<User> repository;
    private static ActiveRepoUser instance = null;

    /**
     * Constructor
     */
    public ActiveRepoUser() {
    }

    /**
     * Getter of User repository
     * @return User repository
     */
    public Repository<User> getRepository() {
        return repository;
    }

    /**
     * Setter of User repository
     * @param repository User repository
     */
    public void setRepository(Repository<User> repository) {
        this.repository = repository;
    }

    /**
     * Get instance
     * @return instance
     */
    public static ActiveRepoUser getInstance() {
        if (instance == null)
            instance = new ActiveRepoUser();
        return instance;
    }
}
