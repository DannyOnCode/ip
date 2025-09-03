package aura.io;

import java.util.List;

import aura.task.Task;

/**
 * Handles all user interface interactions.
 * This class is responsible for displaying messages to the user and reading user input.
 */
public class Ui {
    private static final String ASSISTANCE_NAME = "Aura";
    private static final String LOGO = """
                _
               / \\  _   _ _ __ __ _
              / _ \\| | | | '__/ _` |
             / ___ \\ |_| | | | (_| |
            /_/   \\_\\__,_|_|  \\__,_|
            """;
    private final Parser parser;

    /**
     * Constructs a Ui object.
     * Initializes the parser for reading user input.
     */
    public Ui() {
        this.parser = new Parser();
    }

    /**
     * Displays the welcome message to the user.
     */
    public void greeting() {
        System.out.println("____________________________________________________________");
        System.out.println(this.LOGO
                + "Hello! I'm " + this.ASSISTANCE_NAME + "\n"
                + "What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays the exit message to the user.
     */
    public void exitMessage() {
        replyPrint("Bye Bye my friend");
        printDivider();
    }

    /**
     * Prints a divider line to the console for better readability.
     */
    public void printDivider() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Prints a formatted reply to the user.
     *
     * @param text The text to be printed.
     */
    public void replyPrint(String text) {
        System.out.println("\t" + text);
    }

    /**
     * Displays an error message when loading from the save file fails.
     */
    public void showLoadingError() {
        replyPrint("There seems to have been an error in loading the file");
    }

    /**
     * Reads and returns the user's input from the console.
     *
     * @return The user's input as a String.
     */
    public String getInput() {
        return this.parser.getInput();
    }

    /**
     * Displays a given list of tasks to the user in a numbered format.
     *
     * @param taskList The list of tasks to be displayed.
     */
    public void displayGivenList(List<Task> taskList) {
        printDivider();
        for (int i = 0; i < taskList.size(); i++) {
            replyPrint(String.format("%d. %s", (i + 1), taskList.get(i)));
        }
        printDivider();
    }
}
