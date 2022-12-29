package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Repository.Repository;

public class ActiveRepoUser {
    private Repository<User> repository;
    private static ActiveRepoUser instance = null;

    public ActiveRepoUser() {
    }

    public Repository<User> getRepository() {
        return repository;
    }

    public void setRepository(Repository<User> repository) {
        this.repository = repository;
    }

    public static ActiveRepoUser getInstance() {
        if (instance == null)
            instance = new ActiveRepoUser();
        return instance;
    }
}
