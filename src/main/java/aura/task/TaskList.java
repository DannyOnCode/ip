package aura.task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import aura.io.Parser;
import aura.io.Ui;
import aura.storage.Storage;

/**
 * Manages a list of tasks, providing methods to add, delete, and modify them.
 */
public class TaskList {
    private List<Task> tasks;
    private final Ui ui;

    /**
     * Constructs a TaskList object, initializing an empty list and a UI for interaction.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.ui = new Ui();
    }

    /**
     * Loads tasks from a storage file into the list.
     *
     * @param storage The storage handler to load tasks from.
     * @throws IOException If an error occurs during file reading.
     */
    public void loadTask(Storage storage) throws IOException {
        this.tasks = storage.loadTasks();
    }

    /**
     * Saves the current task list to the storage file.
     *
     * @param storage The storage handler to save tasks to.
     * @return A confirmation message.
     */
    public String saveFile(Storage storage) {
        return storage.saveTasks(this.tasks);
    }

    /**
     * Formats and returns a string representation of tasks in the list.
     * Returns two types of sorting, without sorting or sorting by due date (for Deadlines)
     *
     * @return A string listing all tasks.
     */
    public String printList(String input) {
        try {
            String trimmedIndex = input.substring(4).trim();
            String returnString;
            switch (trimmedIndex) {
                case "" -> returnString = ui.displayGivenList(this.tasks);
                case "1" -> {
                    List<Deadlines> deadlineTasks = new ArrayList<>();
                    for (Task task : this.tasks) {
                        if (task instanceof Deadlines) {
                            deadlineTasks.add((Deadlines) task);
                        }
                    }

                    deadlineTasks.sort(null);

                    if (deadlineTasks.isEmpty()) {
                        returnString = "There are no deadline tasks to display.";
                    } else {
                        returnString = ui.displayGivenList(new ArrayList<>(deadlineTasks));
                    }
                }
                default -> returnString = "ERROR: Invalid index given, please enter 1 after a space";
            }
            return returnString;
        } catch (Exception e) {
            return "ERROR: unexpected error, please try again with the either \"list\" or \"list 1\"";
        }
    }

    /**
     * Adds a single task to the list and returns a confirmation message.
     *
     * @param task The task to be added.
     * @return A string confirming the addition and showing the new task count.
     */
    public String addTask(Task task) {
        int sizeBeforeInsert = tasks.size();
        this.tasks.add(task);
        assert tasks.size() == sizeBeforeInsert + 1
                : "Successfully inserted by list size did not increase";
        return "Got it. I've added this task:\n"
                + String.format("  %s\n", task)
                + String.format("Now you have %d tasks in the list.", this.tasks.size());
    }

    /**
     * Deletes a task by its index from user input. Handles invalid input or index out of bounds.
     *
     * @param input The user command string (e.g., "delete 1").
     * @return A confirmation message on success or an error message on failure.
     */
    public String deleteTask(String input) {
        try {
            String trimmedIndex = input.substring(7).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = this.tasks.get(index - 1);
            this.tasks.remove(index - 1);
            return "Understood Sir, I have removed the task: \n"
                    + String.format("  %s\n", task)
                    + String.format("Now you have %d tasks in the list.", this.tasks.size());
        } catch (NumberFormatException e) {
            return "You've used the command delete but the index is invalid.";
        } catch (IndexOutOfBoundsException e) {
            return "WHAT? Your input index was not in the list";
        }
    }

    /**
     * Marks a task as complete by its index from user input.
     *
     * @param input The user command string (e.g., "mark 1").
     * @return A success message or an error message if the index is invalid.
     */
    public String markTask(String input) {
        try {
            String trimmedIndex = input.substring(5).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = this.tasks.get(index - 1);
            task.markAsDone();
            return "Well Done sir! I've marked this task as done: \n"
                    + String.format("%s", task);
        } catch (NumberFormatException e) {
            return "ERROR: You've used the command mark but the index is invalid.\n"
                    + "If this was intended, please use a different keyword (e.g \"check\")";
        } catch (IndexOutOfBoundsException e) {
            return "ERROR: WHAT? Your input index was not in the list";
        }
    }

    /**
     * Marks a task as not complete by its index from user input.
     *
     * @param input The user command string (e.g., "unmark 1").
     * @return A success message or an error message.
     */
    public String unMarkTask(String input) {
        try {
            String trimmedIndex = input.substring(7).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = this.tasks.get(index - 1);
            task.markAsUnDone();
            return "Alright, I've marked this task as not done: \n"
                    + String.format("%s", task);
        } catch (NumberFormatException e) {
            ui.printDivider();
            ui.replyPrint("");
            ui.replyPrint("");
            ui.printDivider();
            return "ERROR: You've used the command unmark but the index is invalid.\n"
                    + "If this was intended, please use a different keyword (e.g \"uncheck\")";
        } catch (IndexOutOfBoundsException e) {
            return "ERROR: WHAT? Your input index was not in the list";
        }
    }

    /**
     * Parses input to create and add a ToDo task.
     *
     * @param input The command string (e.g., "todo read book").
     * @return A confirmation message or an error.
     */
    public String addToDo(String input) {
        try {
            String trimmedTask = input.substring(5).trim();
            return addTask(new ToDos(trimmedTask));
        } catch (Exception e) {
            return "ERROR: Please follow the format \"todo [Task]\"";
        }
    }

    /**
     * Parses input to create and add a Deadline task.
     *
     * @param input The command string (e.g., "deadline submit report /by 2025-10-26 1800").
     * @return A confirmation message or an error.
     */
    public String addDeadline(String input) {
        try {
            String trimmedTask = input.substring(9).trim();
            String[] splitDeadline = trimmedTask.split("/by");
            LocalDateTime byDate = Parser.parseStringToDate(splitDeadline[1].trim());
            if (byDate != null) {
                return addTask(new Deadlines(splitDeadline[0].trim(), byDate));
            } else {
                return "ERROR: Please follow the format yyyy-mm-dd HHmm and ensure the date is correct";
            }
        } catch (Exception e) {
            return "ERROR: Please follow the format \"deadline [Task] /by [Due date]\"";
        }
    }

    /**
     * Parses input to create and add an Event task.
     *
     * @param input The command string (e.g., "event team meeting /from 2025-10-26 1400 /to 2025-10-26 1500").
     * @return A confirmation message or an error.
     */
    public String addEvent(String input) {
        try {
            String trimmedTask = input.substring(6).trim();
            String[] splitEvent = trimmedTask.split("/from");
            String[] splitDateRange = splitEvent[1].split("/to");
            LocalDateTime fromDate = Parser.parseStringToDate(splitDateRange[0].trim());
            LocalDateTime toDate = Parser.parseStringToDate(splitDateRange[1].trim());
            if (fromDate != null && toDate != null) {
                return addTask(new Events(splitEvent[0].trim(), fromDate, toDate));
            } else {
                return "ERROR: Please follow the format yyyy-mm-dd HHmm and ensure the date is correct";
            }

        } catch (Exception e) {
            return "ERROR: Please follow the format \"event [Task] "
                    + "/from [Start date] /to [End date]\"";
        }
    }

    /**
     * Finds tasks containing a specific keyword and returns a list of them.
     *
     * @param input The user command (e.g., "find report").
     * @return A string listing matching tasks or an error if the input is invalid.
     */
    public String getTasksWithKeyword(String input) {
        try {
            String trimmedKeyword = input.substring(5).trim();

            List<Task> filteredTask = new ArrayList<>();
            for (Task task : tasks) {
                if (task.containsKeyword(trimmedKeyword)) {
                    filteredTask.add(task);
                }
            }
            return ui.displayGivenList(filteredTask);
        } catch (Exception e) {
            return "ERROR: Please enter a valid keyword";
        }

    }
}
