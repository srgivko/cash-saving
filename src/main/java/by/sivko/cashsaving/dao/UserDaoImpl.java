package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {
    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        try {
            user = super.entityManager
                    .createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            nre.printStackTrace();
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try {
            user = super.entityManager
                    .createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            nre.printStackTrace();
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }
}
