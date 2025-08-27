public class ToDos extends Task{
    public ToDos(String description) {
        super(description);
    }

    public ToDos(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
