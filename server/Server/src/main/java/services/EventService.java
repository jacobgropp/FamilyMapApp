package services;

import java.util.Set;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.EventsDataAccess;
import model.Event;
import request.EventRequest;
import request.EventsRequest;
import response.EventResponse;
import response.EventsResponse;
import response.Response;

/**
 * Created by jakeg on 2/16/2018.
 * the object that handles the EventRequest
 */

public class EventService {
    /**
     * Returns the single Event object with the specified ID.
     *
     * @param request the EventRequest asking to access a specific event
     *
     * @return EventResponse or null with error message if failure
     */
    public EventResponse getEvent(EventRequest request){
        //Find the given Event in the database
        EventsDataAccess eventDao = new EventsDataAccess();
        if(eventDao.findEvent(request.getEventID())){
            //The Event exists. Convert from a database entry to a java object and place in an EventResponse.
            Event event = eventDao.convertDatabaseToJava(request.getEventID());
            EventResponse response = new EventResponse(event.getEventID(), event.getDescendant(), event.getPersonID());

            //Set the remaining components for the response statement
            response.setLatitude(event.getLatitude());
            response.setLongitude(event.getLongitude());
            response.setCity(event.getCity());
            response.setCountry(event.getCountry());
            response.setEventType(event.getEventType());
            response.setYear(event.getYear());

            //Return the new EventResponse
            return response;
        }
        return null;
    }

    /**
     * Returns an array of EventsResponse for the users entire family
     *
     * @param request the EventsRequest
     *
     * @return EventsResponse or null with error message if failure
     */
    public EventsResponse getAllEvents(EventsRequest request){
        //Find the given Username in the database attached to the authToken
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        if(authTokenDao.findAuthToken(request.getAuthToken())){
            //AuthToken exists. Pull the username from the authToken.
            String username = authTokenDao.getUser(request.getAuthToken());

            //Pull all Events that belong to the User and store in a set of Events
            EventsDataAccess eventDao = new EventsDataAccess();
            Set<Event> events = eventDao.findAllDescendantsEvents(username);

            //Return a new PersonsResponse object
            EventsResponse response = new EventsResponse(events);
            return response;
        }
        return null;
    }
}
