package com.example.lab4secondtry.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private List<User> friends;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return (user.firstName.equals(this.firstName) && user.lastName.equals(this.lastName));
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + "'\n" +
                "lastName='" + lastName + "'\n" +
                "friends=" + friends + '}';
    }
}
