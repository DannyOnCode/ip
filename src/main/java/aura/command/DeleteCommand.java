package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to delete a task from the list.
 */
public class DeleteCommand extends Command {
    /**
     * Constructs a DeleteCommand.
     *
     * @param input The user input string, typically the index of the task to delete.
     */
    public DeleteCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to delete a task from the list.
     *
     * @param tasks The TaskList to delete the task from.
     * @param storage The storage handler to save the updated list.
     * @param ui The user interface for displaying messages.
     * @return A confirmation message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.deleteTask(super.getInput());
    }
}
