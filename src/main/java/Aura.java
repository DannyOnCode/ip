import jdk.jfr.Event;

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
    private static final List<Task> lists = new ArrayList<>();

    public static void main(String[] args) {
        greeting();
        echo();
    }

    private static void echo() {
        String input;
        boolean flag = true;
        while (flag) {
            Scanner scanner = new Scanner(System.in);
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
                    } else {
                        printDivider();
                        replyPrint("Your input was invalid, make sure to include a valid command");
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
            System.out.println("You've used the command mark but the index is invalid.");
            System.out.println("If this was intended, please use a different keyword (e.g \"check\")");
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
            String trimmedInput = input.substring(5).trim();
            addTask(new ToDos(trimmedInput));
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
            addTask(new Deadlines(splitDeadline[0].trim(), splitDeadline[1].trim()));
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
            addTask(new Events(splitEvent[0].trim(), splitDateRange[0].trim(), splitDateRange[1].trim()));
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
}
