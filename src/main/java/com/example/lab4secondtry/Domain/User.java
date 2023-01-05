package com.example.lab4secondtry.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private List<User> friends;

    /**
     * Constructor
     */
    public User() {
    }

    /**
     * Constructor with parameters
     * @param firstName first name of User
     * @param lastName last name of User
     */
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>();
    }

    /**
     * Getter for first name
     * @return first name of User
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for first name
     * @param firstName first name of User
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for last name
     * @return last name of User
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for last name
     * @param lastName last name of User
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for friends
     * @return friends of User
     */
    public List<User> getFriends() {
        return friends;
    }

    /**
     * Setter for friends
     * @param friends friends of User
     */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Hash code override of User private attributes
     * @return hash code of User private attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /**
     * Equals override of User entity
     * @param obj User to be compared to
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return (user.firstName.equals(this.firstName) && user.lastName.equals(this.lastName));
    }

    /**
     * To string override of User entity
     * @return string
     */
    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + "'\n" +
                "lastName='" + lastName + "'\n" +
                "friends=" + friends + '}';
    }
}
