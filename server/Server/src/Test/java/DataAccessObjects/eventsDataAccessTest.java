package DataAccessObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import DataAccessObjects.Database.DatabaseConnection;
import model.Event;
import services.ClearService;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/6/2018.
 */
public class eventsDataAccessTest extends DatabaseConnection{
    @Before
    public void setUp() throws Exception {
        openDatabase();
        createTables();
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearDatabase = new ClearService();
        System.out.println(clearDatabase.clear());
        closeDatabase();
    }

    @Test
    public void createEvent() throws Exception {
        //Create an Event to add
        Event event = new Event("Groppstopper", UUID.randomUUID().toString());
        event.setCity("Boise");
        event.setCountry("United States");
        event.setEventType("Birth");
        event.setYear("1994");
        EventsDataAccess eventDao = new EventsDataAccess();

        //Expect true to return. False means it was unsuccessful. This will occur forever as long
        //as there is a table defined as "EVENT".
        assertTrue(eventDao.createEvent(event));
    }

    @Test
    public void findEvent() throws Exception {
        //Create an Event to add
        Event event = new Event("Groppstopper", UUID.randomUUID().toString());
        event.setCity("Boise");
        event.setCountry("United States");
        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(event);

        //True if the event exists
        assertTrue(eventDao.findEvent(event.getEventID()));

        //False if the event does not exist
        assertFalse(eventDao.findEvent("FalseEvent"));
    }

    @Test
    public void deleteEvents() throws Exception {
        //Create two Events to add
        Event event1 = new Event("Groppstopper", UUID.randomUUID().toString());
        event1.setCity("Boise");
        event1.setCountry("United States");
        Event event2 = new Event("Groppstopper", UUID.randomUUID().toString());
        event2.setCity("Stanley");
        event2.setCountry("United States");
        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(event1);
        eventDao.createEvent(event2);

        //Will always return true as long as there is an event table to delete.
        assertTrue(eventDao.deleteEvents());
    }

    @Test
    public void findAllDescendantsEvents() throws Exception {
        //Create a set of events
        Set<Event> events = new HashSet<Event>();
        Event event1 = new Event("Groppstopper", "goodID");
        event1.setCity("Boise");
        event1.setCountry("United States");
        events.add(event1);

        //Add events to the database
        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(event1);
        eventDao.createEvent(new Event("Jakester", "badID"));

        //Will return events only associated with Groppstopper
        assertEquals(eventDao.findAllDescendantsEvents("Groppstopper").toString(), events.toString());

        //Jakester events are returned, not Groppstopper
        assertNotEquals(eventDao.findAllDescendantsEvents("Jakester").toString(), events.toString());
    }

    @Test
    public void convertDatabaseToJava() throws Exception {
        //Create an Event to add
        Event event = new Event("Groppstopper", UUID.randomUUID().toString());
        event.setCity("Boise");
        event.setCountry("United States");
        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(event);

        //The event converted into java from the database is the same as what was placed in the DB
        assertEquals(eventDao.convertDatabaseToJava(event.getEventID()).toString(), event.toString());
    }

}