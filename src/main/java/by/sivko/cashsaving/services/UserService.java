package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.User;

import java.util.Optional;

public interface UserService {
    User addUser(User user);

    User removeUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(Long id);
}
