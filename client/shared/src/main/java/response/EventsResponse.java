package response;

import java.util.Set;

import com.bignerdranch.android.familymap.model.Event;

/**
 * Created by jakeg on 2/16/2018.
 *
 * the response from an event service that stores all family EventsResponse for the user
 */

public class EventsResponse {
    private Set<Event> events;
    private String message;
    //Constructor
    /**
     * EventsResponse: [array of Event objects]
     */
    public EventsResponse(Set<Event> events){
        this.events = events;
    }
    /**
     * @return EventsResponse
     */
    public Set<Event> getEvents(){
        return events;
    }
    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * toString for the EventsResponse object
     * @return toString
     */
    @Override
    public String toString(){
        StringBuilder newString = new StringBuilder();
        for(Event event : events){
            newString.append(event.toString() + " ");
        }
        return newString.toString();
    }
}
