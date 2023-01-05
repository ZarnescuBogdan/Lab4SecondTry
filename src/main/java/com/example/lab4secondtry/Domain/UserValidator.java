package com.example.lab4secondtry.Domain;

import java.util.Objects;

public class UserValidator implements Validator<User> {
    /**
     * Validate User entity
     * @param entity - entity to be validated
     * @throws ValidationException - if User is not valid, throws my own error
     */
    @Override
    public void validate(User entity) throws ValidationException {
        if(Objects.equals(entity.getFirstName(), "") || Objects.equals(entity.getLastName(), ""))
            throw new ValidationException("Names cannot be null");
        boolean allLettersFN = entity.getFirstName().chars().allMatch(Character::isLetter);
        boolean allLettersLN = entity.getLastName().chars().allMatch(Character::isLetter);
        if(!allLettersFN || !allLettersLN)
            throw new ValidationException("Names can only contain letters!");
    }
}
