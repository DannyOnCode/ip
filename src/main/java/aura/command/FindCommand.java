package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to find tasks by keyword.
 */
public class FindCommand extends Command {
    /**
     * Constructs a FindCommand.
     *
     * @param input The keyword to search for within the task descriptions.
     */
    public FindCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to find and list tasks containing the specified keyword.
     *
     * @param tasks The TaskList to search within.
     * @param storage The storage handler (not used in this command).
     * @param ui The user interface for displaying messages.
     * @return A string containing the list of matching tasks.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.getTasksWithKeyword(super.getInput());
    }
}
