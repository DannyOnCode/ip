package aura.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline. A `Deadlines` object includes a description and a "by" date-time.
 */
public class Deadlines extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a new `Deadlines` task.
     *
     * @param description The description of the deadline task.
     * @param by          The deadline date and time.
     */
    public Deadlines(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a `Deadlines` task with a specific completion status.
     *
     * @param description The description of the deadline task.
     * @param isDone      The completion status of the task.
     * @param by          The deadline date and time.
     */
    public Deadlines(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Converts a LocalDateTime object to a formatted string for display.
     *
     * @param date The date to format.
     * @return The formatted date string (e.g., "Aug 28 2025, 2:09 pm").
     */
    private String convertDateToString(LocalDateTime date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return date.format(dateTimeFormatter);
    }

    /**
     * Converts a LocalDateTime object to a formatted string for saving.
     *
     * @param date The date to format.
     * @return The formatted date string (e.g., "2025-08-28 1409").
     */
    private String convertDateToSave(LocalDateTime date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return date.format(dateTimeFormatter);
    }

    /**
     * Returns the string representation of the task for saving to a file.
     *
     * @return A formatted string for file storage (e.g., "D|Submit CS2103|1|2025-08-28 1409").
     */
    @Override
    public String getSaveLineFormat() {
        return String.format("D|%s|%s\n", super.getSaveLineFormat(), convertDateToSave(this.by));
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return A formatted string for displaying to the user (e.g., [D][X] Submit CS2103 (by Aug 28 2025 2:09 pm).
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by %s)",
                super.toString(), convertDateToString(this.by));
    }
}
