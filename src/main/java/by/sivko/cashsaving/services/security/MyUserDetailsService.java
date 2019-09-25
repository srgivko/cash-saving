package by.sivko.cashsaving.services.security;

import by.sivko.cashsaving.annotations.Logging;
import by.sivko.cashsaving.dao.UserDao;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.utils.PdfUserDetails;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Logging
    private Logger logger;

    private final UserDao userDao;

    @Autowired
    public MyUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info(String.format("Try to login with username=[%s]", username));
        Optional<User> user = this.userDao.findByUsername(username);
        if (user.isPresent()) return new PdfUserDetails(user.get());
        user = this.userDao.findByEmail(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(username);
        logger.info(String.format("Success login with username=[%s]", username));
        return new PdfUserDetails(user.get());
    }
}
