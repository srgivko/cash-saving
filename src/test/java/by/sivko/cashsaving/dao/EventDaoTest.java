package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.dao.datasetLoaders.DefaultDataSetLoader;
import by.sivko.cashsaving.models.Event;
import com.github.springtestdbunit.annotation.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static by.sivko.cashsaving.dao.AbstractDatabaseAnnotationInclude.*;
import static org.junit.Assert.*;

@DatabaseSetups({
        @DatabaseSetup(LINK_USERS_DATA_SET),
        @DatabaseSetup(LINK_CATEGORIES_DATA_SET),
        @DatabaseSetup(LINK_EVENTS_DATA_SET)
})
@DatabaseTearDowns({
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_EVENTS_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_CATEGORIES_DATA_SET),
        @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = LINK_USERS_DATA_SET)
})
@DbUnitConfiguration(dataSetLoader = DefaultDataSetLoader.class)
public class EventDaoTest extends AbstractDatabaseAnnotationInclude {

    private static final int EVENT_LIST_SIZE = 7;
    private static final Event NEW_EVENT = new Event();
    private static final long EXIST_EVENT_ID = 1000000L;
    private static final long NOT_EXIST_EVENT_ID = 1005000L;

    @Autowired
    EventDao eventDao;

    @Before
    public void init() {
        NEW_EVENT.setId(null);
        NEW_EVENT.setType(Event.Type.INCOME);
        NEW_EVENT.setAmount(new BigDecimal(100L));
        NEW_EVENT.setDescription("new description");
        NEW_EVENT.setType(Event.Type.INCOME);
    }

    @Test
    public void checkAllEventsCount() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
    }

    @Test
    public void getExistEvent() {
        Optional<Event> optionalEvent = this.eventDao.getById(EXIST_EVENT_ID);
        assertTrue(optionalEvent.isPresent());
        assertNotNull(optionalEvent.get());
    }

    @Test
    public void getNotExistEvent() {
        Optional<Event> optionalEvent = this.eventDao.getById(NOT_EXIST_EVENT_ID);
        assertFalse(optionalEvent.isPresent());
    }

    @Test
    public void checkAddEvent() {
        assertNull(NEW_EVENT.getId());
        this.eventDao.add(NEW_EVENT);
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE + 1);
        assertNotNull(NEW_EVENT.getId());
    }

    @Test
    public void ifAddPersistenceEvent() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        Optional<Event> optionalEvent = this.eventDao.getById(EXIST_EVENT_ID);
        assertTrue(optionalEvent.isPresent());
        Event event = optionalEvent.get();
        Long eventIdRemainder = event.getId();
        assertNotNull(eventIdRemainder);
        this.eventDao.add(event);
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        assertEquals(eventIdRemainder, event.getId());
    }

    @Test
    public void checkEditEvent() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        Optional<Event> optionalEvent = this.eventDao.getById(EXIST_EVENT_ID);
        assertTrue(optionalEvent.isPresent());
        Event event = optionalEvent.get();
        String descriptionRemainder = event.getDescription();
        event.setDescription("new description");
        event = this.eventDao.edit(event);
        assertNotEquals(descriptionRemainder, event.getDescription());
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
    }

    @Test
    public void ifEditTransientEvent() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        assertNull(NEW_EVENT.getId());
        Event event = this.eventDao.edit(NEW_EVENT);
        assertNotNull(event.getId());
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE + 1);
    }

    @Test
    public void checkRemoveEvent() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        Event event = this.eventDao.delete(this.eventDao.getById(EXIST_EVENT_ID).orElseThrow(RuntimeException::new));
        assertNotNull(event);
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE - 1);
    }

    @Test
    public void ifRemoveExistEvent() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        assertNull(NEW_EVENT.getId());
        Event event = this.eventDao.delete(NEW_EVENT);
        assertNull(event.getId());
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
    }

    @Test
    public void ifRemoveDetachedExistEvent() {
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE);
        Event event = this.eventDao.getById(EXIST_EVENT_ID).orElseThrow(RuntimeException::new);
        this.eventDao.edit(event);
        this.eventDao.delete(event);
        assertEquals(this.eventDao.getAll().size(), EVENT_LIST_SIZE - 1);
    }
}
