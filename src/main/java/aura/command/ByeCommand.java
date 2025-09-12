package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to exit the application.
 */
public class ByeCommand extends Command {
    /**
     * Constructs a ByeCommand.
     *
     * @param input The user input string, which is typically "bye".
     */
    public ByeCommand(String input) {
        super(input);
    }

    /**
     * Executes the exit command, returning a goodbye message.
     *
     * @param taskList The list of tasks (not used in this command).
     * @param storage The storage handler (not used in this command).
     * @param ui The user interface to get the exit message from.
     * @return The farewell message to be displayed to the user.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        return ui.exitMessage();
    }
}
