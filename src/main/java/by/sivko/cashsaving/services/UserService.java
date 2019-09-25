package by.sivko.cashsaving.services;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.User;

import java.util.Optional;

public interface UserService {
    User addUser(User user);

    User removeUser(User user);

    User updateUser(User user);

    User getUserById(Long id) throws NotFoundEntityException;

    User removeUserById(Long id) throws NotFoundEntityException;

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
