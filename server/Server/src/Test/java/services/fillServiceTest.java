package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import json.Json;
import model.Person;
import model.User;
import request.FillRequest;
import response.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by jakeg on 3/7/2018.
 */
public class fillServiceTest {
    @Before
    public void setUp() throws Exception {
        Json.load();
    }

    @After
    public void tearDown() throws Exception {
        ClearService service = new ClearService();
        service.clear();
    }

    /**
     * This test also tests the generateAncestry method.
     * @throws Exception
     */
    @Test
    public void fill() throws Exception {
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

        //Create a fill request
        FillRequest request = new FillRequest("Groppstopper");

        //Run the service
        FillService service = new FillService();
        Response actualResult = service.fill(request);

        //Expected string
        Response expectedResult = new Response("Successfully added "
                + 31 + " persons, and " +
                61 + " events to the database.");

        //Expected result and actual result are the same
        assertEquals(expectedResult.toString(), actualResult.toString());

        //Will not fill if the user does not exist
        assertEquals(new FillService().fill(new FillRequest("Jakester")).toString(), new Response("User does not exist.").toString());
    }
}