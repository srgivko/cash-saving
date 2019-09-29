package by.sivko.cashsaving.repositories;

import by.sivko.cashsaving.models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findByName(String name);
}
