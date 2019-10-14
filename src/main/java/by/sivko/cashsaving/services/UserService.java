package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User addUser(User user);

    void removeUser(User user);

    Optional<User> getUserById(Long id);

    void removeUserById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    void activateUser(String code);
}
