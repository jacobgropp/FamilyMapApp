package model;

import java.util.UUID;

/**
 * Created by jakeg on 2/15/2018.
 *
 * A user on the FMS
 * Person ID: Unique identifier for this person (non-empty string)
 * Descendant: User (Username) to which this person belongs
 * First Name: Persons first name (non-empty string)
 * Last Name: Persons last name (non-empty string)
 * Gender: Persons gender (string: f or m)
 * Father: ID of persons father (possibly null)
 * Mother: ID of persons mother (possibly null)
 * Spouse: ID of persons spouse (possibly null)
 */
public class Person {

    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String spouse;
    private String father;
    private String mother;
    //Constructor
    /**
     * The single constructor for the person
     * @param firstName the new username for the user
     * @param lastName the new username for the user
     */
    public Person(String username, String firstName, String lastName){
        personID = UUID.randomUUID().toString();
        this.descendant = username;
        this.firstName = firstName;
        this.lastName = lastName;
        father = null;
        mother = null;
        spouse = null;
    }
    /**
     * Constructor called when a new user is registered
     * @param user the initial person generated that corresponds with the user
     */
    public Person(User user){
        personID = user.getPersonID();
        descendant = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        gender = user.getGender();
        father = null;
        mother = null;
        spouse = null;
    }
    /**
     * the getter for the personID
     * @return the personID
     */
    public String getPersonID(){
        return personID;
    }
    /**
     * the getter for the field descendant
     * @return the descendant
     */
    public String getDescendant(){
        return descendant;
    }
    /**
     *The getter for the field firstName.
     *@return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     *The getter for the field lastName.
     *@return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     *The getter for the field: gender.
     *@return the gender
     */
    public String getGender() {
        return gender;
    }
    /**
     *The getter for the field Father.
     *@return the Father
     */
    public String getFatherID() {
        return father;
    }
    /**
     *The getter for the field Mother.
     *@return the Mother
     */
    public String getMotherID() {
        return mother;
    }
    /**
     *The getter for the field Spouse.
     *@return the Spouse
     */
    public String getSpouseID() {
        return spouse;
    }
    /**
     *The toString method for a person.
     *@return A string representation of a person that satisfies the following
     *format: person[ID: [ID],
     *        firstName: [firstName],
     *        lastName: [lastName],
     *        gender: [gender],
     *        (If father, mother, or spouse exist)
     *        father: [father],
     *        mother: [mother],
     *        spouse: [spouse]]
     */
    @Override
    public String toString() {
       StringBuilder newString = new StringBuilder();
        newString.append("Person{" +
                "descendant: " + descendant + ", " +
                "personID: " + personID + ", " +
                "firstName: " + firstName + ", " +
                "lastName: " + lastName + ", " +
                "gender: " + gender + ", " +
                "fatherID: " + father + ", " +
                "motherID: " + mother + ", " +
                "spouseID: " + spouse + "}");
        return newString.toString();
    }
    //Commands
    /**
     * The setter for the field personID
     * @param personID
     */
    public void setPersonID(String personID){
        this.personID = personID;
    }
    /**
     *The setter for the field descendant.
     *@param descendant the new descendant for this person
     */
    public void setDescendant(String descendant){
       this.descendant = descendant;
    }
    /**
     *The setter for the field firstName.
     *@param firstName the new firstName for this person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     *The setter for the field lastName.
     *@param lastName the new lastName for this person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     *The setter for the field gender.
     *@param gender the new gender for this person
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    /**
     *The setter for the field father.
     *@param fatherID the new father for this person
     */
    public void setFather(String fatherID) {
        this.father = fatherID;
    }
    /**
     *The setter for the field mother.
     *@param motherID the new mother for this person
     */
    public void setMother(String motherID) {
        this.mother = motherID;
    }
    /**
     *The setter for the field spouse.
     *@param spouseID the new spouse for this person
     */
    public void setSpouse(String spouseID) {
        this.spouse = spouseID;
    }
}
