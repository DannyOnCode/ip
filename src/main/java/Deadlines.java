import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Deadlines extends Task{
    protected LocalDateTime by;

    public Deadlines(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadlines(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
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
        return String.format("D|%s|%s\n", super.getSaveLineFormat(), convertDateToSave(this.by));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by %s)",
                super.toString(), convertDateToString(this.by));
    }
}
