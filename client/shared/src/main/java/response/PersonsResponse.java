package response;

import java.util.Set;

import com.bignerdranch.android.familymap.model.Person;

/**
 * Created by jakeg on 2/16/2018.
 */

public class PersonsResponse {
    private Set<Person> persons;
    private String message;
    //Constructor
    /**
     * The response to a service request to find ALL family members of a user
     *
     * people: [Array of person objects]
     *
     * stores an error message if response came out invalid
     */
    public PersonsResponse(Set<Person> persons){
        this.persons = persons;
    }
    /**
     * @return people
     */
    public Set<Person> getPersons(){
        return persons;
    }
    /**
     * @return
     */
    public String getMessage(){
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
        for(Person person : persons){
            newString.append(person.toString() + " ");
        }
        return newString.toString();
    }
}
