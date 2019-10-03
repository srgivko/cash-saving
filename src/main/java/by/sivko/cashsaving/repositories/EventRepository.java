package by.sivko.cashsaving.repositories;

import by.sivko.cashsaving.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findByName(@Param("name") String name);
}
