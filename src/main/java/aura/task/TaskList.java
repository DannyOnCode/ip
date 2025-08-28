package aura.task;

import aura.io.Parser;
import aura.io.Ui;
import aura.storage.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of tasks, including adding, deleting, and modifying tasks.
 */
public class TaskList {
    private List<Task> tasks;
    private final Ui ui;

    /**
     * Constructs a TaskList object.
     * Initializes an empty list of tasks and a Ui object for user interaction.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.ui = new Ui();
    }

    /**
     * Loads tasks from a storage file into the task list.
     *
     * @param storage The storage handler to load tasks from.
     * @throws IOException If an error occurs during file reading.
     */
    public void loadTask(Storage storage) throws IOException {
        this.tasks = storage.loadTasks();
    }

    /**
     * Saves the current list of tasks to the storage file.
     *
     * @param storage The storage handler to save tasks to.
     * @return A confirmation message.
     */
    public String saveFile(Storage storage) {
        return storage.saveTasks(this.tasks);
    }

    /**
     * Prints all tasks in the list to the console.
     */
    public void printList() {
        ui.displayGivenList(this.tasks);
    }

    /**
     * Adds a task to the list and displays a confirmation message.
     *
     * @param task The task to be added.
     * @return true, indicating the operation was successful.
     */
    public boolean addTask(Task task) {
        this.tasks.add(task);
        ui.printDivider();
        ui.replyPrint("Got it. I've added this task:");
        ui.replyPrint(String.format("  %s", task));
        ui.replyPrint(String.format("Now you have %d tasks in the list.", this.tasks.size()));
        ui.printDivider();
        return true;
    }

    /**
     * Deletes a task from the list based on user input.
     *
     * @param input The user command, including the index of the task to delete.
     * @return true if the task was deleted successfully, false otherwise.
     */
    public boolean deleteTask(String input) {
        try {
            String trimmedIndex = input.substring(7).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = this.tasks.get(index - 1);
            this.tasks.remove(index - 1);
            ui.printDivider();
            ui.replyPrint("Understood Sir, I have removed the task: ");
            ui.replyPrint(String.format("  %s", task));
            ui.replyPrint(String.format("Now you have %d tasks in the list.", this.tasks.size()));
            ui.printDivider();
            return true;
        } catch (NumberFormatException e) {
            ui.printDivider();
            ui.replyPrint("You've used the command delete but the index is invalid.");
            ui.printDivider();
            return false;
        } catch (IndexOutOfBoundsException e) {
            ui.printDivider();
            ui.replyPrint("WHAT? Your input index was not in the list");
            ui.printDivider();
            return false;
        }
    }

    /**
     * Marks a task as done based on user input.
     *
     * @param input The user command, including the index of the task to mark.
     * @return true if the task was marked successfully, false otherwise.
     */
    public boolean markTask(String input) {
        try {
            String trimmedIndex = input.substring(5).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = this.tasks.get(index - 1);
            task.markAsDone();
            ui.printDivider();
            ui.replyPrint("Well Done sir! I've marked this task as done: ");
            ui.replyPrint(String.format("%s", task));
            ui.printDivider();
            return true;
        } catch (NumberFormatException e) {
            ui.printDivider();
            ui.replyPrint("You've used the command mark but the index is invalid.");
            ui.replyPrint("If this was intended, please use a different keyword (e.g \"check\")");
            ui.printDivider();
            return false;
        } catch (IndexOutOfBoundsException e) {
            ui.printDivider();
            System.out.println("WHAT? Your input index was not in the list");
            ui.printDivider();
            return false;
        }
    }

    /**
     * Marks a task as not done based on user input.
     *
     * @param input The user command, including the index of the task to unmark.
     * @return true if the task was unmarked successfully, false otherwise.
     */
    public boolean unMarkTask(String input) {
        try {
            String trimmedIndex = input.substring(7).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = this.tasks.get(index - 1);
            task.markAsUnDone();

            ui.printDivider();
            ui.replyPrint("Alright, I've marked this task as not done: ");
            ui.replyPrint(String.format("%s", task));
            ui.printDivider();
            return true;
        } catch (NumberFormatException e) {
            ui.printDivider();
            ui.replyPrint("You've used the command unmark but the index is invalid.");
            ui.replyPrint("If this was intended, please use a different keyword (e.g \"uncheck\")");
            ui.printDivider();
            return false;
        } catch (IndexOutOfBoundsException e) {
            ui.printDivider();
            ui.replyPrint("WHAT? Your input index was not in the list");
            ui.printDivider();
            return false;
        }
    }

    /**
     * Parses user input to create and add a ToDo task.
     *
     * @param input The full user command for adding a to-do.
     * @return true if the to-do was added successfully, false otherwise.
     */
    public boolean addToDo(String input) {
        try {
            String trimmedTask = input.substring(5).trim();
            return addTask(new ToDos(trimmedTask));
        } catch (Exception e) {
            ui.printDivider();
            ui.replyPrint("Please follow the format \"todo [Task]\"");
            ui.printDivider();
            return false;
        }
    }

    /**
     * Parses user input to create and add a Deadline task.
     *
     * @param input The full user command for adding a deadline.
     * @return true if the deadline was added successfully, false otherwise.
     */
    public boolean addDeadline(String input) {
        try {
            String trimmedTask = input.substring(9).trim();
            String[] splitDeadline = trimmedTask.split("/by");
            LocalDateTime byDate = Parser.parseStringToDate(splitDeadline[1].trim());
            if (byDate != null) {
                return addTask(new Deadlines(splitDeadline[0].trim(), byDate));
            } else {
                ui.printDivider();
                ui.replyPrint("Please follow the format yyyy-mm-dd HHmm and ensure the date is correct");
                ui.printDivider();
                return false;
            }
        } catch (Exception e) {
            ui.printDivider();
            ui.replyPrint("Please follow the format \"deadline [Task] /by [Due date]\"");
            ui.printDivider();
            return false;
        }
    }

    /**
     * Parses user input to create and add an Event task.
     *
     * @param input The full user command for adding an event.
     * @return true if the event was added successfully, false otherwise.
     */
    public boolean addEvent(String input) {
        try {
            String trimmedTask = input.substring(6).trim();
            String[] splitEvent = trimmedTask.split("/from");
            String[] splitDateRange = splitEvent[1].split("/to");
            LocalDateTime fromDate = Parser.parseStringToDate(splitDateRange[0].trim());
            LocalDateTime toDate = Parser.parseStringToDate(splitDateRange[1].trim());
            if (fromDate != null && toDate != null) {
                return addTask(new Events(splitEvent[0].trim(), fromDate, toDate));
            } else {
                ui.printDivider();
                ui.replyPrint("Please follow the format yyyy-mm-dd HHmm and ensure the date is correct");
                ui.printDivider();
                return false;
            }

        } catch (Exception e) {
            ui.printDivider();
            ui.replyPrint("Please follow the format \"event [Task] /from [Start date] /to [End date]\"");
            ui.printDivider();
            return false;
        }
    }

    /**
     * Filters the task list and returns a new list containing only tasks
     * whose descriptions contain the given keyword.
     *
     * @param input The full user command, including the keyword (e.g., "find book").
     * @return A new list of tasks that match the keyword, or null if the input is invalid.
     */
    public List<Task> getTasksWithKeyword(String input) {
        try {
            String trimmedKeyword = input.substring(5).trim();

            List<Task> filteredTask = new ArrayList<>();
            for (Task task : tasks) {
                if (task.containsKeyword(trimmedKeyword)) {
                    filteredTask.add(task);
                }
            }
            return filteredTask;
        } catch (Exception e) {
            ui.printDivider();
            ui.replyPrint("Please enter a valid keyword");
            ui.printDivider();
            return null;
        }

    }
}
