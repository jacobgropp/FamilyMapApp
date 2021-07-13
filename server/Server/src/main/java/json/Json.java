package json;

import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static json.JsonConverter.convertJsonToObject;

/**
 * Created by jakeg on 3/7/2018.
 */

public class Json {
    private final static File mnames = new File("Server/json/mnames.json");
    private final static File fnames = new File("Server/json/fnames.json");
    private final static File snames = new File("Server/json/snames.json");
    private final static File locationsJson = new File("Server/json/Locations.json");

    private static Locations locations = new Locations();
    private static Names maleNames = new Names();
    private static Names femaleNames = new Names();
    private static Names surNames = new Names();


    public static void load(){

        //Create a string from the mnames.json file
        StringBuilder maleNamesString = new StringBuilder();
        try (Scanner maleNamesFile = new Scanner(mnames)){
            while (maleNamesFile.hasNext()) {
                maleNamesString.append(maleNamesFile.next());
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }

        //Create a string from the fnames.json file
        StringBuilder femaleNamesString = new StringBuilder();
        try (Scanner femaleNamesFile = new Scanner(fnames)){
            while (femaleNamesFile.hasNext()) {
                femaleNamesString.append(femaleNamesFile.next());
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }

        //Create a string from the snames.json file
        StringBuilder surNamesString = new StringBuilder();
        try (Scanner surNamesFile = new Scanner(snames)){
            while (surNamesFile.hasNext()) {
                surNamesString.append(surNamesFile.next());
            }

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }

        //Create a string from the locations.json file
        StringBuilder locationsString = new StringBuilder();
        try (Scanner locationsFile = new Scanner(locationsJson)){
            while (locationsFile.hasNext()) {
                locationsString.append(locationsFile.next());
            }

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return;
        }

        maleNames = (Names)convertJsonToObject(maleNamesString.toString(), new Names());

        femaleNames = (Names)convertJsonToObject(femaleNamesString.toString(), new Names());

        surNames = (Names)convertJsonToObject(surNamesString.toString(), new Names());

        locations = (Locations)convertJsonToObject(locationsString.toString(), new Locations());
    }

    /**
     * Static randomNumber function.
     * @param length
     * @return randomNumber
     */
    public static int randomNumber(int length) {
        Random random = new Random();
        int randomNumber = random.nextInt(length);
        return randomNumber;
    }

    /**
     * Generates a random male name from the provided file
     * @return unique male name
     */
    public static String getUniqueMaleName(){
        int randomIndex = randomNumber(maleNames.getNames().length);
        return maleNames.getNames()[randomIndex];
    }

    /**
     * Generates a random female name from the provided file
     * @return unique female name
     */
    public static String getUniqueFemaleName(){
        int randomIndex = randomNumber(femaleNames.getNames().length);
        return femaleNames.getNames()[randomIndex];
    }


    public static String getUniqueSurName(){
        int randomIndex = randomNumber(surNames.getNames().length);
        return surNames.getNames()[randomIndex];
    }

    /**
     * Generates a random location from the provided file
     * @return unique location
     */
    public static Location getUniqueLocation(){
        int randomIndex = randomNumber(femaleNames.getNames().length);
        return locations.getLocations()[randomIndex];
    }
}
