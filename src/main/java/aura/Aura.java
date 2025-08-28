package aura;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

import java.io.IOException;
import java.util.List;

/**
 * The main class for the Aura application.
 * This class initializes the application and manages the main run loop.
 */
public class Aura {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * The main entry point of the Aura application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Aura("./data/Aura.txt").run();
    }

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
        this.ui.greeting();
        String input;
        boolean flag = true;
        boolean isUpdated;
        while (flag) {
            input = this.ui.getInput();

            switch (input.toLowerCase()) {
                case "bye" -> {
                    ui.exitMessage();
                    flag = false;
                }
                case "list" -> this.tasks.printList();
                default -> {
                    if (input.toLowerCase().startsWith("mark ")) {
                        isUpdated = this.tasks.markTask(input);
                    } else if (input.toLowerCase().startsWith("unmark ")) {
                        isUpdated = this.tasks.unMarkTask(input);
                    } else if (input.toLowerCase().startsWith("todo ")) {
                        isUpdated = this.tasks.addToDo(input);
                    } else if (input.toLowerCase().startsWith("deadline ")) {
                        isUpdated = this.tasks.addDeadline(input);
                    } else if (input.toLowerCase().startsWith("event ")) {
                        isUpdated = this.tasks.addEvent(input);
                    } else if (input.toLowerCase().startsWith("delete ")) {
                        isUpdated = this.tasks.deleteTask(input);
                    } else {
                        String error;
                        List<String> commands = List.of("mark", "unmark", "todo", "deadline", "event", "delete");
                        if (commands.contains(input)) {
                            error = String.format("ERROR SIR!! The description of the command %s cannot be empty.", input);
                        } else {
                            error = "Your input was invalid, make sure to include a valid command";
                        }
                        ui.printDivider();
                        ui.replyPrint(error);
                        ui.printDivider();
                        isUpdated = false;
                    }

                    if (isUpdated) {
                        ui.replyPrint(this.tasks.saveFile(storage));
                    }
                }
            }
        }
    }
}
