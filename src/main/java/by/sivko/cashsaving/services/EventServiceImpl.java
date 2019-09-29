package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event addEvent(Event event) {
        return this.eventRepository.save(event);
    }

    @Override
    public void removeEvent(Event event) {
        this.eventRepository.delete(event);
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public void removeEventById(Long id) {
        this.eventRepository.deleteById(id);
    }

}
