package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to add a new todo task.
 */
public class TodoCommand extends Command {
    /**
     * Constructs a TodoCommand.
     *
     * @param input The user input string containing the todo's description.
     */
    public TodoCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to add a todo task to the list.
     *
     * @param tasks The TaskList to add the new todo to.
     * @param storage The storage handler to save the updated list.
     * @param ui The user interface for displaying messages.
     * @return A confirmation message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.addToDo(super.getInput());
    }
}
