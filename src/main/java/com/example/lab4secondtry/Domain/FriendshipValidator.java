package com.example.lab4secondtry.Domain;

import java.util.ArrayList;
import java.util.List;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        List<String> possible = new ArrayList<>();
        possible.add("accepted");
        possible.add("unaccepted");
        if (!possible.contains(entity.getStatus()) )
            throw new ValidationException("Status is invalid!(Must be one of the following: 'acepted' or 'unaccepted')");
        if (entity.getFirstUserId() == entity.getSecondUserId())
            throw new ValidationException("Friendship cannot be with only one user!");
    }
}
