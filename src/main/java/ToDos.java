public class ToDos extends Task{
    public ToDos(String description) {
        super(description);
    }

    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
