public class Aura {
    public static final String ASSISTANCE_NAME = "Aura";
    public static final String LOGO = "    _                   \n"
                                    + "   / \\  _   _ _ __ __ _ \n"
                                    + "  / _ \\| | | | '__/ _` |\n"
                                    + " / ___ \\ |_| | | | (_| |\n"
                                    + "/_/   \\_\\__,_|_|  \\__,_|\n";

    public static void main(String[] args) {
        printDividerLine();
        System.out.println(Aura.LOGO);
        System.out.println("Hello! I'm " + Aura.ASSISTANCE_NAME);
        System.out.println("What can I do for you?");
        printDividerLine();
        System.out.println("Bye Bye. Hope to see you again soon!");
        printDividerLine();
    }

    public static void printDividerLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printLogo() {
        System.out.println(Aura.LOGO);
    }
}
