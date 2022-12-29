package com.example.lab4secondtry.Domain;

public class ActiveUser {
    private User user;
    private static ActiveUser instance = null;

    private ActiveUser() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ActiveUser getInstance() {
        if (instance == null)
            instance = new ActiveUser();
        return instance;
    }
}
