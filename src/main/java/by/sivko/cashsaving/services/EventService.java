package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.Event;

import java.util.Optional;

public interface EventService {
    Event addEvent(Event event);

    Event removeEvent(Event event);

    Event updateEvent(Event event);

    Optional<Event> getEventById(Long id);
}
