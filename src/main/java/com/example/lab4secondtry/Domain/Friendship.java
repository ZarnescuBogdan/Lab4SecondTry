package com.example.lab4secondtry.Domain;

import java.util.Date;
import java.util.Objects;

public class Friendship extends Entity{
    private long firstUserId;
    private long secondUserId;
    private String status;
    Date date;

    /**
     * Constructor
     */
    public Friendship() {
    }

    /**
     * Constructor with parameters
     * @param firstUserId sender
     * @param secondUserId receiver
     * @param status accepted or unaccepted
     * @param date date of sent or accepted
     */
    public Friendship(long firstUserId, long secondUserId, String status, Date date) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.status = status;
        this.date = date;
    }

    /**
     * Getter of sender
     * @return sender
     */
    public long getFirstUserId() {
        return firstUserId;
    }

    /**
     * Setter of sender
     * @param firstUserId sender
     */
    public void setFirstUserId(long firstUserId) {
        this.firstUserId = firstUserId;
    }

    /**
     * Getter of receiver
     * @return receiver
     */
    public long getSecondUserId() {
        return secondUserId;
    }

    /**
     * Setter of receiver
     * @param secondUserId receiver
     */
    public void setSecondUserId(long secondUserId) {
        this.secondUserId = secondUserId;
    }

    /**
     * Getter of status
     * @return accepted or unaccepted
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter of status
     * @param status accepted or unaccepted
     */
    public void setStatus(String status) {
        this.status = status;
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

    /**
     * Hash code override of Friendship entity
     * @return hash code of Friendship private attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstUserId, secondUserId);
    }

    /**
     * Equals override of Friendship entity
     * @param obj Friendship entity
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Friendship friendship = (Friendship) obj;
        return ((friendship.firstUserId == this.firstUserId && friendship.secondUserId == this.secondUserId)
                || (friendship.secondUserId == this.firstUserId && friendship.firstUserId == this.secondUserId) );
    }

    /**
     * To string override of Friendship entity
     * @return string
     */
    @Override
    public String toString() {
        return "Friendship{" +
                "firstUserId: " + firstUserId + '\n' +
                "secondUserId: " + secondUserId + '\n' +
                "status: " + status + '\n' +
                "date: " + date + '}';
    }
}
