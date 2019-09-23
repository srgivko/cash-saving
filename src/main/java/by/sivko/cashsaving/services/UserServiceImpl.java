package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.AuthorityDao;
import by.sivko.cashsaving.dao.UserDao;
import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserServiceImpl(UserDao userDao, AuthorityDao authorityDao) {
        this.userDao = userDao;
        this.authorityDao = authorityDao;
    }

    @Override
    public User addUser(User user) {
        Authority authority = this.authorityDao.getAuthorityByType(AuthorityType.ROLE_USER);
        user.setAuthorities(new HashSet<>(Collections.singletonList(authority)));
        return this.userDao.add(user);
    }

    @Override
    public User removeUser(User user) {
        return this.userDao.delete(user);
    }

    @Override
    public User updateUser(User user) {
        return this.userDao.edit(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return this.userDao.getById(id);
    }
}
