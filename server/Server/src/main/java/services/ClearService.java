package services;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.EventsDataAccess;
import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import response.Response;

/**
 * Created by jakeg on 2/16/2018.
 */

public class ClearService {
    /**
     * performs the clear service by emptying all data in the database
     *
     * @return the clearResult that is a message of a success or failure
     */
    public Response clear(){
        //Create DAO objects that will be used to clear the database
        UserDataAccess userDao = new UserDataAccess();
        PersonDataAccess personDao = new PersonDataAccess();
        EventsDataAccess eventsDao = new EventsDataAccess();
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();

        //Clear the database
        if(userDao.deleteUsers() && personDao.deletePersons() && eventsDao.deleteEvents() && authTokenDao.deleteAuthTokens()){
            return new Response("Clear succeeded.");
        }
        else{
            return new Response("Clear was unsuccessful.");
        }
    }
}
