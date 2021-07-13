package DataAccessObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataAccessObjects.Database.DatabaseConnection;
import model.User;
import services.ClearService;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/6/2018.
 */
public class userDataAccessTest extends DatabaseConnection{
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
    public void createUser() throws Exception {
        //Create an Event to add
        User user = new User("Groppstopper", "password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");
        UserDataAccess userDao = new UserDataAccess();

        //This will always return true as long as there is a USER table to write to.
        assertTrue(userDao.createUser(user));
    }

    @Test
    public void findUser() throws Exception {
        //Create an Event to add
        User user = new User("Groppstopper", "password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);

        //Groppstopper exists in the database
        assertTrue(userDao.findUsername("Groppstopper"));

        //Jakester does not exist in the database
        assertFalse(userDao.findUsername("Jakester"));
    }

    @Test
    public void authenticatePassword() throws Exception {
        //Create an Event to add
        User user = new User("Groppstopper", "password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);

        //Authenticate a correct password from an existing user.
        assertTrue(userDao.authenticatePassword(user.getUsername(), user.getPassword()));

        //Cannot authenticate a false password from an existing user.
        assertFalse(userDao.authenticatePassword(user.getUsername(), "wrongPassword"));

        //Cannot authenticate a password from user that does not exist.
        assertFalse(userDao.authenticatePassword("Jakester", "wrongPassword"));
    }

    @Test
    public void deleteUsers() throws Exception {
        //Create an Event to add
        User user = new User("Groppstopper", "password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");
        User user1 = new User("Jakers", "password");
        user1.setEmail("jakegropp@gmail.com");
        user1.setFirstName("Jake");
        user1.setLastName("Gropp");
        user1.setGender("m");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);
        userDao.createUser(user1);

        //Will alwyas return false as long as there is a USER table to delete from
        assertEquals(userDao.deleteUsers(), true);
    }

    @Test
    public void getPersonID() throws Exception {
        //Create an Event to add
        User user = new User("Groppstopper", "password");
        user.setEmail("jakegropp@gmail.com");
        user.setFirstName("Jake");
        user.setLastName("Gropp");
        user.setGender("m");
        UserDataAccess userDao = new UserDataAccess();
        userDao.createUser(user);

        //Finds the user personID if the user exists
        assertEquals(userDao.getPersonID(user.getUsername()), user.getPersonID());

        //Cannot retrieve personID from a user that does not exist
        assertNotEquals(userDao.getPersonID("jakester"), "This id does not exist");
    }

}