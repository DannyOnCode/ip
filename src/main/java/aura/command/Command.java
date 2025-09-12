package aura.command;

import aura.io.Ui;
import aura.storage.Storage;
import aura.task.TaskList;

/**
 * Represents an abstract command. All specific command classes inherit from this class.
 */
public abstract class Command {
    private final String input;

    /**
     * Constructs a new command with the given user input.
     *
     * @param input The user-provided input string for the command.
     */
    public Command(String input) {
        this.input = input;
    }

    /**
     * Gets the input string associated with this command.
     *
     * @return The raw input string.
     */
    public String getInput() {
        return this.input;
    }

    /**
     * Executes the command. This method is intended to be overridden by subclasses.
     *
     * @param taskList The TaskList to be modified by the command.
     * @param storage The Storage to save changes to.
     * @param ui The Ui to interact with the user.
     * @return A string representing the result of the command execution.
     */
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        return "Unused Command";
    };
}
