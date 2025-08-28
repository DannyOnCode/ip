package aura.task;

/**
 * Represents a to-do task. A `ToDos` object corresponds to a task without any date/time attached.
 */
public class ToDos extends Task {
    /**
     * Constructs a new `ToDos` task.
     *
     * @param description The description of the to-do task.
     */
    public ToDos(String description) {
        super(description);
    }

    /**
     * Constructs a `ToDos` task with a specific completion status.
     *
     * @param description The description of the to-do task.
     * @param isDone      The completion status of the task.
     */
    public ToDos(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the string representation of the task for saving to a file.
     *
     * @return A formatted string for file storage.
     */
    public String getSaveLineFormat() {
        return String.format("T|%s\n", super.getSaveLineFormat());
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return A formatted string for displaying to the user.
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
