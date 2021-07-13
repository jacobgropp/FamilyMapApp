package com.bignerdranch.android.familymap.server;

import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Person;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import request.EventsRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.PersonsRequest;
import request.RegisterRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonResponse;
import response.PersonsResponse;
import response.RegisterResponse;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 4/17/2018.
 */
public class ServerProxyTest {

    private String serverHost = "10.4.176.255";
    private int serverPort = 8080;

    @Test
    public void registerUser() throws Exception {
        RegisterRequest request = new RegisterRequest("Groppstopper", "password");
        request.setFirstName("Jake");
        request.setLastName("Gropp");
        request.setEmail("jake.gropp@gmail.com");
        request.setGender("m");

        ServerProxy proxy = new ServerProxy(serverHost, serverPort);


        RegisterResponse actualResponse = proxy.registerUser(request);

        RegisterResponse expectedResponse = new RegisterResponse(actualResponse.getAuthToken(),
                "Groppstopper", actualResponse.getPersonID());

        assertEquals(expectedResponse.toString(), actualResponse.toString());

        RegisterResponse badReponse = proxy.registerUser(request);

        RegisterResponse expectedBadResponse = new RegisterResponse(null, null, null);
        expectedBadResponse.setMessage("Username already exists. Please choose another.");

        assertEquals(expectedBadResponse.toString(), badReponse.toString());
    }

    @Test
    public void loginUser() throws Exception {
        LoginRequest request = new LoginRequest("GroppyCantStoppy", "password");

        ServerProxy proxy = new ServerProxy(serverHost, serverPort);

        LoginResponse actualResponse = proxy.loginUser(request);

        LoginResponse expectedResponse = new LoginResponse(actualResponse.getAuthToken(),
                "GroppyCantStoppy", actualResponse.getPersonID());

        assertEquals(expectedResponse.toString(), actualResponse.toString());

        LoginResponse expectedBadResponse = new LoginResponse(null, null, null);
        expectedBadResponse.setMessage("{\"error\":\"Failed to login. User does not exist.\"}");

        LoginRequest badRequest = new LoginRequest("Pliskin", "password");

        LoginResponse actualBadResponse = proxy.loginUser(badRequest);

        assertEquals(expectedBadResponse.getMessage(), actualBadResponse.getMessage());
    }

    @Test
    public void findPerson() throws Exception {
        PersonRequest request = new PersonRequest("Josh", "0e79acf3-9ab8-4731-a88b-a9a1ecdc8083");

        ServerProxy proxy = new ServerProxy(serverHost, serverPort);

        PersonResponse actualResponse = proxy.findPerson(request);

        PersonResponse expectedResponse = new PersonResponse("GroppyCantStoppy", "Josh");
        expectedResponse.setFirstName("Josh");
        expectedResponse.setLastName("Gropp");
        expectedResponse.setGender("m");
        expectedResponse.setFatherID(null);
        expectedResponse.setMotherID(null);
        expectedResponse.setSpouseID(null);

        assertEquals(expectedResponse.toString(), actualResponse.toString());

        PersonResponse expectedBadResponse = new PersonResponse(null, null);
        expectedBadResponse.setMessage("ERROR: Bad Request");

        PersonRequest badRequest = new PersonRequest("Jake", "0e79acf3-9ab8-4731-a88b-a9a1ecdc8083");
        PersonResponse actualBadResponse = proxy.findPerson(badRequest);

        assertEquals(expectedBadResponse.getMessage(), actualBadResponse.getMessage());
    }

    @Test
    public void findPersons() throws Exception {
        PersonsRequest request = new PersonsRequest("0e79acf3-9ab8-4731-a88b-a9a1ecdc8083");

        ServerProxy proxy = new ServerProxy(serverHost, serverPort);

        PersonsResponse actualResponse = proxy.findPersons(request);

        Set<Person> persons = new HashSet<>();
        Person person = new Person("GroppyCantStoppy", "Josh", "Gropp");
        person.setPersonID("Josh");
        person.setGender("m");
        person.setFather(null);
        person.setMother(null);
        person.setSpouse(null);
        persons.add(person);

        PersonsResponse expectedResponse = new PersonsResponse(persons);

        assertEquals(expectedResponse.toString(), actualResponse.toString());

        PersonsRequest badRequest = new PersonsRequest("badToken");

        PersonsResponse actualBadResponse = proxy.findPersons(badRequest);

        PersonsResponse expectedBadResponse = new PersonsResponse(null);
        expectedBadResponse.setMessage("ERROR: Bad Request");

        assertEquals(expectedBadResponse.getMessage(), actualBadResponse.getMessage());
    }

    @Test
    public void findEvents() throws Exception {
        EventsRequest request = new EventsRequest("0e79acf3-9ab8-4731-a88b-a9a1ecdc8083");

        ServerProxy proxy = new ServerProxy(serverHost, serverPort);

        EventsResponse actualResponse = proxy.findEvents(request);

        Set<Event> events = new HashSet<>();
        Event event = new Event("GroppyCantStoppy", "Josh");
        event.setEventID("SlicedHisButt");
        event.setCity("Idaho");
        event.setCountry("Boise");
        event.setLatitude(43);
        event.setLongitude(-57);
        event.setEventType("ButtSlicing");
        event.setYear("2004");

        events.add(event);

        EventsResponse expectedResponse = new EventsResponse(events);

        assertEquals(expectedResponse.toString(), actualResponse.toString());

        EventsResponse expectedBadResponse = new EventsResponse(null);
        expectedBadResponse.setMessage("ERROR: Bad Request");

        EventsRequest badRequest = new EventsRequest("bad token");
        EventsResponse actualBadResponse = proxy.findEvents(badRequest);

        assertEquals(expectedBadResponse.getMessage(), actualBadResponse.getMessage());
    }

}