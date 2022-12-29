package com.example.lab4secondtry.Domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Friendship extends Entity{
    private long firstUserId;
    private long secondUserId;
    private String status;
    Date date;

    public Friendship() {
    }

    public Friendship(long firstUserId, long secondUserId, String status, Date date) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.status = status;
        this.date = date;
    }

    public long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(long secondUserId) {
        this.secondUserId = secondUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstUserId, secondUserId);
    }

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

    @Override
    public String toString() {
        return "Friendship{" +
                "firstUserId: " + firstUserId + '\n' +
                "secondUserId: " + secondUserId + '\n' +
                "status: " + status + '\n' +
                "date: " + date + '}';
    }
}
