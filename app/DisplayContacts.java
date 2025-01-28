import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/**
 * A subordinate class to the Main-class of the Finnish Contacts Application.
 *
 * This class contains the necessary methods for displaying the contacts to the
 * user when requested.
 *
 * @author Joel Laitinen
 * @version 1.1
 */

public class DisplayContacts {

    /**
     * This is the method that displays the contacts in a way that the user is
     * meant to see, that is pleasing to the eye and easily legible. It reads
     * the CSV-file named "Contacts.csv" if it is found in the same directory
     * and puts the contents into a String which it returns when called.
     *
     * @return all contacts saved into the database in an easily legible form.
     * @param purpose a String which is used when there are no contacts
     * available for a purpose that is defined when the method is called.
     */
    public static String displayContactsForUser(String purpose) {
        String contactsAsString = "";

        try {
            File contactsList = new File("Contacts.csv");
            Scanner readContacts = new Scanner(contactsList);

            readContacts.useDelimiter(",");

            int personNumber = 0;

            if (!(readContacts.hasNext())) {
                return ("\nThere are no contacts available for " + purpose + ".");
            }

            while (readContacts.hasNext()) {
                for (int i = 1; i < 7; i++) {
                    if (i == 1) {
                        personNumber++;
                        contactsAsString = contactsAsString + "\n" + "PERSON " + personNumber + ":";
                        String firstElementNoLineBreak = readContacts.next();
                        firstElementNoLineBreak = firstElementNoLineBreak.replace("\n", "").replace("\r", "");
                        contactsAsString = contactsAsString + "\n Personal ID: " + firstElementNoLineBreak;
                    } else if (i == 2) {
                        contactsAsString = contactsAsString + "\n First name: " + readContacts.next();
                    } else if (i == 3) {
                        contactsAsString = contactsAsString + "\n Last name: " + readContacts.next();
                    } else if (i == 4) {
                        contactsAsString = contactsAsString + "\n Phone number: " + readContacts.next();
                    } else if (i == 5) {
                        contactsAsString = contactsAsString + "\n Address: " + readContacts.next();
                    } else if (i == 6) {
                        contactsAsString = contactsAsString + "\n E-mail: " + readContacts.next();
                    }
                }
                contactsAsString = contactsAsString + "\n";
            }

            readContacts.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file in question cannot be found");
            e.printStackTrace();
        }

        return contactsAsString;
    }

    /**
     * This method displays the contacts, but it is not ever seen by the user.
     * Instead, this method is used by the AddContact-class when it rewrites
     * the contents of the file. It constructs a String which is identical to
     * the way the contents of the CSV-file are arranged in the file itself.
     * This is so the AddContact-class can directly and easily add another row
     * to the end.
     *
     * @return all contacts identically to how they are written in the file.
     */
    public static String displayContactsForAdding() {
        String contactsAsString = "";

        try {
            File contactsList = new File("Contacts.csv");
            Scanner readContacts = new Scanner(contactsList);

            readContacts.useDelimiter(" ,  ");

            while (readContacts.hasNext()) {
                contactsAsString = contactsAsString + readContacts.next();
            }

            readContacts.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file in question cannot be found");
            e.printStackTrace();
        }

        return contactsAsString;
    }
}
