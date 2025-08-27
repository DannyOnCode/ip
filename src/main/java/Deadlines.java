public class Deadlines extends Task{
    protected String by;

    public Deadlines(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadlines(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String getSaveLineFormat() {
        return String.format("D|%s|%s\n", super.getSaveLineFormat(), this.by);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by %s)",
                super.toString(), this.by);
    }
}
