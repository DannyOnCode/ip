import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aura {
    public static final String ASSISTANCE_NAME = "Aura";
    public static final String LOGO = "    _                   \n"
            + "   / \\  _   _ _ __ __ _ \n"
            + "  / _ \\| | | | '__/ _` |\n"
            + " / ___ \\ |_| | | | (_| |\n"
            + "/_/   \\_\\__,_|_|  \\__,_|\n";
    private static List<Task> lists = new ArrayList<>();

    public static void main(String[] args) {
        greeting();
        try {
            loadTask();
        } catch (IOException e) {
            printDivider();
            replyPrint("Failed to load save file");
            throw new RuntimeException(e);
        }
        echo();
    }

    private static void echo() {
        String input;
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "bye" -> {
                    exit();
                    flag = false;
                }
                case "list" -> printList();
                default -> {
                    if (input.toLowerCase().startsWith("mark ")) {
                        markTask(input);
                    } else if (input.toLowerCase().startsWith("unmark ")) {
                        unMarkTask(input);
                    } else if (input.toLowerCase().startsWith("todo ")) {
                        addToDo(input);
                    } else if (input.toLowerCase().startsWith("deadline ")) {
                        addDeadline(input);
                    } else if (input.toLowerCase().startsWith("event ")) {
                        addEvent(input);
                    } else if (input.toLowerCase().startsWith("delete ")) {
                        deleteTask(input);
                    } else {
                        String error;
                        List<String> commands = List.of("mark", "unmark", "todo", "deadline", "event", "delete");
                        if (commands.contains(input)) {
                            error = String.format("ERROR SIR!! The description of the command %s cannot be empty.", input);
                        } else {
                            error = "Your input was invalid, make sure to include a valid command";
                        }
                        printDivider();
                        replyPrint(error);
                        printDivider();
                    }
                }
            }
        }
    }

    private static void greeting() {
        System.out.println("____________________________________________________________");
        System.out.println(Aura.LOGO
                + "Hello! I'm " + Aura.ASSISTANCE_NAME + "\n"
                + "What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private static void exit() {
        printDivider();
        saveFile();
        replyPrint("Bye Bye my friend");
        printDivider();
    }

    private static void addTask(Task task) {
        Aura.lists.add(task);
        printDivider();
        replyPrint("Got it. I've added this task:");
        replyPrint(String.format("  %s", task));
        replyPrint(String.format("Now you have %d tasks in the list.", Aura.lists.size()));
        printDivider();
    }

    private static void deleteTask(String input) {
        try {
            String trimmedIndex = input.substring(7).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = Aura.lists.get(index - 1);
            Aura.lists.remove(index - 1);
            printDivider();
            replyPrint("Understood Sir, I have removed the task: ");
            replyPrint(String.format("  %s", task));
            replyPrint(String.format("Now you have %d tasks in the list.", Aura.lists.size()));
        } catch (NumberFormatException e) {
            printDivider();
            replyPrint("You've used the command delete but the index is invalid.");
        } catch (IndexOutOfBoundsException e) {
            printDivider();
            replyPrint("WHAT? Your input index was not in the list");
        } finally {
            printDivider();
        }
    }

    private static void markTask(String input) {
        try {
            String trimmedIndex = input.substring(5).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = lists.get(index - 1);
            task.markAsDone();
            printDivider();
            replyPrint("Well Done sir! I've marked this task as done: ");
            replyPrint(String.format("%s", task));
        } catch (NumberFormatException e) {
            printDivider();
            replyPrint("You've used the command mark but the index is invalid.");
            replyPrint("If this was intended, please use a different keyword (e.g \"check\")");
        } catch (IndexOutOfBoundsException e) {
            printDivider();
            System.out.println("WHAT? Your input index was not in the list");
        } finally {
            printDivider();
        }
    }

    private static void unMarkTask(String input) {
        try {
            String trimmedIndex = input.substring(7).trim();

            int index = Integer.parseInt(trimmedIndex);
            Task task = lists.get(index - 1);
            task.markAsUnDone();

            printDivider();
            replyPrint("Alright, I've marked this task as not done: ");
            replyPrint(String.format("%s", task));
        } catch (NumberFormatException e) {
            printDivider();
            replyPrint("You've used the command unmark but the index is invalid.");
            replyPrint("If this was intended, please use a different keyword (e.g \"uncheck\")");
        } catch (IndexOutOfBoundsException e) {
            printDivider();
            replyPrint("WHAT? Your input index was not in the list");
        } finally {
            printDivider();
        }
    }

    private static void addToDo(String input) {
        try {
            String trimmedTask = input.substring(5).trim();
            addTask(new ToDos(trimmedTask));
        } catch (Exception e) {
            printDivider();
            replyPrint("Please follow the format \"todo [Task]\"");
            printDivider();
        }
    }

    private static void addDeadline(String input) {
        try {
            String trimmedTask = input.substring(9).trim();
            String[] splitDeadline = trimmedTask.split("/by");
            LocalDateTime byDate = parseStringToDate(splitDeadline[1].trim());
            if (byDate != null) {
                addTask(new Deadlines(splitDeadline[0].trim(), byDate));
            }
        } catch (Exception e) {
            printDivider();
            replyPrint("Please follow the format \"deadline [Task] /by [Due date]\"");
            printDivider();
        }
    }

    private static void addEvent(String input) {
        try {
            String trimmedTask = input.substring(6).trim();
            String[] splitEvent = trimmedTask.split("/from");
            String[] splitDateRange = splitEvent[1].split("/to");
            LocalDateTime fromDate = parseStringToDate(splitDateRange[0].trim());
            LocalDateTime toDate = parseStringToDate(splitDateRange[1].trim());
            if (fromDate != null && toDate != null) {
                addTask(new Events(splitEvent[0].trim(), fromDate, toDate));
            }
        } catch (Exception e) {
            printDivider();
            replyPrint("Please follow the format \"event [Task] /from [Start date] /to [End date]\"");
            printDivider();
        }
    }

    private static void printList() {
        printDivider();
        for (int i = 0; i < lists.size(); i++) {
            replyPrint(String.format("%d. %s", (i + 1), lists.get(i)));
        }
        printDivider();
    }

    private static void printDivider() {
        System.out.println("    ____________________________________________________________");
    }

    private static void replyPrint(String text) {
        System.out.println("\t\t" + text);
    }

    private static void saveFile() {
        Storage store = new Storage();
        replyPrint(store.saveTasks(lists));
    }

    private static LocalDateTime parseStringToDate(String dateTime) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTime, inputFormatter);
        } catch (DateTimeParseException e) {
            printDivider();
            replyPrint("Please follow the format yyyy-mm-dd HHmm and ensure the date is correct");
            printDivider();
            return null;
        }
    }

    private static void loadTask() throws IOException {
        Storage store = new Storage();
        lists = store.loadTasks();
    }
}
