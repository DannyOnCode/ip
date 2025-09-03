package aura.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task. An `Events` object includes a description, a "from" date-time, and a "to" date-time.
 */
public class Events extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs a new `Events` task.
     *
     * @param description The description of the event.
     * @param from        The start date and time of the event.
     * @param to          The end date and time of the event.
     */
    public Events(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an `Events` task with a specific completion status.
     *
     * @param description The description of the event.
     * @param isDone      The completion status of the task.
     * @param from        The start date and time of the event.
     * @param to          The end date and time of the event.
     */
    public Events(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Converts a LocalDateTime object to a formatted string for display.
     *
     * @param date The date to format.
     * @return The formatted date string (e.g., "Aug 28 2025, 2:09 PM").
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
     * @return A formatted string for file storage (e.g., "E|Submit CS2103|1|2025-08-28 1409|2025-08-28 1420").
     */
    @Override
    public String getSaveLineFormat() {
        return String.format("E|%s|%s|%s\n", super.getSaveLineFormat(),
                convertDateToSave(this.from),
                convertDateToSave(this.to));
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return A formatted string for displaying to the user
     *      (e.g., [E][X] Submit CS2103 (from Aug 28 2025 2:09 pm to Aug 28 2025 2:20 pm).
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(), convertDateToString(this.from),
                convertDateToString(this.to));
    }
}
