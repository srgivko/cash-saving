package by.sivko.cashsaving.services;

import by.sivko.cashsaving.annotations.Logging;
import by.sivko.cashsaving.dao.AuthorityDao;
import by.sivko.cashsaving.dao.UserDao;
import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.models.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final AuthorityDao authorityDao;

    private final PasswordEncoder passwordEncoder;

    @Logging
    Logger logger;

    @Autowired
    public UserServiceImpl(UserDao userDao, AuthorityDao authorityDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.authorityDao = authorityDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = this.authorityDao.getAuthorityByType(AuthorityType.ROLE_USER);
        user.setAuthorities(new HashSet<>(Collections.singletonList(authority)));
        logger.info(String.format("Add new user with %d", user.getId()));
        return this.userDao.add(user);
    }

    @Override
    public User removeUser(User user) {
        this.userDao.delete(user);
        logger.info(String.format("Delete new user with %d", user.getId()));
        return user;
    }

    @Override
    public User updateUser(User user) {
        user = this.userDao.edit(user);
        logger.info(String.format("Update new user with %d", user.getId()));
        return user;
    }

    @Override
    public User getUserById(Long id) throws NotFoundEntityException {
        User user = this.userDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        logger.info(String.format("Get new user with %d", user.getId()));
        return user;
    }

    @Override
    public User removeUserById(Long id) throws NotFoundEntityException {
        User user = this.userDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        this.userDao.delete(user);
        logger.info(String.format("Remove new user with %d", user.getId()));
        return user;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = this.userDao.findByEmail(email);
        logger.info(String.format("findByEmail User with email %s", email));
        return optionalUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> optionalUser = this.userDao.findByUsername(username);
        logger.info(String.format("findByUsername User with username %s", username));
        return optionalUser;
    }
}
