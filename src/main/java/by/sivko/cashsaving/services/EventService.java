package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.Event;

import java.util.Optional;

public interface EventService {
    Event addEvent(Event event);

    void removeEvent(Event event);

    Optional<Event> getEventById(Long id);

    void removeEventById(Long id);
}
