package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to list all tasks.
 */
public class ListCommand extends Command {
    /**
     * Constructs a ListCommand.
     *
     * @param input The user input string, which is typically "list".
     */
    public ListCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to display all tasks in the list.
     *
     * @param tasks The TaskList to be displayed.
     * @param storage The storage handler (not used in this command).
     * @param ui The user interface for displaying messages.
     * @return A string containing the formatted list of all tasks.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.printList();
    }
}
