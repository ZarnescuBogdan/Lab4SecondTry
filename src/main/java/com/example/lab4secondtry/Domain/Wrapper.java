package com.example.lab4secondtry.Domain;

import java.util.Date;

public class Wrapper {
    private String firstName;
    private String lastName;
    private Date date;

    /**
     * Constructor with parameters
     * @param firstName first name
     * @param lastName last name
     * @param date date
     */
    public Wrapper(String firstName, String lastName, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
    }

    /**
     * Getter of first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of first name
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of last name
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of date
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter of date
     * @param date date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
