package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;

public interface AuthorityDao {
    Authority getAuthorityByType(AuthorityType authorityType);
}
