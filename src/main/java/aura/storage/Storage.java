package aura.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import aura.task.Deadlines;
import aura.task.Events;
import aura.task.Task;
import aura.task.ToDos;



/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object with a default file path.
     */
    public Storage() {
        this.filePath = "./data/aura.txt";
    }

    /**
     * Constructs a Storage object with a specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     * @throws IOException If an error occurs during file reading.
     */
    public List<Task> loadTasks() throws IOException {
        File taskFile = new File(this.filePath);

        taskFile.getParentFile().mkdir();
        taskFile.createNewFile();

        Scanner scanFile = new Scanner(taskFile);
        List<Task> tasks = new ArrayList<>();

        String[] taskParts;
        while (scanFile.hasNext()) {
            String nextLine = scanFile.nextLine();
            taskParts = nextLine.split("\\|");

            switch (taskParts[0]) {
                case "T" -> tasks.add(new ToDos(taskParts[1], taskParts[2].equals("1")));
                case "D" -> tasks.add(new Deadlines(taskParts[1], taskParts[2].equals("1"),
                        parseStringToDate(taskParts[3])));
                case "E" -> tasks.add(new Events(taskParts[1], taskParts[2].equals("1"),
                        parseStringToDate(taskParts[3]), parseStringToDate(taskParts[4])));
                default -> System.out.println("A line was corrupted :" + Arrays.toString(taskParts));
            }
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the storage file.
     *
     * @param taskList The list of tasks to be saved.
     * @return A confirmation message.
     */
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

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTime The date-time string to parse.
     * @return The parsed LocalDateTime object, or null if parsing fails.
     */
    private static LocalDateTime parseStringToDate(String dateTime) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTime, inputFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
