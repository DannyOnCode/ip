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
                    } else {
                        addItem(input);
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
        System.out.println("\t\tBye Bye my friend");
        printDivider();
    }

    private static void addItem(String item) {
        Aura.lists.add(new Task(item));
        printDivider();
        System.out.println(String.format("\t\tadded: %s", item));
        printDivider();
    }

    private static void markTask(String input) {
        String trimmedIndex = input.substring(5).trim();

        int index = Integer.parseInt(trimmedIndex);
        Task task = lists.get(index - 1);
        task.markAsDone();
        printDivider();
        System.out.println("\t\tWell Done sir! I've marked this task as done: ");
        System.out.println(String.format("\t\t%s", task));
        printDivider();
    }

    private static void unMarkTask(String input) {
        String trimmedIndex = input.substring(7).trim();

        int index = Integer.parseInt(trimmedIndex);
        Task task = lists.get(index - 1);
        task.markAsUnDone();

        printDivider();
        System.out.println("\t\tAlright, I've marked this task as not done: ");
        System.out.println(String.format("\t\t%s", task));
        printDivider();
    }

    private static void printList() {
        printDivider();
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(String.format("\t\t%d. %s", (i + 1), lists.get(i)));
        }
        printDivider();
    }

    private static void printDivider() {
        System.out.println("    ____________________________________________________________");
    }
}
