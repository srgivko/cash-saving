package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.EventDao;
import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.models.Event;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EventServiceTest extends AbstractServiceAnnotationInclude {

    private static final Event EVENT = new Event();

    @Mock
    EventDao eventDao;

    @InjectMocks
    EventServiceImpl eventService;

    @Before
    public void setUp() {
        EVENT.setDescription("NEW_DESCRIPTION");
        EVENT.setCategory(new Category());
        EVENT.setType(Event.Type.INCOME);
        EVENT.setAmount(BigDecimal.ONE);
        EVENT.setId(1L);
        when(this.eventDao.add(EVENT)).thenReturn(EVENT);
        when(this.eventDao.getById(EVENT.getId())).thenReturn(Optional.of(EVENT));
    }

    @Test
    public void addEvent() {
        Event event = this.eventService.addEvent(EVENT);
        verify(eventDao, times(1)).add(EVENT);
        assertEquals(EVENT, event);
    }

    @Test
    public void getElementById(){
        Optional<Event> optionalEvent = this.eventService.getEventById(EVENT.getId());
        verify(eventDao, times(1)).getById(EVENT.getId());
        assertEquals(EVENT, optionalEvent.orElseThrow(RuntimeException::new));
    }
}