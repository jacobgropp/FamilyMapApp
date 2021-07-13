package DataAccessObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import DataAccessObjects.Database.DatabaseConnection;
import model.Person;
import services.ClearService;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/6/2018.
 */
public class personDataAccessTest extends DatabaseConnection{
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
    public void createPerson() throws Exception {
        //Create an Person to add
        Person person = new Person("Groppstopper", "Jake", "Gropp");
        person.setGender("m");
        PersonDataAccess personDao = new PersonDataAccess();

        //Will always return true if there exists a table to write the person to.
        assertTrue(personDao.createPerson(person));
    }

    @Test
    public void findPerson() throws Exception {
        //Create an Person to add
        Person person = new Person("Groppstopper", "Jake", "Gropp");
        person.setGender("m");
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person);

        //True if the person exists
        assertTrue(personDao.findPerson(person.getPersonID()));

        //False if the person does not exist
        assertFalse(personDao.findPerson("FalsePerson"));
    }

    @Test
    public void deletePersons() throws Exception {
        //Create two Persons to add
        Person person1 = new Person("Groppstopper", "Jake", "Gropp");
        person1.setGender("m");
        Person person2 = new Person("Groppstopper", "Joe", "Gropp");
        person2.setGender("m");
        PersonDataAccess personDao = new PersonDataAccess();

        //Always returns true if there is a table named "PERSONS" to delete table from.
        assertTrue(personDao.deletePersons());
    }

    @Test
    public void deleteDescendantsPersons() throws Exception {
        Set<Person> persons = new HashSet<Person>();
        Person person1 = new Person("Groppstopper", "Jake", "Gropp");
        person1.setGender("m");
        persons.add(person1);
        Person person2 = new Person("Groppstopper", "Joe", "Gropp");
        person2.setGender("m");
        persons.add(person2);
        Person person3 = new Person("Jakester", "Janice", "Gorringe");
        person3.setGender("f");
        persons.add(person3);

        //Add persons to database
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person1);
        personDao.createPerson(person2);
        personDao.createPerson(person3);

        //Will always return true as long as there exists data to delete connected to Groppstopper
        assertTrue(personDao.deleteDescendantsPersons("Groppstopper"));

        //Check to make sure Jakester's person still exists.
        assertTrue(personDao.findPerson(person3.getPersonID()));
    }

    @Test
    public void findAllDescendantsPersons() throws Exception {
        Set<Person> persons = new HashSet<Person>();
        Person person1 = new Person("Groppstopper", "Jake", "Gropp");
        person1.setGender("m");
        persons.add(person1);

        //Add persons to database
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person1);
        personDao.createPerson(new Person("Jakester", "Jake", "Gropp"));

        //Will return events only associated with Groppstopper
        assertEquals(personDao.findAllDescendantsPersons("Groppstopper").toString(), persons.toString());

        //Jakester events are returned, not Groppstopper
        assertNotEquals(personDao.findAllDescendantsPersons("Jakester").toString(), persons.toString());
    }

    @Test
    public void convertDatabaseToJava() throws Exception {
        Set<Person> persons = new HashSet<Person>();
        Person person = new Person("Groppstopper", "Jake", "Gropp");
        person.setGender("m");
        person.setFather(UUID.randomUUID().toString());
        person.setMother(UUID.randomUUID().toString());
        person.setSpouse(UUID.randomUUID().toString());

        //Add persons to database
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(person);

        //The event converted into java from the database is the same as what was placed in the DB
        assertEquals(personDao.convertDatabaseToJava(person.getPersonID()).toString(), person.toString());
    }

}