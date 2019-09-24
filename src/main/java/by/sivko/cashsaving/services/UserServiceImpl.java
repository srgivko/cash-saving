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
        logger.debug(String.format("Add new user with %d", user.getId()));
        return this.userDao.add(user);
    }

    @Override
    public User removeUser(User user) {
        this.userDao.delete(user);
        logger.debug(String.format("Add new user with %d", user.getId()));
        return user;
    }

    @Override
    public User updateUser(User user) {
        user = this.userDao.edit(user);
        logger.debug(String.format("Update new user with %d", user.getId()));
        return user;
    }

    @Override
    public User getUserById(Long id) throws NotFoundEntityException {
        User user = this.userDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        logger.debug(String.format("Get new user with %d", user.getId()));
        return user;
    }

    @Override
    public User removeUserById(Long id) throws NotFoundEntityException {
        User user = this.userDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        this.userDao.delete(user);
        logger.debug(String.format("Remove new user with %d", user.getId()));
        return user;
    }
}
