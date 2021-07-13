package services;

import org.junit.After;
import org.junit.Test;

import java.util.Set;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.UserDataAccess;
import model.User;
import request.RegisterRequest;
import response.RegisterResponse;

import static json.Json.load;
import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/7/2018.
 */
public class registerServiceTest {

    @After
    public void tearDown() throws Exception {
        load();
        ClearService clearDB = new ClearService();
        clearDB.clear();
    }

    @Test
    public void register() throws Exception {
        //Create a RegisterRequest
        RegisterRequest request = new RegisterRequest("Groppstopper", "Password");
        request.setEmail("jakegropp@gmail.com");
        request.setFirstName("Jake");
        request.setLastName("Gropp");
        request.setGender("m");

        //Run a RegisterService
        RegisterService service = new RegisterService();
        RegisterResponse actualResponse = service.register(request);

        //Find the AuthorizationToken
        AuthorizationDataAccess authDao = new AuthorizationDataAccess();
        Set<String> keys = authDao.getKeys(request.getUsername());
        String key = keys.iterator().next();

        //Find the personID
        UserDataAccess userDao = new UserDataAccess();
        String personID = userDao.getPersonID(request.getUsername());

        //Create an expected RegisterResponse
        RegisterResponse expectedResponse = new RegisterResponse(key, request.getUsername(), personID);

        //The expected response on a successful register
        assertEquals(expectedResponse.toString(), actualResponse.toString());

        //The response if the user already exists
        assertEquals(new RegisterService().register(new RegisterRequest("Groppstopper", "Password")).toString(),
                new RegisterResponse("Username already exists. Please choose another.").toString());

    }

    @Test
    public void createNewUser() throws Exception {
        //Create a RegisterRequest
        RegisterRequest request = new RegisterRequest("Groppstopper", "Password");
        request.setEmail("jakegropp@gmail.com");
        request.setFirstName("Jake");
        request.setLastName("Gropp");
        request.setGender("m");

        //Run the service
        RegisterService service = new RegisterService();
        User actualUser = service.createNewUser(request);

        //Create the expected user
        User expectedUser = new User("Groppstopper", "Password");
        expectedUser.setEmail("jakegropp@gmail.com");
        expectedUser.setFirstName("Jake");
        expectedUser.setLastName("Gropp");
        expectedUser.setGender("m");
        expectedUser.setPersonID(actualUser.getPersonID());

        //The expected result compared to the actual
        assertEquals(expectedUser.toString(), actualUser.toString());

        //Returns null if the user already exists
        assertNull(new RegisterService().createNewUser(new RegisterRequest("Groppstopper", "Password")));
    }

    @Test
    public void generateAncestryData() throws Exception {
        //This is tested in the FillService test
    }

    @Test
    public void logInNewUser() throws Exception {
        //This essentially calls a LoginService and is tested there
    }

}