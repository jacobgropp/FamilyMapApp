package services;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.UserDataAccess;
import model.AuthorizationToken;
import request.LoginRequest;
import response.LoginResponse;

/**
 * Created by jakeg on 2/15/2018.
 *
 * Logs in the user and returns an auth token
 */
public class LoginService {
    /**
     * Logs in the user and returns an auth token
     *
     * @param request the LoginRequest to access the users data from the database
     *
     * @return result the LoginResponse that generates an authToken, the username, and the users personID
     */
    public LoginResponse login(LoginRequest request){
        //Determine the user exists within the database
        String username = request.getUsername();
        String password = request.getPassword();
        UserDataAccess userDao = new UserDataAccess();
        if(userDao.findUsername(username)){
            //User exists.
            if(userDao.authenticatePassword(username, password)) {
                //Password is correct. Create a new authToken for this session and store in the database.
                AuthorizationToken authToken = new AuthorizationToken(username);
                AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
                authTokenDao.createAuthToken(authToken);

                //Find the personID associated with the Person representing the user
                String personID = userDao.getPersonID(username);

                LoginResponse response = new LoginResponse(authToken.getKey(), username, personID);
                return response;
            }
            else{
                //The password is incorrect. Return an error message.
                String error = "Failed to login. Password is incorrect.";
                LoginResponse response = new LoginResponse(error);
                return response;
            }
        }
        else {
            //The user does not exist. Return an error message.
            String error = "Failed to login. User does not exist.";
            LoginResponse response = new LoginResponse(error);
            return response;
        }
    }
}
