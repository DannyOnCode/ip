package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    /**
     * Constructs an UnmarkCommand.
     *
     * @param input The user input string, typically the index of the task to unmark.
     */
    public UnmarkCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to mark a specified task as not complete.
     *
     * @param tasks The TaskList containing the task to be unmarked.
     * @param storage The storage handler to save the updated list.
     * @param ui The user interface for displaying messages.
     * @return A confirmation message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.unMarkTask(super.getInput());
    }
}
