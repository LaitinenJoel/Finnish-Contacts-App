import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.Console;

/**
 * A subordinate class to the Main-class of the Finnish Contacts
 * Application.
 *
 * This class contains the necessary methods for adding a new contact to the
 * CSV-file.
 *
 * @author Joel Laitinen
 * @version 1.1
 */

public class AddContact {

    /**
     * Allows the user to write things.
     */
    private static Console c = System.console();

    /**
     * This method constructs an ArrayList consisting of the personal ID, first
     * and last names, phone number, and optionally the address and e-mail
     * submitted by the user. Each element in the ArrayList calls a separate
     * method, and at the end they are all written at the end of the CSV-file,
     * the contents of which are obtained from the DisplayContacts-class.
     */
    public static void addContact() {
        ArrayList<String> addStuff = new ArrayList<String>();

        System.out.println("");
        System.out.println("Please add in personal ID, first name, last name, phone number, address (optional) and e-mail (optional).");

        System.out.print("Personal ID: "); addStuff.add(personalID());
        System.out.print("First name: "); addStuff.add(name());
        System.out.print("Last name: "); addStuff.add(name());
        System.out.print("Phone number: "); addStuff.add(phoneNumber());
        System.out.print("Address (optional, press ENTER to skip): ");
        addStuff.add(address());
        System.out.print("E-mail (optional, press ENTER to skip): ");
        addStuff.add(email());

        String existingContacts = DisplayContacts.displayContactsForAdding();
        try {
            FileWriter addToFile = new FileWriter("Contacts.csv");
            addToFile.write(existingContacts);
            addToFile.write("\n");
            for (int i = 0; i < addStuff.size(); i++) {
                addToFile.write(addStuff.get(i));
                addToFile.write(",");
            }
            addToFile.close();
            System.out.println("");
            System.out.println("Contact added.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        addAnother();
    }

    /**
     * This method asks the user to provide an ID. It individually checks
     * through each character whether they correspond to the formula for
     * creating and ID. If something doesn't, the method asks the user to
     * provide another ID.
     *
     * @return the ID submitted by the user.
     * @see https://en.wikipedia.org/wiki/National_identification_number#Finland
     */
    public static String personalID() {
        String id = "";
        boolean idCheck = true;
        while (idCheck) {
            id = c.readLine();

            if (id.equals("")) {
                System.out.println("Must provide an ID, please submit again.");
                continue;
            }

            if (!(id.length() == 11)) {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(0) < '0' || id.charAt(0) > '3') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(0) == '3' && id.charAt(1) > '1') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(2) < '0' || id.charAt(2) > '1') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(2) == '1' && id.charAt(3) > '2') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(4) < '0' || id.charAt(4) > '9') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(5) < '0' || id.charAt(5) > '9') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (!(id.charAt(6) == '+' ||
                         id.charAt(6) == '-' ||
                         id.charAt(6) == 'Y' ||
                         id.charAt(6) == 'X' ||
                         id.charAt(6) == 'W' ||
                         id.charAt(6) == 'V' ||
                         id.charAt(6) == 'U' ||
                         id.charAt(6) == 'A' ||
                         id.charAt(6) == 'B' ||
                         id.charAt(6) == 'C' ||
                         id.charAt(6) == 'D' ||
                         id.charAt(6) == 'E' ||
                         id.charAt(6) == 'F')) {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(7) < '0' || id.charAt(7) > '9') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(8) < '0' || id.charAt(8) > '9') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(9) < '0' || id.charAt(9) > '9') {
                System.out.println("ID invalid, please submit again.");
                continue;
            } else if (id.charAt(7) == '0' && id.charAt(8) == '0' && id.charAt(9) < '2') {
                System.out.println("ID invalid, please submit again.");
                continue;
            }

            String DDMMYYZZZ = "";
            for (int i = 0; i < 6; i++) {
                DDMMYYZZZ = DDMMYYZZZ + id.charAt(i);
            }
            for (int i = 7; i < 10; i++) {
                DDMMYYZZZ = DDMMYYZZZ + id.charAt(i);
            }

            int intDDMMYYZZZ = Integer.parseInt(DDMMYYZZZ) % 31;
            char[] controlCharacter = {'0','1','2','3','4','5','6','7','8','9',
                                       'A','B','C','D','E','F','H','J','K','L',
                                       'M','N','P','R','S','T','U','V','W','X',
                                       'Y'};

            if (!(id.charAt(10) == controlCharacter[intDDMMYYZZZ])) {
                System.out.println("ID invalid, please submit again.");
                continue;
            }

            idCheck = false;
        }

        return id;
    }

    /**
     * This method asks the user to provide a name, first or last. The name
     * cannot contain numbers or special characters. If it does, the name is
     * not accepted and the method asks the user to provide another one.
     *
     * @return the name submitted by the user.
     */
    public static String name() {
        boolean nameCheck = true;
        String myName = "";
        String[] illegalCharacters = {"0","1","2","3","4","5","6","7","8","9","(",
                                      ")","`","~","!","@","#","$","%","^","&","*",
                                      "-","+","=","|","\\","{","}","[","]",":"," ",
                                      ";","\"","\'","<",">",",",".","?","/","_",};

        while (nameCheck) {
            boolean invalidName = false;
            myName = c.readLine();
            for (int i = 0; i < illegalCharacters.length; i++) {
                if (myName.contains(illegalCharacters[i])) {
                    System.out.println("Invalid name, please submit again.");
                    invalidName = true;
                    break;
                } else if (myName.equals("")) {
                    System.out.println("Must provide a name, please submit again.");
                    invalidName = true;
                    break;
                }
            }
            if (invalidName) continue;

            nameCheck = false;
        }

        return myName;
    }

    /**
     * This method asks the user to provide a phone number. The number must
     * correspond to the rules of Finnish mobile numbers. If it doesn't, the
     * program asks the user to provide another one.
     *
     * @return the phone number submitted by the user.
     * @see https://en.wikipedia.org/wiki/Telephone_numbers_in_Finland
     */
    public static String phoneNumber() {
        boolean mobileCheck = true;
        String mobileNumber = "";
        String[] illegalCharacters = {"(",")","`","~","!","@","#","$","%","^","&",
                                      "-","=","|","\\","{","}","[","]",":"," ",
                                      ";","\"","\'","<",">",",",".","?","/","_","*",
                                      "a","b","c","d","e","f","g","h","i","j","k",
                                      "l","m","n","o","p","q","r","s","t","u","v",
                                      "w","x","y","z","A","B","C","D","E","F","G",
                                      "H","I","J","K","L","M","N","O","P","Q","R",
                                      "S","T","U","V","W","X","Y","Z",};
        while (mobileCheck) {
            boolean invalidNumber = false;
            mobileNumber = c.readLine();

            if (mobileNumber.equals("")) {
                System.out.println("Must provide a number, please submit again.");
                continue;
            }

            if (mobileNumber.length() < 7 || mobileNumber.length() > 13) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (!(mobileNumber.startsWith("04") || mobileNumber.startsWith("05") || mobileNumber.startsWith("+358"))) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (mobileNumber.charAt(0) == 0 && mobileNumber.charAt(1) == 4 && mobileNumber.charAt(2) > 6) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (mobileNumber.charAt(0) == 0 && mobileNumber.charAt(1) == 5 && (!(mobileNumber.charAt(2) == 0))) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (mobileNumber.charAt(0) == '+' && mobileNumber.charAt(1) == 3 && mobileNumber.charAt(2) == 5 && mobileNumber.charAt(3) == 8 && (!(mobileNumber.charAt(4) == 4 || mobileNumber.charAt(4) == 5))) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (mobileNumber.charAt(0) == '+' && mobileNumber.charAt(1) == 3 && mobileNumber.charAt(2) == 5 && mobileNumber.charAt(3) == 8 && mobileNumber.charAt(4) == 4 && mobileNumber.charAt(5) > 6) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (mobileNumber.charAt(0) == '+' && mobileNumber.charAt(1) == 3 && mobileNumber.charAt(2) == 5 && mobileNumber.charAt(3) == 8 && mobileNumber.charAt(4) == 5 && (!(mobileNumber.charAt(5) == 0))) {
                System.out.println("Number invalid, please submit again.");
                continue;
            }

            for (int i = 0; i < illegalCharacters.length; i++) {
                if (mobileNumber.contains(illegalCharacters[i])) {
                    System.out.println("Number invalid, please submit again.");
                    invalidNumber = true;
                    break;
                }
            }
            if (invalidNumber) continue;

            mobileCheck = false;
        }

        return mobileNumber;
    }

    /**
     * This method asks the user to separately provide a street name, house
     * number, apartment number (optional), postal code, and city name. Each
     * element is added together into a String which the method then returns.
     * However, this method is completely optional as the program doesn't
     * require the user to provide an address if they don't want to, so this
     * method can be skipped by pressing ENTER at the start when it asks for
     * a street name.
     *
     * @return the address submitted by the user.
     */
    public static String address() {
        String fullAddress = "";
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k",
                            "l","m","n","o","p","q","r","s","t","u","v",
                            "w","x","y","z","å","ä","ö","A","B","C","D","E","F","G",
                            "H","I","J","K","L","M","N","O","P","Q","R",
                            "S","T","U","V","W","X","Y","Z","Å","Ä","Ö"};
        char[] lettersChar = {'a','b','c','d','e','f','g','h','i','j','k',
                              'l','m','n','o','p','q','r','s','t','u','v',
                              'w','x','y','z','å','ä','ö','A','B','C','D','E','F','G',
                              'H','I','J','K','L','M','N','O','P','Q','R',
                              'S','T','U','V','W','X','Y','Z','Å','Ä','Ö'};
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        char[] numbersChar = {'0','1','2','3','4','5','6','7','8','9'};
        String[] illegalCharacters = {"(",")","`","~","!","@","#","$","%","^","&",
                                      "-","+","=","|","\\","{","}","[","]",":",
                                      ";","\"","\'","<",">",",",".","?","/","_","*",};

        boolean streetNameCheck = true;
        System.out.print("\n The name of the street: ");
        while (streetNameCheck) {
            boolean invalidStreetName = false;
            String streetName = c.readLine();
            int matchingLetters = 0;

            if (streetName.equals("")) {
                return ("-");
            }

            if (streetName.length() < 3) {
                System.out.println("Street name too short, please submit again.");
                invalidStreetName = true;
            }
            if (invalidStreetName) continue;

            for (int i = 0; i < streetName.length(); i++) {
                for (int a = 0; a < lettersChar.length; a++) {
                    if (streetName.charAt(i) == lettersChar[a]) {
                        matchingLetters++;
                    }
                }
            }

            if (matchingLetters != streetName.length()) {
                System.out.println("Invalid street name, please submit again.");
                invalidStreetName = true;
            }

            if (invalidStreetName) continue;

            String correctedStreetName = "" + Character.toUpperCase(streetName.charAt(0));
            for (int i = 1; i < streetName.length(); i++) {
                correctedStreetName = correctedStreetName + Character.toLowerCase(streetName.charAt(i));
            }

            fullAddress = fullAddress + correctedStreetName;

            streetNameCheck = false;
        }

        boolean houseNumberCheck = true;
        System.out.print(" The number of the house: ");
        while (houseNumberCheck) {
            boolean invalidHouseNumber = false;
            String houseNumber = c.readLine();
            int matchingNumbers = 0;

            if (houseNumber.length() > 3) {
                System.out.println("House number is too long, please submit again.");
                invalidHouseNumber = true;
            }

            for (int i = 0; i < houseNumber.length(); i++) {
                for (int j = 0; j < numbersChar.length; j++) {
                    if (houseNumber.charAt(i) == numbersChar[j]) {
                        matchingNumbers++;
                    }
                }
            }

            if (matchingNumbers != houseNumber.length()) {
                System.out.println("House number must contain only numbers, please submit again.");
                invalidHouseNumber = true;
            }

            for (int j = 0; j < letters.length; j++) {
                if (houseNumber.contains(letters[j])) {
                    System.out.println("Invalid house number, please submit again.");
                    invalidHouseNumber = true;
                    break;
                }
            }

            for (int k = 0; k < illegalCharacters.length; k++) {
                if (houseNumber.contains(illegalCharacters[k])) {
                    System.out.println("Invalid house number, please submit again.");
                    invalidHouseNumber = true;
                    break;
                }
            }

            if (invalidHouseNumber) continue;

            fullAddress = fullAddress + " " + houseNumber;

            houseNumberCheck = false;
        }

        boolean apartmentNumberCheck = true;
        System.out.print(" The stair and number of the apartment (optional, press ENTER to skip): ");
        while (apartmentNumberCheck) {
            boolean invalidApartmentNumber = false;
            String apartmentNumber = c.readLine();
            int matchingNumbers = 0;

            if (apartmentNumber.equals("")) {
                apartmentNumberCheck = false;
                break;
            }

            if (apartmentNumber.length() < 2) {
                System.out.println("Invalid apartment number, please submit again.");
                invalidApartmentNumber = true;
            }
            if (invalidApartmentNumber) continue;

            boolean isLetter = false;
            for (int i = 0; i < lettersChar.length; i++) {
                if (apartmentNumber.charAt(0) == lettersChar[i]) {
                    isLetter = true;
                }

                for (int j = 1; j < apartmentNumber.length(); j++) {
                    if (apartmentNumber.charAt(j) == lettersChar[i]) {
                        System.out.println("Invalid apartment number, please submit again.");
                        invalidApartmentNumber = true;
                        break;
                    }
                }
                if (invalidApartmentNumber) break;
            }

            if (isLetter == false) {
                System.out.println("Invalid stair letter, please submit again.");
                invalidApartmentNumber = true;
            }

            if (!(apartmentNumber.charAt(1) == ' ')) {
                String modifiedApartmentNumber = "";
                char[] modifiedApartmentNumberArray = new char[apartmentNumber.length() + 1];
                modifiedApartmentNumberArray[0] = apartmentNumber.charAt(0);
                modifiedApartmentNumberArray[1] = ' ';
                for (int i = 1; i < apartmentNumber.length(); i++) {
                    modifiedApartmentNumberArray[i + 1] = apartmentNumber.charAt(i);
                }
                for (int i = 0; i < modifiedApartmentNumberArray.length; i++) {
                    modifiedApartmentNumber = modifiedApartmentNumber + modifiedApartmentNumberArray[i];
                }
                apartmentNumber = modifiedApartmentNumber;
            }

            for (int i = 2; i < apartmentNumber.length(); i++) {
                for (int j = 0; j < numbersChar.length; j++) {
                    if (apartmentNumber.charAt(i) == numbersChar[j]) {
                        matchingNumbers++;
                    }
                }
            }
            if (matchingNumbers != apartmentNumber.length() - 2) {
                System.out.println("Invalid apartment number, please submit again.");
                invalidApartmentNumber = true;
            }

            if (invalidApartmentNumber) continue;

            fullAddress = fullAddress + " " + apartmentNumber;

            apartmentNumberCheck = false;
        }

        boolean postalCodeCheck = true;
        System.out.print(" Postal code: ");
        while (postalCodeCheck) {
            boolean invalidPostalCode = false;
            String postalCode = c.readLine();
            int matchingNumbers = 0;

            if (postalCode.length() != 5) {
                System.out.println("Postal code must be five numbers long, please submit again.");
                invalidPostalCode = true;
            }

            for (int i = 0; i < postalCode.length(); i++) {
                for (int j = 0; j < numbersChar.length; j++) {
                    if (postalCode.charAt(i) == numbersChar[j]) {
                        matchingNumbers++;
                    }
                }
            }
            if (matchingNumbers != postalCode.length()) {
                System.out.println("Postal code must contain only numbers, please submit again.");
                invalidPostalCode = true;
            }

            if (invalidPostalCode) continue;

            fullAddress = fullAddress + " " + postalCode;

            postalCodeCheck = false;
        }

        boolean cityNameCheck = true;
        System.out.print(" City name: ");
        while (cityNameCheck) {
            boolean invalidCityName = false;
            String cityName = c.readLine();
            int matchingLetters = 0;

            for (int i = 0; i < cityName.length(); i++) {
                for (int a = 0; a < lettersChar.length; a++) {
                    if (cityName.charAt(i) == lettersChar[a]) {
                        matchingLetters++;
                    }
                }
            }
            if (matchingLetters != cityName.length()) {
                System.out.println("Invalid city name, please submit again.");
                invalidCityName = true;
            }

            if (invalidCityName) continue;

            String correctedCityName = "" + Character.toUpperCase(cityName.charAt(0));
            for (int i = 1; i < cityName.length(); i++) {
                correctedCityName = correctedCityName + Character.toLowerCase(cityName.charAt(i));
            }

            fullAddress = fullAddress + " " + correctedCityName;

            cityNameCheck = false;
        }

        return fullAddress;
    }

    /**
     * This method asks the user for an e-mail address. However, this method is
     * completely optional, as the program doesn't require the user to provide
     * an e-mail address if they don't want to, so it is possible to skip this
     * method by pressing ENTER.
     *
     * @return the e-mail submitted by the user.
     */
    public static String email() {
        boolean emailCheck = true;
        String emailAddress = "";

        while (emailCheck) {
            emailAddress = c.readLine();

            if (emailAddress.equals("")) {
                emailAddress = "-";
                break;
            }

            if (!(emailAddress.contains("@") && emailAddress.contains("."))) {
                System.out.println("Number invalid, please submit again.");
                continue;
            } else if (emailAddress.contains(",")) {
                System.out.println("Number invalid, please submit again.");
                continue;
            }

            emailCheck = false;
        }

        return emailAddress;
    }

    /**
     * This method asks the user if they'd like to immediately add another
     * contact by pressing 1, which loops back to the addContact()-method, or
     * if they'd like to do something else by pressing 2, which calls the
     * pickOption()-method in the primary Main-class.
     */
    public static void addAnother() {
        System.out.println("Would you like to add another contact? Pick the corresponding number.");
        System.out.println("1. Yes.");
        System.out.println("2. No.");

        int yesOrNo = 0;
        boolean check = true;

        while (check) {
            try {
                yesOrNo = Integer.parseInt(c.readLine());
                while (!(yesOrNo == 1 || yesOrNo == 2)) {
                    System.out.println("Please choose 1 or 2.");
                    yesOrNo = Integer.parseInt(c.readLine());
                }
                check = false;
            } catch (NumberFormatException e) {
                System.out.println("Please choose 1 or 2.");
            }
        }

        if (yesOrNo == 1) {
            addContact();
        } else if (yesOrNo == 2) {
            Main.anythingElse();
        }
    }
}
