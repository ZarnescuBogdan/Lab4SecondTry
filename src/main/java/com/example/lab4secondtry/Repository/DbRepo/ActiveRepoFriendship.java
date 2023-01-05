package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Repository.Repository;

public class ActiveRepoFriendship {
    private Repository<Friendship> repository;
    private static ActiveRepoFriendship instance = null;

    /**
     * Constructor
     */
    public ActiveRepoFriendship() {
    }

    /**
     * Getter of Friendship repository
     * @return Friendship repository
     */
    public Repository<Friendship> getRepository() {
        return repository;
    }

    /**
     * Setter of Friendship repository
     * @param repository Friendship repository
     */
    public void setRepository(Repository<Friendship> repository) {
        this.repository = repository;
    }

    /**
     * Get instance
     * @return instance
     */
    public static ActiveRepoFriendship getInstance() {
        if (instance == null)
            instance = new ActiveRepoFriendship();
        return instance;
    }
}
