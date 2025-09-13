package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents the command to add a new event task.
 */
public class EventCommand extends Command {
    /**
     * Constructs an EventCommand.
     *
     * @param input The user input string containing the event's description, start time, and end time.
     */
    public EventCommand(String input) {
        super(input);
    }

    /**
     * Executes the command to add an event task to the list.
     *
     * @param tasks The TaskList to add the new event to.
     * @param storage The storage handler to save the updated list.
     * @param ui The user interface for displaying messages.
     * @return A confirmation message.
     */
    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        return tasks.addEvent(super.getInput());
    }
}
