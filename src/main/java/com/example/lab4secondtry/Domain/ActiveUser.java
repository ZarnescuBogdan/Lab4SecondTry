package com.example.lab4secondtry.Domain;

public class ActiveUser {
    private User user;
    private static ActiveUser instance = null;

    /**
     * Constructor
     */
    private ActiveUser() {
    }

    /**
     * Getter of User
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of User
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter of instance
     * @return instance
     */
    public static ActiveUser getInstance() {
        if (instance == null)
            instance = new ActiveUser();
        return instance;
    }
}
