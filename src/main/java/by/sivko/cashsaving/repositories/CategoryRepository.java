package by.sivko.cashsaving.repositories;

import by.sivko.cashsaving.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Collection<Category> findAllByUserUsername(String username);

    Collection<Category> findAllByUserId(Long id);

    Collection<Category> findAllByNameLike(String name);

    Collection<Category> findAllByCreateAtBetween(Date categoryTimeStart, Date categoryTimeEnd);

    @Query("select c from Category c where c.createAt <= :creationDateTime")
    Collection<Category> findAllWithCreateAtBefore(@Param("creationDateTime") Date creationDateTime);
}
