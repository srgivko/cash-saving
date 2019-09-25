package by.sivko.cashsaving.services;

import by.sivko.cashsaving.annotations.Logging;
import by.sivko.cashsaving.dao.EventDao;
import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Event;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;

    @Logging
    private Logger logger;

    @Autowired
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event addEvent(Event event) {
        this.eventDao.add(event);
        logger.info(String.format("Add event with %d", event.getId()));
        return event;
    }

    @Override
    public Event removeEvent(Event event) {
        event = this.eventDao.delete(event);
        logger.info(String.format("Delete event with %d", event.getId()));
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        event = this.eventDao.edit(event);
        logger.info(String.format("Update event with %d", event.getId()));
        return event;
    }

    @Override
    public Event getEventById(Long id) throws NotFoundEntityException {
        Event event = this.eventDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        logger.info(String.format("Get event with %d", event.getId()));
        return event;
    }

    @Override
    public Event removeEventById(Long id) throws NotFoundEntityException {
        Event event = this.eventDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        this.eventDao.delete(event);
        logger.info(String.format("Remove event with %d", event.getId()));
        return event;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
