package aura;

import java.io.IOException;
import java.util.List;

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
     * Starts the main run loop of the application.
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
        switch (input.toLowerCase()) {
            case "bye" -> {
                returnText = ui.exitMessage();
            }
            case "list" -> {
                returnText = this.tasks.printList();
            }
            default -> {
                if (input.toLowerCase().startsWith("mark ")) {
                    returnText = this.tasks.markTask(input);
                } else if (input.toLowerCase().startsWith("unmark ")) {
                    returnText = this.tasks.unMarkTask(input);
                } else if (input.toLowerCase().startsWith("todo ")) {
                    returnText = this.tasks.addToDo(input);
                } else if (input.toLowerCase().startsWith("deadline ")) {
                    returnText = this.tasks.addDeadline(input);
                } else if (input.toLowerCase().startsWith("event ")) {
                    returnText = this.tasks.addEvent(input);
                } else if (input.toLowerCase().startsWith("delete ")) {
                    returnText = this.tasks.deleteTask(input);
                } else if (input.toLowerCase().startsWith("find ")) {
                    returnText = this.tasks.getTasksWithKeyword(input);
                } else {
                    String error;
                    List<String> commands = List.of("mark", "unmark", "todo", "deadline", "event", "delete");
                    if (commands.contains(input)) {
                        error = String.format("ERROR SIR!! The description of the command %s cannot be empty.",
                                input);
                    } else {
                        error = "ERROR: Your input was invalid, make sure to include a valid command";
                    }
                    returnText = error;
                }

                if (!returnText.toLowerCase().contains("error")) {
                    returnText += '\n' + this.tasks.saveFile(storage);
                }
            }
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
