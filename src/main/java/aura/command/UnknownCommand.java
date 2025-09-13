package aura.command;

import java.util.List;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents a command that is not recognized or is invalid.
 */
public class UnknownCommand extends Command {
    /**
     * Constructs an UnknownCommand.
     *
     * @param input The invalid user input string.
     */
    public UnknownCommand(String input) {
        super(input);
    }

    /**
     * Executes the unknown command by returning an appropriate error message.
     *
     * @param tasks The list of tasks (not used in this command).
     * @param storage The storage handler (not used in this command).
     * @param ui The user interface (not used in this command).
     * @return A string containing an error message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        String error;
        List<String> commands = List.of("mark", "unmark", "todo", "deadline", "event", "delete");
        if (commands.contains(super.getInput())) {
            error = String.format("ERROR: The description of the command %s cannot be empty.",
                    super.getInput());
        } else {
            error = "ERROR: Your input was invalid, make sure to include a valid command";
        }
        return error;
    }
}
