package aura.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Events(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Events(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    private String convertDateToString(LocalDateTime date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return date.format(dateTimeFormatter);
    }

    private String convertDateToSave(LocalDateTime date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return date.format(dateTimeFormatter);
    }

    @Override
    public String getSaveLineFormat() {
        return String.format("E|%s|%s|%s\n", super.getSaveLineFormat(), convertDateToSave(this.from), convertDateToSave(this.to));
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(), convertDateToString(this.from), convertDateToString(this.to));
    }
}
