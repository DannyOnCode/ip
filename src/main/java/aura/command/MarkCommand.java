package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to mark a task as done.
 */
public class MarkCommand extends Command {
    /**
     * Constructs a MarkCommand.
     *
     * @param input The user input string, typically the index of the task to mark.
     */
    public MarkCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to mark a specified task as complete.
     *
     * @param tasks The TaskList containing the task to be marked.
     * @param storage The storage handler to save the updated list.
     * @param ui The user interface for displaying messages.
     * @return A confirmation message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.markTask(super.getInput());
    }
}
