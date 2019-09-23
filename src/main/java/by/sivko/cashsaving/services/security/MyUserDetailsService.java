package by.sivko.cashsaving.services.security;

import by.sivko.cashsaving.dao.UserDao;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.utils.PdfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public MyUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userDao.findByUsername(username);
        if (user.isPresent()) return new PdfUserDetails(user.get());
        user = this.userDao.findByEmail(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(username);
        return new PdfUserDetails(user.get());
    }
}
