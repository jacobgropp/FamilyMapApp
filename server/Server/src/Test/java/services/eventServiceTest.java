package services;

import org.junit.After;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.EventsDataAccess;
import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import model.AuthorizationToken;
import model.Event;
import model.Person;
import model.User;
import request.EventRequest;
import request.EventsRequest;
import response.EventResponse;
import response.EventsResponse;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/6/2018.
 */
public class eventServiceTest {

    @After
    public void tearDown() throws Exception {
        ClearService clearDB = new ClearService();
        clearDB.clear();
    }

    @Test
    public void getEvent() throws Exception {
        //Add a User to the database
        User user = new User("Groppstopper", "Password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);

        //Add a Person to the database
        Person person = new Person(user);
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person);

        //Add Event to the database
        Event event = new Event("Groppstopper", person.getPersonID());
        event.setLatitude(43);
        event.setLongitude(-116);
        event.setCity("Boise");
        event.setCountry("United States");
        event.setEventType("Birth");
        event.setYear("1994");
        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(event);

        //Add an authToken to the database
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authDao = new AuthorizationDataAccess();
        authDao.createAuthToken(authToken);

        //Create an EventRequest
        EventRequest request = new EventRequest(event.getEventID(), authToken.getKey());
        EventService service = new EventService();
        EventResponse actualResponse = service.getEvent(request);

        //Create the expected response
        EventResponse expectedResponse = new EventResponse(event.getEventID(), event.getDescendant(), event.getPersonID());
        expectedResponse.setLatitude(event.getLatitude());
        expectedResponse.setLongitude((event.getLongitude()));
        expectedResponse.setCity(event.getCity());
        expectedResponse.setCountry(event.getCountry());
        expectedResponse.setEventType(event.getEventType());
        expectedResponse.setYear(event.getYear());

        //Returns the expected event
        assertEquals(expectedResponse.toString(), actualResponse.toString());

        //Returns null if the event does not exist
        assertNull(new EventService().getEvent(new EventRequest("falseID", "falseToken")));
    }

    @Test
    public void getAllEvents() throws Exception {
        //Add a User to the database
        User user = new User("Groppstopper", "Password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");

        //Add a Person to the database
        Person person = new Person(user);
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person);

        //Add birth, baptism, and graduation to the database
        Event birth = new Event("Groppstopper", person.getPersonID());
        birth.setLatitude(43);
        birth.setLongitude(-116);
        birth.setCity("Boise");
        birth.setCountry("United States");
        birth.setEventType("Birth");
        birth.setYear("1994");

        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(birth);

        //Add an authToken to the database
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authDao = new AuthorizationDataAccess();
        authDao.createAuthToken(authToken);

        //Create an EventRequest
        EventsRequest request = new EventsRequest(authToken.getKey());
        EventService service = new EventService();
        EventsResponse actualResponse = service.getAllEvents(request);

        //Create the expected response
        Set<Event> events = new HashSet<Event>();
        events.add(birth);
        EventsResponse expectedResponse = new EventsResponse(events);

        //Returns the expected result response
        assertEquals(expectedResponse.toString(), actualResponse.toString());

        //Returns null if the authToken is invalid does not exist
        assertNull(new EventService().getAllEvents(new EventsRequest("falseID")));
    }

}