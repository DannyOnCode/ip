package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to add a new deadline task.
 */
public class DeadlineCommand extends Command {
    /**
     * Constructs a DeadlineCommand.
     *
     * @param input The user input string containing the deadline's description and due date.
     */
    public DeadlineCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to add a deadline task to the list.
     *
     * @param tasks The TaskList to add the new deadline to.
     * @param storage The storage handler to save the updated list.
     * @param ui The user interface for displaying messages.
     * @return A confirmation message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.addDeadline(super.getInput());
    }
}
