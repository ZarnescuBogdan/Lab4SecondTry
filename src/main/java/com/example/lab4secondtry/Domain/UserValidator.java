package com.example.lab4secondtry.Domain;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if(entity.getFirstName() == "" || entity.getLastName() == "")
            throw new ValidationException("Names cannot be null");
        boolean allLettersFN = entity.getFirstName().chars().allMatch(Character::isLetter);
        boolean allLettersLN = entity.getLastName().chars().allMatch(Character::isLetter);
        if(!allLettersFN || !allLettersLN)
            throw new ValidationException("Names can only contain letters!");
    }
}
