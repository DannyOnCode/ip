package aura;

import java.io.IOException;

import aura.command.Command;
import aura.io.Parser;
import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * The main class for the Aura application.
 * This class initializes the application and manages the main run loop.
 */
public class Aura {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs an Aura object.
     * Initializes the UI, storage, and task list components.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Aura(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList();
        try {
            this.tasks.loadTask(storage);
        } catch (IOException e) {
            ui.showLoadingError();
        }
    }

    /**
     * Starts the main run loop of the application when used in CLI.
     * It processes user commands until the "bye" command is entered.
     */
    public void run() {
        System.out.println(ui.greeting());
        String input;
        boolean isRunning = true;
        while (isRunning) {
            input = this.ui.getInput();
            if (input.equalsIgnoreCase("bye")) {
                ui.replyPrint(ui.exitMessage());
                isRunning = false;
            }
            ui.replyPrint(handleUserCommand(input));
        }
    }

    /**
     * Parses user input, executes the corresponding command, and returns a response string.
     * Saves tasks to storage on modification.
     *
     * @param input The full command string from the user.
     * @return A string containing the result of the command, e.g., a confirmation or an error.
     */
    public String handleUserCommand(String input) {
        String returnText = "";
        Command command = Parser.parseInput(input);
        returnText = command.execute(tasks, storage, ui);

        if (!returnText.toLowerCase().contains("error")) {
            returnText += '\n' + this.tasks.saveFile(storage);
        }

        return returnText;
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return handleUserCommand(input);
    }

    /**
     * The main entry point of the Aura application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Aura("./data/Aura.txt").run();
    }
}
