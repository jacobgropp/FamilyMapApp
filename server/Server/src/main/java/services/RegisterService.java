package services;

import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import model.Person;
import model.User;
import request.FillRequest;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;

/**
 * Created by jakeg on 2/15/2018.
 *
 * the object that handles the register request
 */
public class RegisterService {
    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new
     * user, logs the user in, and returns an auth token.
     *
     * @param request RegisterRequest with the appropriate information to register a user
     * @return response RegisterResponse declaring the user has been registered and logged in
     */
    public RegisterResponse register(RegisterRequest request){
        RegisterResponse response = new RegisterResponse(null, null, null);
        try {
            //Create the new user java object and store in the server database
            User user = createNewUser(request);
            if (user != null) {

                //Generate the Person object representing the user and their 4 generations of ancestor data
                generateAncestryData(user);

                //Log in the new user for the first time and create a RegisterResponse with the login authToken
                response = logInNewUser(user);
                return response;
            }
            response.setMessage("Username already exists. Please choose another.");
            return response;
        }
        catch(Exception e){
            response.setMessage(e.getClass().getName() + ": " + e.getMessage());
            return response;
        }
    }

    /**
     * Creates the new user as a java object
     * Stores newly created user data in the server database
     * @param request
     * @return
     */
    public User createNewUser(RegisterRequest request){
        try{
            //Check if the user already exists
            if(!new UserDataAccess().findUsername(request.getUsername())) {
                //Create the new user
                User user = new User(request.getUsername(), request.getPassword());
                user.setEmail(request.getEmail());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setGender(request.getGender());

                //Store the new user's data in the server database
                UserDataAccess userDao = new UserDataAccess();
                userDao.createUser(user);
                return user;
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    /**
     * Generates initial ancestry data for the newly created user
     * @param user
     */
    public void generateAncestryData(User user){
        //Generate the Person object that represents the user
        Person userPerson = new Person(user);

        //Place the newly generated Person in the database
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(userPerson);

        //Create a FillRequest to generate the Father, Mother, and Spouse for the Person object representing the user
        FillRequest newRegisterFillRequest = new FillRequest(user.getUsername());
        FillService newRegisterFill = new FillService();
        newRegisterFill.fill(newRegisterFillRequest);
    }

    /**
     * Creates a LoginRequest, performs the first time login,
     * creates a login response with a new authToken, and
     * returns a new RegisterResponse with the give authToken
     * @param user
     * @return response
     */
    public RegisterResponse logInNewUser(User user){
        LoginRequest firstTimeLoginRequest = new LoginRequest(user.getUsername(), user.getPassword());
        LoginService firstTimeLogin = new LoginService();
        LoginResponse firstTimeLoginResponse = firstTimeLogin.login(firstTimeLoginRequest);
        RegisterResponse response = new RegisterResponse(firstTimeLoginResponse.getAuthToken(), user.getUsername(), user.getPersonID());
        return response;
    }
}