import java.util.Scanner;

public class Aura {
    public static final String ASSISTANCE_NAME = "Aura";
    public static final String LOGO = "    _                   \n"
                                    + "   / \\  _   _ _ __ __ _ \n"
                                    + "  / _ \\| | | | '__/ _` |\n"
                                    + " / ___ \\ |_| | | | (_| |\n"
                                    + "/_/   \\_\\__,_|_|  \\__,_|\n";

    public static void main(String[] args) {
        greeting();
        echo();
        exit();
    }

    private static void formatReplyAndDisplay(String reply) {
        System.out.println("    ____________________________________________________________");
        System.out.println("        " + reply);
        System.out.println("    ____________________________________________________________");
    }

    private static void echo() {
        String input;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if (input.toLowerCase().equals("bye")) {
                formatReplyAndDisplay("Bye Bye friend");
                break;
            }

            formatReplyAndDisplay(input);
        }
    }

    private static void greeting() {
        formatReplyAndDisplay(Aura.LOGO
                + "Hello! I'm " + Aura.ASSISTANCE_NAME + "\n"
                + "What can I do for you?");
    }

    private static void exit() {
        System.out.print("Bye Bye. Hope to see you again soon!");
    }
}
