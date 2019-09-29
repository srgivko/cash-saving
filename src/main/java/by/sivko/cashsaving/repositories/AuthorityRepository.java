package by.sivko.cashsaving.repositories;

import by.sivko.cashsaving.models.Authority;
import by.sivko.cashsaving.models.AuthorityType;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByType(AuthorityType type);
}
