package services;

import java.util.Set;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.PersonDataAccess;
import model.Person;
import request.PersonsRequest;
import request.PersonRequest;
import response.PersonResponse;
import response.PersonsResponse;

/**
 * Created by jakeg on 2/16/2018.
 *
 * the object that handles the PersonRequest
 */

public class PersonService {
    /**
     * performs the person service, accessing a person from the database
     *
     * @param request the PersonRequest to access a specific person
     *
     * @return personResult
     */
    public PersonResponse getPerson(PersonRequest request){
        //Find the given Person in the database
        PersonDataAccess personDao = new PersonDataAccess();
        if(personDao.findPerson(request.getPersonID())){
            //The Person exists. Convert from a database entry to a java object and place in a PersonResponse.
            Person person = personDao.convertDatabaseToJava(request.getPersonID());
            PersonResponse response = new PersonResponse(person.getDescendant(), person.getPersonID());

            //Set the remaining properties within the response statement
            response.setFirstName(person.getFirstName());
            response.setLastName(person.getLastName());
            response.setGender(person.getGender());
            response.setFatherID(person.getFatherID());
            response.setMotherID(person.getMotherID());
            response.setSpouseID(person.getSpouseID());

            //Return the new PersonResponse
            return response;
        }
        return null;
    }
    /**
     * performs the person service, accessing a person from the database
     *
     * @param request the PersonRequest to access a specific person
     *
     * @return response the peopleReponse which declares the user succesfully accessed the given people
     */
    public PersonsResponse getAllPersons(PersonsRequest request){
        //Find the given Username in the database attached to the authToken
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        if(authTokenDao.findAuthToken(request.getAuthToken())){
            //AuthToken exists. Pull the username from the authToken.
            String username = authTokenDao.getUser(request.getAuthToken());

            //Pull all Persons that belong to the User and store in an array of Persons
            PersonDataAccess personDao = new PersonDataAccess();
            Set<Person> persons = personDao.findAllDescendantsPersons(username);

            //Return a new PersonsResponse object
            PersonsResponse response = new PersonsResponse(persons);
            return response;
        }
        return null;
    }
}
