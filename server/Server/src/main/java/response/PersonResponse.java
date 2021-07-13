package response;

/**
 * Created by jakeg on 2/16/2018.
 *
 * the response from the PersonService
 */

public class PersonResponse {
    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    //Constructor
    /**
     * stores the information from the database to send back to the client
     *
     * descendant: String
     * personID: String
     * firstName: String
     * lastName: String
     * gender: String (f or m)
     * father: String (optional)
     * mother: String (optional)
     * spouse: String (optional)
     *
     */
    public PersonResponse(String descendant, String personID){
        this.descendant = descendant;
        this.personID = personID;
    }
    //GETTERS
    /**
     * The getter for the field descendant
     * @return descendant
     */
    public String getDescendant(){
        return descendant;
    }
    /**
     * The getter for the field personID
     * @return personID
     */
    public String getPersonID(){
        return personID;
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
     *The getter for the field gender.
     *@return the gender
     */
    public String getGender() {
        return gender;
    }
    /**
     *The getter for the field Father.
     *@return the Father
     */
    public String getFather() {
        return fatherID;
    }
    /**
     *The getter for the field Mother.
     *@return the Mother
     */
    public String getMother() {
        return motherID;
    }
    /**
     *The getter for the field Spouse.
     *@return the Spouse
     */
    public String getSpouse() {
        return spouseID;
    }

    //SETTERS

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    /**
     * @param fatherID
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }
    /**
     * @param motherID
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }
    /**
     * @param spouseID
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * the toString method
     * @return toString
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
                "fatherID: " + fatherID + ", " +
                "motherID: " + motherID + ", " +
                "spouseID: " + spouseID + "}");
        return newString.toString();
    }
}
