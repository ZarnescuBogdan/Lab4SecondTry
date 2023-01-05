package com.example.lab4secondtry.Service;

import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Domain.UserValidator;
import com.example.lab4secondtry.Domain.ValidationException;
import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoFriendship;
import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoUser;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService implements Service<User> {
    private UserValidator validator;

    public UserService() {
        this.validator = new UserValidator();
    }

    @Override
    public User find(User user) {
        validator.validate(user);

        for (User u : ActiveRepoUser.getInstance().getRepository().findAll()) {
            if (user.equals(u))
                return u;
        }
        throw new ValidationException("This user does not exist!");
    }

    public Optional<User> findById(long id) {
        return ActiveRepoUser.getInstance().getRepository().findOne(id);
    }

    @Override
    public void add(User entity) {
        validator.validate(entity);

        for (User u : ActiveRepoUser.getInstance().getRepository().findAll()) {
            if (entity.equals(u))
                throw new ValidationException("This user already exists!");
        }

        ActiveRepoUser.getInstance().getRepository().save(entity);
    }

    @Override
    public void delete(long id) {
        ActiveRepoUser.getInstance().getRepository().delete(id);
    }

    @Override
    public Iterable<User> getAll() {
        return ActiveRepoUser.getInstance().getRepository().findAll();
    }

    public void sendFriendRequest(User user1, User user2) {
        Iterable<Friendship> messages = ActiveRepoFriendship.getInstance().getRepository().findAll();
        List<Friendship> allFriendships = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());

        for (Friendship f: allFriendships) {
            if ((f.getFirstUserId() == user1.getId() && f.getSecondUserId() == user2.getId())
            || (f.getSecondUserId() == user1.getId() && f.getFirstUserId() == user2.getId()) ) {
                if (f.getStatus().equals("accepted"))
                    throw new ValidationException("You are already friends!");
                else if (f.getFirstUserId() == user1.getId())
                    throw new ValidationException("You already sent this user a friend request");
                else throw new ValidationException("You have a pending request from this user!");
            }
        }

        Friendship friendship = new Friendship(user1.getId(), user2.getId(), "unaccepted", new Date(System.currentTimeMillis()));
        ActiveRepoFriendship.getInstance().getRepository().save(friendship);
    }
}
