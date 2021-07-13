package services;

import org.junit.Before;
import org.junit.Test;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.EventsDataAccess;
import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import model.AuthorizationToken;
import model.Event;
import model.Person;
import model.User;
import response.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by jakeg on 3/6/2018.
 */
public class clearServiceTest {
    @Before
    public void setUp() throws Exception {
        //Add a User
        User user = new User("Groppstopper", "Password");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);

        //Add a Person
        Person person = new Person("Groppstopper", "Jake", "Gropp");
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person);

        //Add an Event
        Event event = new Event("Groppstopper", person.getPersonID());
        EventsDataAccess eventDao = new EventsDataAccess();
        eventDao.createEvent(event);

        //Add an authToken
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        authTokenDao.createAuthToken(authToken);
    }

    @Test
    public void clear() throws Exception {
        //Clear will always work unless it were to try to delete from a non-existing
        //database which will never happen in this case as the database is created upon boot up.
        assertEquals(new ClearService().clear().toString(), new Response("Clear succeeded.").toString());
    }

}