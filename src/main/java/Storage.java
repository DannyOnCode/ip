import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage() {
        this.filePath = "./data/aura.txt";
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> loadTasks() throws IOException {
        File taskFile = new File(this.filePath);

        taskFile.getParentFile().mkdir();
        taskFile.createNewFile();

        Scanner scanFile = new Scanner(taskFile);
        List<Task> tasks = new ArrayList<>();

        String[] details;
        while (scanFile.hasNext()) {
            String nextLine = scanFile.nextLine();
            details = nextLine.split("\\|");

            switch (details[0]) {
                case "T" -> tasks.add(new ToDos(details[1], details[2].equals("1")));
                case "D" -> tasks.add(new Deadlines(details[1], details[2].equals("1"), details[3]));
                case "E" -> tasks.add(new Events(details[1], details[2].equals("1"), details[3], details[4]));
                default -> System.out.println("A line was corrupted :" + Arrays.toString(details));
            }
        }
        return tasks;
    }

    public String saveTasks(List<Task> taskList) {
        try (FileWriter fileWriter = new FileWriter(this.filePath)) {
            for (Task task : taskList) {
                fileWriter.write(task.getSaveLineFormat());
            }

            return "Successfully saved file to " + this.filePath;
        } catch (IOException e) {
            System.out.println("There seems to have been a problem");
            throw new RuntimeException(e);
        }
    }
}
