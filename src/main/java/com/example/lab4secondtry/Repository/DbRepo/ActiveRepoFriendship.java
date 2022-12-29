package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Repository.Repository;

public class ActiveRepoFriendship {
    private Repository<Friendship> repository;
    private static ActiveRepoFriendship instance = null;

    public ActiveRepoFriendship() {
    }

    public Repository<Friendship> getRepository() {
        return repository;
    }

    public void setRepository(Repository<Friendship> repository) {
        this.repository = repository;
    }

    public static ActiveRepoFriendship getInstance() {
        if (instance == null)
            instance = new ActiveRepoFriendship();
        return instance;
    }
}
