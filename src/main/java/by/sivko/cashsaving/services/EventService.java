package by.sivko.cashsaving.services;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Event;

public interface EventService {
    Event addEvent(Event event);

    Event removeEvent(Event event);

    Event updateEvent(Event event);

    Event getEventById(Long id) throws NotFoundEntityException;

    Event removeEventById(Long id) throws NotFoundEntityException;
}
