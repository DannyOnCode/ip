import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                case "D" -> tasks.add(new Deadlines(details[1], details[2].equals("1"), parseStringToDate(details[3])));
                case "E" -> tasks.add(new Events(details[1], details[2].equals("1"), parseStringToDate(details[3]), parseStringToDate(details[4])));
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

            return "Updated save file at " + this.filePath;
        } catch (IOException e) {
            System.out.println("There seems to have been an issue saving the tasks");
            throw new RuntimeException(e);
        }
    }

    private static LocalDateTime parseStringToDate(String dateTime) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTime, inputFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
