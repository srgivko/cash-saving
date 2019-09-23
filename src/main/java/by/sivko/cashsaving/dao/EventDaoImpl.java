package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventDaoImpl extends GenericDaoImpl<Event, Long> implements EventDao {
}
