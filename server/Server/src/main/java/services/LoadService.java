package services;

import java.util.ArrayList;

import DataAccessObjects.EventsDataAccess;
import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import response.Response;

/**
 * Created by jakeg on 2/16/2018.
 *
 * the object that handles the load request
 */
public class LoadService {
    /**
     * Clears all data from the database (just like the /clear API), and then loads the
     * posted user, person, and event data into the database.
     *
     * @param request the LoadRequest delivered by the client to be entered into the database
     *
     * @return result the loadResponse that is a message stating whether it was a success or failure
     */
    public Response load(LoadRequest request){
        try {
            //Clear all data from the database
            ClearService clearDatabase = new ClearService();
            clearDatabase.clear();

            //Add users from the request to the database
            addUsers(request.getUsers());

            //Add persons from the request to the database
            addPersons(request.getPersons());

            //Add events from the request to the database
            addEvents(request.getEvents());

            String success = "Successfully added " +  request.getUsers().size() + " users, "
                    + request.getPersons().size() + " persons, and " + request.getEvents().size() +
                     " events to the database.";
            return new Response(success);
        }
        catch(Exception e){
            String error = e.getClass().getName() + ": " + e.getMessage();
            return new Response(error);
        }


    }

    /**
     * Places all users in the User array into the database
     *
     * @param users
     */
    public void addUsers(ArrayList<User> users){
        //Iterate through all of the users in the array
        for(User user : users){
            //Add the new User to the database
            UserDataAccess userDao = new UserDataAccess();
            userDao.createUser(user);
        }
    }

    /**
     * Places all persons in the Person array into the database
     * @param persons
     */
    public void addPersons(ArrayList<Person> persons){
        //Iterate through all of the persons in the array
        for(Person person : persons){
            //Add the new Person to the database
            PersonDataAccess personDao = new PersonDataAccess();
            personDao.createPerson(person);
        }
    }

    /**
     * Places all events in the Event array into the database
     * @param events
     */
    public void addEvents(ArrayList<Event> events){
        //Iterate through all of the events in the array
        for(Event event : events){
            //Add the new User to the database
            EventsDataAccess eventDao = new EventsDataAccess();
            eventDao.createEvent(event);
        }
    }

}
