import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.io.File;
import java.io.Console;
import java.io.FileWriter;

/**
 * A subordinate class to the Main-class of the Finnish Contacts Application.
 *
 * This class contains the necessary methods for deleting a contact in the
 * CSV-file.
 *
 * @author Joel Laitinen
 * @version 1.1
 */

public class DeleteContacts {

    /**
     * Allows the user to write things.
     */
    private static Console c = System.console();

    /**
	 * A list of all the lines in the file "Contacts.csv."
	 */
	private static List<String> allLines;
	static {
		List<String> exceptionCheck = null;
		try {
			exceptionCheck = Files.readAllLines(new File("Contacts.csv").toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		allLines = exceptionCheck;
	}

    /**
     * This method reloads the allLines contact list so that the program is
     * always working with the latest file information.
     */
    private static void reloadContacts() {
		try {
			allLines = Files.readAllLines(new File("Contacts.csv").toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * This method asks which contact the user would like to delete. The
     * contacts are displayed to the user by calling the
     * displayContactsForUser()-method in the DisplayContacts-class, and the
     * number corresponding to the desired contact must be pressed. The method
     * interprets this as a specific row in the CSV-file, and then removes it.
     * It then rewrites the file without the deleted line.
     */
    public static void deleteContact() throws IOException {
        reloadContacts();

        int chooseLineToDelete = 0;

        System.out.println("Pick which contact you'd like to delete by inputting the corresponding number.");

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e);
        }

        String displayingContacts = DisplayContacts.displayContactsForUser("deletion");
        System.out.println(displayingContacts);
        System.out.println("");
        if (displayingContacts.trim().equals("There are no contacts available for deletion.")) {
            return;
        }

        boolean deleteCheck = true;

        while (deleteCheck) {
            try {
                chooseLineToDelete = Integer.parseInt(c.readLine());
                while (chooseLineToDelete >= allLines.size() || chooseLineToDelete <= 0) {
                    System.out.println("Please choose a number corresponding to one of the contacts.");
                    chooseLineToDelete = Integer.parseInt(c.readLine());
                }
                deleteCheck = false;
            } catch (NumberFormatException e) {
                System.out.println("Please choose a number corresponding to one of the contacts.");
            }
        }
        System.out.println("");

        allLines.remove(chooseLineToDelete);

        try {
            FileWriter updateFile = new FileWriter("Contacts.csv");
            for (int i = 1; i < allLines.size(); i++) {
                updateFile.write("\n");
                updateFile.write(allLines.get(i));
            }
            updateFile.close();
            System.out.println("");
            System.out.println("Contact successfully deleted.");
            System.out.println("");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        reloadContacts();

        deleteMore();
    }

    /**
     * This method asks the user if they'd like to immediately delete another
     * contact by pressing 1, which loops back to the deleteContact()-method,
     * or not by pressing 2, which calls the anythingElse()-method in the
     * primary Main-class.
     */
    public static void deleteMore() {
        System.out.println("Would you like to delete another contact? Pick the corresponding number.");
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
            try {
                deleteContact();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (yesOrNo == 2) {
            Main.anythingElse();
        }
    }
}