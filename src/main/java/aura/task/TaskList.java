package aura.task;

import aura.io.Parser;
import aura.io.Ui;
import aura.storage.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;
    private final Ui ui;

    public TaskList() {
        this.tasks = new ArrayList<>();
        this.ui = new Ui();
    }

    public void loadTask(Storage storage) throws IOException {
        this.tasks = storage.loadTasks();
    }

    public String saveFile(Storage storage) {
        return storage.saveTasks(this.tasks);
    }

    public void printList() {
        ui.displayGivenList(this.tasks);
    }

    public boolean addTask(Task task) {
        this.tasks.add(task);
        ui.printDivider();
        ui.replyPrint("Got it. I've added this task:");
        ui.replyPrint(String.format("  %s", task));
        ui.replyPrint(String.format("Now you have %d tasks in the list.", this.tasks.size()));
        ui.printDivider();
        return true;
    }

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
