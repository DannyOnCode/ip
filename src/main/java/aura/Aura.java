package aura;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

import java.io.IOException;
import java.util.List;

public class Aura {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public static void main(String[] args) {
        new Aura("./data/Aura.txt").run();
    }

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

    public void run() {
        this.ui.greeting();
        String input;
        boolean isRunning = true;
        boolean isUpdated;
        while (isRunning) {
            input = this.ui.getInput();

            switch (input.toLowerCase()) {
                case "bye" -> {
                    ui.exitMessage();
                    isRunning = false;
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
