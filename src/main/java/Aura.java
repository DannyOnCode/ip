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
    private static final List<String> lists = new ArrayList<>();

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
                default -> addItem(input);
            }
        }
    }

    private static void formatReplyAndDisplay(String reply) {
        System.out.println("    ____________________________________________________________");
        System.out.println("        " + reply);
        System.out.println("    ____________________________________________________________");
    }

    private static void greeting() {
        formatReplyAndDisplay(Aura.LOGO
                + "Hello! I'm " + Aura.ASSISTANCE_NAME + "\n"
                + "What can I do for you?");
    }

    private static void exit() {
        formatReplyAndDisplay("Bye Bye my friend");
    }

    private static void addItem(String item) {
        Aura.lists.add(item);
        formatReplyAndDisplay(String.format("added: %s", item));
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
