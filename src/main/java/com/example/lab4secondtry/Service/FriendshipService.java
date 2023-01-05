package com.example.lab4secondtry.Service;

import com.example.lab4secondtry.Domain.*;
import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoFriendship;
import com.example.lab4secondtry.Repository.DbRepo.ActiveRepoUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendshipService implements Service<Friendship> {
    FriendshipValidator validator;

    public FriendshipService() {
        this.validator = new FriendshipValidator();
    }

    @Override
    public Friendship find(Friendship entity) {
        validator.validate(entity);

        Iterable<Friendship> allFriendships = ActiveRepoFriendship.getInstance().getRepository().findAll();

        for (Friendship f : allFriendships) {
            if (entity.equals(f))
                return f;
        }
        throw new ValidationException("This friendship does not exist!");
    }

    @Override
    public void add(Friendship entity) {
        validator.validate(entity);

        for (Friendship f : ActiveRepoFriendship.getInstance().getRepository().findAll()) {
            if (entity.equals(f))
                throw new ValidationException("This friendship already exists!");
        }

        ActiveRepoFriendship.getInstance().getRepository().save(entity);
    }

    @Override
    public void delete(long id) {
        ActiveRepoFriendship.getInstance().getRepository().delete(id);
    }

    @Override
    public Iterable<Friendship> getAll() {
        return ActiveRepoFriendship.getInstance().getRepository().findAll();
    }

    public void getAllFriendsUser() {
        Iterable<Friendship> message = getAll();
        List<Friendship> allFriendships = StreamSupport.stream(message.spliterator(), false)
                .collect(Collectors.toList());
        allFriendships.forEach(x -> {
            if (x.getFirstUserId() == ActiveUser.getInstance().getUser().getId() && x.getStatus().equals("accepted")) {
                Optional<User> user = ActiveRepoUser.getInstance().getRepository().findOne(x.getSecondUserId());
                if (user.isPresent()) {
                    ActiveUser.getInstance().getUser().getFriends().add(user.get());
                }
            } else if (x.getSecondUserId() == ActiveUser.getInstance().getUser().getId() && x.getStatus().equals("accepted")) {
                Optional<User> user = ActiveRepoUser.getInstance().getRepository().findOne(x.getFirstUserId());
                if (user.isPresent()) {
                    ActiveUser.getInstance().getUser().getFriends().add(user.get());
                }
            }
        });
    }

    public void update(Friendship friendship) {
        ActiveRepoFriendship.getInstance().getRepository().update(friendship);
    }
}
