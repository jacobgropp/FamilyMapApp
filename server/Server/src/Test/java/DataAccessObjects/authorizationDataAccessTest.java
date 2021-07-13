package DataAccessObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataAccessObjects.Database.DatabaseConnection;
import model.AuthorizationToken;
import services.ClearService;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/6/2018.
 */
public class authorizationDataAccessTest extends DatabaseConnection{
    @Before
    public void setUp() throws Exception {
        openDatabase();
        createTables();
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearDatabase = new ClearService();
        clearDatabase.clear();
        closeDatabase();
    }

    @Test
    public void createAuthToken() throws Exception {
        //Create an AuthToken to add
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();

        //Test the functionality of the create method.
        //This should always work if passed an authToken.
        assertTrue(authTokenDao.createAuthToken(authToken));
    }

    @Test
    public void findAuthToken() throws Exception {
        //Create an AuthToken to find
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        authTokenDao.createAuthToken(authToken);

        //If the authToken key exists, return true.
        assertTrue(authTokenDao.findAuthToken(authToken.getKey()));

        //If the authToken key does no exist, return false.
        assertFalse(authTokenDao.findAuthToken("falseToken"));
    }

    @Test
    public void deleteAuthTokens() throws Exception {
        //Create two AuthTokens with the same username delete
        AuthorizationToken authToken1 = new AuthorizationToken("Groppstopper");
        AuthorizationToken authToken2 = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        authTokenDao.createAuthToken(authToken1);
        authTokenDao.createAuthToken(authToken2);

        //Will return true always as long as there is a table to delete. (There always is).
        assertTrue(authTokenDao.deleteAuthTokens());
    }

    @Test
    public void getUser() throws Exception {
        //Create an AuthToken to find
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        authTokenDao.createAuthToken(authToken);

        //Expect true if the user exists.
        assertEquals(authToken.getUser(), authTokenDao.getUser(authToken.getKey()));

        //Return false if the user does not exist
        assertNotEquals("Jakester", authTokenDao.getUser(authToken.getKey()));
    }

    @Test
    public void getKeys() throws Exception {
        //Create an AuthToken to find
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
        authTokenDao.createAuthToken(authToken);

        //Assume the authToken key is within the created key set for the user
        assertTrue(authTokenDao.getKeys(authToken.getUser()).contains(authToken.getKey()));

        //Return false if the authToken key is NOT within the created key set
        assertFalse(authTokenDao.getKeys(authToken.getUser()).contains("false key"));


    }

}