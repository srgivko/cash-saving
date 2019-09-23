package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.EventDao;
import by.sivko.cashsaving.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;

    @Autowired
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event addEvent(Event event) {
        return this.eventDao.add(event);
    }

    @Override
    public Event removeEvent(Event event) {
        return this.eventDao.delete(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return this.eventDao.edit(event);
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return this.eventDao.getById(id);
    }
}
