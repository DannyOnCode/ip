package aura.task;

/**
 * Represents a task in the Aura application.
 * This is an abstract class that serves as the base for different types of tasks.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a task with a given description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return "X" if the task is done, " " (a space) otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUnDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task for saving to a file.
     *
     * @return A formatted string for file storage.
     */
    public String getSaveLineFormat() {
        return String.format("%s|%s", this.description, this.isDone ? "1" : "0");
    }

    /**
     * Returns a string representation of the task for display.
     *
     * @return A formatted string for displaying to the user.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }
}
