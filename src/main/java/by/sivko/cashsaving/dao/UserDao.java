package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
