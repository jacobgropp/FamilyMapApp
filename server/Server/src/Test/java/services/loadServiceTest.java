package services;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import response.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by jakeg on 3/6/2018.
 */
public class loadServiceTest {

    @After
    public void tearDown() throws Exception {
        ClearService clearDB = new ClearService();
        clearDB.clear();
    }

    @Test
    public void load() throws Exception {
        //Add Users to the database
        User Groppstopper = new User("Groppstopper", "Password");
        Groppstopper.setEmail("joshgropp@gmail.com");
        Groppstopper.setFirstName("Josh");
        Groppstopper.setLastName("Gropp");
        Groppstopper.setGender("m");

        User Jakester = new User("Jakester", "Password");
        Jakester.setEmail("jakegropp@gmail.com");
        Jakester.setFirstName("Jake");
        Jakester.setLastName("Gropp");
        Jakester.setGender("m");

        /*UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(Groppstopper);
        userDao.createUser(Jakester);*/

        //Add Persons to the database
        Person Josh = new Person(Groppstopper);

        Person Jake = new Person(Jakester);

        /*PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(Josh);
        personDao.createPerson(Jake);*/

        //Add Events to the database
        Event JoshBirth = new Event("Groppstopper", Josh.getPersonID());
        JoshBirth.setLatitude(43);
        JoshBirth.setLongitude(-116);
        JoshBirth.setCity("Boise");
        JoshBirth.setCountry("United States");
        JoshBirth.setEventType("Birth");
        JoshBirth.setYear("1997");

        Event JakeBirth = new Event("Jakester", Josh.getPersonID());
        JakeBirth.setLatitude(43);
        JakeBirth.setLongitude(-116);
        JakeBirth.setCity("Boise");
        JakeBirth.setCountry("United States");
        JakeBirth.setEventType("Birth");
        JakeBirth.setYear("1994");

       /* EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(JoshBirth);
        eventDao.createEvent(JakeBirth);*/

        //Create arrays for each model type
        ArrayList<User> users = new ArrayList<User>();
        users.add(Groppstopper);
        users.add(Jakester);
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(Josh);
        persons.add(Jake);
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(JoshBirth);
        events.add(JakeBirth);

        //Create a LoadRequest
        LoadRequest request = new LoadRequest(users, persons, events);
        Response actualResponse = new LoadService().load(request);

        Response expectedResponse = new Response("Successfully added 2 users, 2 persons, and 2 events to the database.");

        //2 users, 2 persons, and 2 events were added.
        assertEquals(expectedResponse.toString(), actualResponse.toString());
    }


}