package services;

import org.junit.After;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.PersonDataAccess;
import model.AuthorizationToken;
import model.Person;
import model.User;
import request.PersonRequest;
import request.PersonsRequest;
import response.PersonResponse;
import response.PersonsResponse;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/6/2018.
 */
public class personServiceTest {
    @After
    public void tearDown() throws Exception {
        ClearService clearDB = new ClearService();
        clearDB.clear();
    }

    @Test
    public void getPerson() throws Exception {
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

        //Add an authToken to the database
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authDao = new AuthorizationDataAccess();
        authDao.createAuthToken(authToken);

        //Create a PersonRequest
        PersonRequest request = new PersonRequest(person.getPersonID(), authToken.getKey());
        PersonService service = new PersonService();
        PersonResponse actualResponse = service.getPerson(request);

        //Create the expected response
        PersonResponse expectedResponse = new PersonResponse(person.getDescendant(), person.getPersonID());
        expectedResponse.setFirstName(person.getFirstName());
        expectedResponse.setLastName(person.getLastName());
        expectedResponse.setGender(person.getGender());

        //Returns the expected person
        assertEquals(expectedResponse.toString(), actualResponse.toString());

        //Returns null if the person does not exist
        assertNull(new PersonService().getPerson(new PersonRequest("falseID", "falseToken")));
    }

    @Test
    public void getAllPeople() throws Exception {
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

        //Add an authToken to the database
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authDao = new AuthorizationDataAccess();
        authDao.createAuthToken(authToken);

        //Create a PersonsRequest
        PersonsRequest request = new PersonsRequest(authToken.getKey());
        PersonService service = new PersonService();
        PersonsResponse actualResponse = service.getAllPersons(request);

        //Create the expected response
        Set<Person> persons = new HashSet<Person>();
        persons.add(person);
        PersonsResponse expectedResponse = new PersonsResponse(persons);

        //Returns the expected result response
        assertEquals(expectedResponse.toString(), actualResponse.toString());

        //Returns null if the authToken is invalid
        assertNull(new PersonService().getAllPersons(new PersonsRequest("falseID")));

    }

}