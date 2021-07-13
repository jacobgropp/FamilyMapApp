package services;

import java.util.Random;

import DataAccessObjects.EventsDataAccess;
import DataAccessObjects.PersonDataAccess;
import DataAccessObjects.UserDataAccess;
import json.Location;
import model.Event;
import model.Person;
import request.FillRequest;
import response.Response;

import static json.Json.getUniqueFemaleName;
import static json.Json.getUniqueLocation;
import static json.Json.getUniqueMaleName;
import static json.Json.getUniqueSurName;

/**
 * Created by jakeg on 2/15/2018.
 *
 * the object that handles the fill request
 */
public class FillService {
    private int personsAdded = 0;
    private int eventsAdded = 0;
    private int highestYear = 2000;
    /**
     * Performs the FillService, filling the database with a number of specified generations.
     * Default generations to create is 4. Otherwise, it can be specified by the user and
     * is stored in the FillRequest.
     *
     * @return a fillResponse that is a message of a success or failure
     */
    public Response fill(FillRequest request){
        try {

            //Create a java object User to be used to find the user in the database
            String username = request.getUsername();
            UserDataAccess userDao = new UserDataAccess();
            if (userDao.findUsername(username)) {

                //User exists. Save the user's person.
                String personID = userDao.getPersonID(username);

                PersonDataAccess personDao = new PersonDataAccess();
                Person userPerson = personDao.convertDatabaseToJava(personID);

                //Delete user's associated ancestry data.
                if (personDao.deleteDescendantsPersons(username)) {
                    if(new EventsDataAccess().deleteAllDescendantsEvents(username)) {

                        //Generate a birth year for the user person
                        generateBirthEvent(userPerson, highestYear);

                        //Ancestry data is clear. Generate the given generations of ancestry data for the user.
                        //We subtract 40 from highestYear to account for a generation passing
                        highestYear = highestYear - 40;
                        generateAncestry(userPerson, request.getGenerations(), highestYear);

                        //Finally add the userPerson to the database with his linked family
                        personDao.createPerson(userPerson);
                        personsAdded++;

                        //Report a success message
                        String success = "Successfully added "
                                + personsAdded + " persons, and " +
                                eventsAdded + " events to the database.";
                        return new Response(success);
                    }
                    return new Response("Could not empty user's family tree.");
                }
            }
            return new Response("User does not exist.");
        }
        catch(Exception e){
            e.printStackTrace();
            String error = e.getClass().getName() + "': '" + e.getMessage();
            return new Response(error);
        }
    }


    /**
     * Generates the given amount of generations of ancestry data recursively
     *
     * @param person
     * @param generations
     */
    public void generateAncestry(Person person, int generations, int year){
        year = year - 40;
        generations--;
        //Generate father
        Person father = new Person(person.getDescendant(), getUniqueMaleName(),getUniqueSurName() );
        father.setGender("m");
        person.setFather(father.getPersonID());
        if(generations != 0) {
            generateAncestry(father, generations, year);
        }

        //Generate mother
        Person mother = new Person(person.getDescendant(), getUniqueFemaleName(), getUniqueSurName());
        mother.setGender("f");
        person.setMother(mother.getPersonID());
        if(generations != 0) {
            generateAncestry(mother, generations, year);
        }

        //Generate birth for father and mother
        generateBirthEvent(father, year);
        generateBirthEvent(mother, year);

        //Link father and mother as spouses and add wedding
        father.setSpouse(mother.getPersonID());
        mother.setSpouse(father.getPersonID());
        generateWeddingEvent(mother, father, year);

        //Place father and mother in the database
        PersonDataAccess personDao = new PersonDataAccess();
        personDao.createPerson(father);
        personsAdded++;
        personDao.createPerson(mother);
        personsAdded++;
    }

    public void generateBirthEvent(Person person, int yearHigh){
        int yearLow = yearHigh - 40;
        //Generate a birth for the given person
        Event birth = new Event(person.getDescendant(), person.getPersonID());

        //Generate a random location for the event
        Location randomLocation = getUniqueLocation();

        //Set the randomLocation within the Event
        birth.setLatitude(Double.parseDouble(randomLocation.getLatitude()));
        birth.setLongitude(Double.parseDouble(randomLocation.getLongitude()));
        birth.setCity(randomLocation.getCity());
        birth.setCountry(randomLocation.getCountry());

        //Set the eventType
        birth.setEventType("Birth");

        //Generate a unique year for the event
        birth.setYear(randomYear(yearHigh, yearLow));

        //Place the new event in the database
        new EventsDataAccess().createEvent(birth);
        eventsAdded++;
    }

    public void generateWeddingEvent(Person man, Person woman, int year){
        Event weddingForMan = new Event(man.getDescendant(), man.getPersonID());
        Event weddingForWoman = new Event(woman.getDescendant(), woman.getPersonID());
        Location randomLocation = getUniqueLocation();
        //Set Latitude
        weddingForMan.setLatitude(Double.parseDouble(randomLocation.getLatitude()));
        weddingForWoman.setLatitude(Double.parseDouble(randomLocation.getLatitude()));
        //Set Longitude
        weddingForMan.setLongitude(Double.parseDouble(randomLocation.getLongitude()));
        weddingForWoman.setLongitude(Double.parseDouble(randomLocation.getLongitude()));
        //Set City
        weddingForMan.setCity(randomLocation.getCity());
        weddingForWoman.setCity(randomLocation.getCity());
        //Set Country
        weddingForMan.setCountry(randomLocation.getCountry());
        weddingForWoman.setCountry(randomLocation.getCountry());
        //Set EventType
        weddingForMan.setEventType("Wedding");
        weddingForWoman.setEventType("Wedding");
        //Set year
        String weddingYear = randomYear(year - 10, year - 30);
        weddingForMan.setYear(weddingYear);
        weddingForWoman.setYear(weddingYear);

        new EventsDataAccess().createEvent(weddingForMan);
        eventsAdded++;
        new EventsDataAccess().createEvent(weddingForWoman);
        eventsAdded++;

    }

    private static String randomYear(int high, int low){
        Random random = new Random();
        int result = random.nextInt(high - low) + low;
        return Integer.toString(result);
    }
}
