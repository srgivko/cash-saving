package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class AuthorityDaoImpl implements AuthorityDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Authority getAuthorityByType(AuthorityType authorityType) {
        Authority authority = null;
        try {
            authority = this.entityManager
                    .createNamedQuery("Authority.getAuthorityByType", Authority.class)
                    .setParameter("type", authorityType)
                    .getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return authority;
    }
}
