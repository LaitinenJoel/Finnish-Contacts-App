# Finnish Contacts Application
This is a program that allows you to add to, edit, display and delete Finnish contacts from a CSV-file. Emphasis on Finnish; functionality with contacts of other nationalities has not been intentionally implemented. It has a command line interface, not a graphical interface.

## Overview
This started as a school project, but I have afterward, on my own, modified and expanded it. This is partly out of interest to revisit an old project, but also to put it on public display in the search for a job in the field of programming.

## Build status
This is version 1.1. A major bug has been removed that was present in the 1.0 release version.

## Code style
This program has been coded entirely with Java.

## Code example
As an example of what the code looks like, this is the method used to delete contacts:
```
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
```

## Screenshots
Here are some ideas to give you an idea of what the program looks like when used:
![image](https://github.com/user-attachments/assets/fcf1ae82-60f5-4841-95d3-af6df7b1d9d4)

![image](https://github.com/user-attachments/assets/3fbb1137-5717-46aa-998d-5bc13361a612)

## Installation and usage
1. Make sure you have Java installed on your computer. You can download it from [https://adoptium.net/en-GB/](https://adoptium.net/en-GB/).
2. Download the `app` folder using [https://download-directory.github.io/](https://download-directory.github.io/) or download the files inside the folder manually. You can insert them anywhere on your computer, but make sure the file structure remains intact.
3. Open the Windows terminal in the directory location you placed the `app` folder in. Any application capable of simulating the terminal should work as well.
4. Type `javac *.java` in the terminal to compile the files.
5. Type `java Main` to run the program.
6. In order for the program to work, you must have a file named `Contacts.csv` in the same `app` directory. If you are starting the project for the first time, make sure the file is empty.
