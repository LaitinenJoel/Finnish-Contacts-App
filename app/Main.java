import java.io.Console;
import java.io.IOException;

/**
 * This program allows the user to manage Finnish contacts.
 *
 * This program is divided into five classes, each containing the necessary
 * code for the features of displaying, creating, editing, and deleting
 * Finnish contacts using a CSV-file as a database for storing and managing the
 * information, as well as the ability to navigate between said features.
 *
 * @author Joel Laitinen
 * @version 1.1
 */

public class Main {

    /**
     * This is the main method for the whole application, though it really only
     * functions as an introduction to the user, after which it immediately
     * calls another method.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("HELLO AND WELCOME TO THE FINNISH CONTACTS APPLICATION. PLEASE SELECT WHAT YOU'D LIKE TO DO.");

        pickOption();
    }

    /**
     * This method makes the user choose between five different options: to
     * display, add, alter, or delete a contact, or to close the application
     * altogether. Each option calls a new method in a separate class
     * corresponding to the desired action. After that it calls another method
     * asking the user if they'd like to do anything else or to close the app.
     */
    public static void pickOption() {
        Console c = System.console();

        System.out.println("");
        System.out.println("Input the right number:");
        System.out.println("1. I'd like to display all contacts.");
        System.out.println("2. I'd like to add a new contact to the database.");
        System.out.println("3. I'd like to alter a contact.");
        System.out.println("4. I'd like to delete a contact.");
        System.out.println("5. I'd like to close the application.");

        int pickAnOption = 0;
        boolean check = true;

        while (check) {
            try {
                pickAnOption = Integer.parseInt(c.readLine());
                while (!(pickAnOption == 1 || pickAnOption == 2 || pickAnOption == 3 || pickAnOption == 4 || pickAnOption == 5)) {
                    System.out.println("Please choose a whole number between 1 and 5.");
                    pickAnOption = Integer.parseInt(c.readLine());
                }
                check = false;
            } catch (NumberFormatException e) {
                System.out.println("Please choose a whole number between 1 and 5.");
            }
        }

        if (pickAnOption == 1) {
            System.out.println(DisplayContacts.displayContactsForUser("display"));
            System.out.println("");
            anythingElse();
        } else if (pickAnOption == 2) {
            AddContact.addContact();
            System.out.println("");
            anythingElse();
        } else if (pickAnOption == 3) {
            UpdateContacts.alterContact();
            System.out.println("");
            anythingElse();
        } else if (pickAnOption == 4) {
            try {
                DeleteContacts.deleteContact();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("");
            anythingElse();
        } else if (pickAnOption == 5) {
            System.out.println("Closing application.");
            System.exit(0);
        }
    }

    /**
     * This method asks the user if they'd like to perform another action
     * with the program by pressing 1, which loops back to the pickAnother()
     * method, or to close the program by pressing 2.
     */
    public static void anythingElse() {
        Console c = System.console();

        System.out.println("");
        System.out.println("Would you like to do anything else? Pick the corresponding number.");
        System.out.println("1. Yes.");
        System.out.println("2. No, I would like to close the application.");

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
            pickOption();
        } else if (yesOrNo == 2) {
            System.out.println("Closing application.");
            System.exit(0);
        }
    }
}
