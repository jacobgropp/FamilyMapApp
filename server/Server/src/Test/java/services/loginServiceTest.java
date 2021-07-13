package services;

import org.junit.After;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.UserDataAccess;
import model.User;
import request.LoginRequest;
import response.LoginResponse;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/7/2018.
 */
public class loginServiceTest {
    @After
    public void tearDown() throws Exception {
        ClearService clearDatabase = new ClearService();
        clearDatabase.clear();
    }

    @Test
    public void login() throws Exception {
        //Add a User
        User user = new User("Groppstopper", "Password");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);

        //Create a LoginRequest
        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());

        //Run the service
        LoginService service = new LoginService();
        LoginResponse actualResponse = service.login(request);

        //Create the expected Response
        AuthorizationDataAccess authDao = new AuthorizationDataAccess();
        Set<String> keys = new HashSet<String>();
        keys = authDao.getKeys(user.getUsername());
        String key = keys.iterator().next();
        LoginResponse expectedResponse = new LoginResponse(key, user.getUsername(), user.getPersonID());

        //Successfully logs in.
        assertEquals(expectedResponse.toString(), actualResponse.toString());

        //Fails to login with a user that does not exist
        assertEquals(new LoginService().login(new LoginRequest("Jakester", "fake")).toString(),
                new LoginResponse("Failed to login. User does not exist.").toString());

        //Fails to login in with incorrect password
        assertEquals(new LoginService().login(new LoginRequest("Groppstopper", "badPassword")).toString(),
                new LoginResponse("Failed to login. Password is incorrect.").toString());
    }

}