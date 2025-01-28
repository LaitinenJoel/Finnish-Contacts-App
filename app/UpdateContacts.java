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
 * This class contains the necessary methods for editing an existing contact
 * in the CSV-file.
 *
 * @author Joel Laitinen
 * @version 1.1
 */

public class UpdateContacts {

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
	 * The line which the user chooses to edit.
	 */
	private static int chooseWhichLine = 0;

	/**
	 * This method asks the user which contact they'd like to edit. The contacts
	 * are displayed to the user by calling the displayContactsForUser()-method
	 * in the DisplayContacts-class, and the number corresponding to the desired
	 * contact must be pressed. The method interprets this as a specific row in
	 * the CSV-file, and then it calls the pickElement()-method to decide which
	 * element the user would like to edit.
	 */
	public static void alterContact() {
        reloadContacts();
		System.out.println("Pick which contact you'd like to edit by inputting the corresponding number.");

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println(e);
		}

        String displayingContacts = DisplayContacts.displayContactsForUser("updating");
		System.out.println(displayingContacts);
		System.out.println("");
		if (displayingContacts.trim().equals("There are no contacts available for updating.")) {
            return;
		}

        boolean contactCheck = true;

		while (contactCheck) {
            try {
                chooseWhichLine = Integer.parseInt(c.readLine());
                while (chooseWhichLine >= allLines.size() || chooseWhichLine <= 0) {
                    System.out.println("Please choose a number corresponding to one of the contacts.");
                    chooseWhichLine = Integer.parseInt(c.readLine());
                }
                contactCheck = false;
            } catch (NumberFormatException e) {
                System.out.println("Please choose a number corresponding to one of the contacts.");
            }
		}
		System.out.println("");

		pickElement();
	}

	/**
	 * This method asks the user which element they'd like to edit in the contact
	 * they've chosen in the alterContact()-method by pressing the corresponding
	 * number. It goes through all the characters, adding them to a char[], until
	 * the program encounters ','. Once a comma is encountered, it checks whether
	 * the amount of commas corresponds to the picked element number. If not, the
	 * program adds all the existing characters into a String and empties the
	 * char[], then continues going through the characters; if yes, it discards
	 * the contents of the char[] and allows the user to type a new String which
	 * it adds to the current String. It then goes through the rest of the
	 * characters and adds them to the String. The String then replaces old line
	 * in the allLines List<String> that reads the contents of the CSV-file, and
	 * the contents of allLines (including the altered line) are then written
	 * onto the CSV-file, overwriting the old information.
	 */
	public static void pickElement() {
		System.out.println("Pick which element you'd like to edit by inputting the corresponding number.");
		System.out.println("1. Personal ID.");
		System.out.println("2. First name.");
		System.out.println("3. Last name.");
		System.out.println("4. Phone number.");
		System.out.println("5. Address.");
		System.out.println("6. E-mail.");
		System.out.println("");

		int elementToChange = 0;
		boolean indexCheck = true;

		while (indexCheck) {
			try {
				elementToChange = Integer.parseInt(c.readLine());
				while (!(elementToChange == 1 || elementToChange == 2 || elementToChange == 3 || elementToChange == 4 || elementToChange == 5 || elementToChange == 6)) {
					System.out.println("Please choose a whole number between 1 and 6.");
					elementToChange = Integer.parseInt(c.readLine());
				}
				indexCheck = false;
			} catch (NumberFormatException e) {
				System.out.println("Please choose a whole number between 1 and 6.");
			}
		}
		System.out.println("");

		char[] elementContainer = new char[allLines.get(chooseWhichLine).length()];
		int commaCounter = 0;
		String alteredLine = "";
		boolean addRest = false;

		for (int i = 0, j = 0; i < allLines.get(chooseWhichLine).length() && j < allLines.get(chooseWhichLine).length(); i = i + 1, j = j + 1) {
			elementContainer[j] = allLines.get(chooseWhichLine).charAt(i);

			if (addRest) {
				alteredLine = alteredLine + allLines.get(chooseWhichLine).charAt(i);
			}

			if(allLines.get(chooseWhichLine).charAt(i) == ',') {
				commaCounter++;

				if (commaCounter == elementToChange) {
					System.out.println("Write the altered text:");
					String alteredElement = "";
					switch (elementToChange) {
						case 1: alteredElement = AddContact.personalID(); break;
						case 2: alteredElement = AddContact.name(); break;
						case 3: alteredElement = AddContact.name(); break;
						case 4: alteredElement = AddContact.phoneNumber(); break;
						case 5: alteredElement = AddContact.address(); break;
						case 6: alteredElement = AddContact.email(); break;
					}
					alteredElement = alteredElement + ",";
					alteredLine = alteredLine + alteredElement;
					addRest = true;
					if (commaCounter == 6) break;
				} else if (commaCounter < elementToChange) {
					for (int k = 0; !(elementContainer[k] == ','); k++) {
						alteredLine = alteredLine + elementContainer[k];
					}
					alteredLine = alteredLine + ",";

					j = -1;
				}
			}
		}

		allLines.set(chooseWhichLine, alteredLine);

		try {
			FileWriter updateFile = new FileWriter("Contacts.csv");
			for (int i = 1; i < allLines.size(); i++) {
				updateFile.write("\n");
				updateFile.write(allLines.get(i));
			}
			updateFile.close();
			System.out.println("");
			System.out.println("Contact successfully updated.");
			System.out.println("");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		editMore();
	}

	/**
	 * This method asks the user if they'd like to edit another element in the
	 * same contact by pressing 1, which loops back to the pickElement()-method,
	 * or not by pressing 2, which calls the anythingElse()-method in the
	 * primary Main-class.
	 */
	public static void editMore() {
		System.out.println("Would you like to edit some other element about this particular contact? Pick the corresponding number.");
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
			pickElement();
		} else if (yesOrNo == 2) {
			Main.anythingElse();
		}
	}
}