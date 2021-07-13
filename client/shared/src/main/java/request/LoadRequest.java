package request;

import java.util.ArrayList;

import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Person;
import com.bignerdranch.android.familymap.model.User;

/**
 * Created by jakeg on 2/16/2018.
 *
 * users[]
 * persons[]
 * EventsResponse[]
 *
 * request to load in specific users, persons, and EventsResponse into database
 */
public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;
    //Constructor
    /**
     * Loads specified family history information into the appropriate users family tree
     *
     * users: [Array of User objects],
     * persons: [Array of Person objects],
     * EventsResponse: [Array of Event objects]
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events){
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
    /**
     * @return users
     */
    public ArrayList<User> getUsers(){
        return users;
    }
    /**
     * @return people
     */
    public ArrayList<Person> getPersons(){
        return persons;
    }
    /**
     * @return EventsResponse
     */
    public ArrayList<Event> getEvents(){
        return events;
    }
}
