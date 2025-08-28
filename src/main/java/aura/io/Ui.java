package aura.io;

import java.util.List;

import aura.task.Task;

public class Ui {
    private final String ASSISTANCE_NAME = "Aura";
    private final String LOGO = """
                _                  \s
               / \\  _   _ _ __ __ _\s
              / _ \\| | | | '__/ _` |
             / ___ \\ |_| | | | (_| |
            /_/   \\_\\__,_|_|  \\__,_|
            """;
    private final Parser parser;
    public Ui(){
        this.parser = new Parser();
    }

    public void greeting() {
        System.out.println("____________________________________________________________");
        System.out.println(this.LOGO
                + "Hello! I'm " + this.ASSISTANCE_NAME + "\n"
                + "What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void exitMessage() {
        replyPrint("Bye Bye my friend");
        printDivider();
    }

    public void printDivider() {
        System.out.println("    ____________________________________________________________");
    }

    public void replyPrint(String text) {
        System.out.println("\t" + text);
    }

    public void showLoadingError() {
        replyPrint("There seems to have been an error in loading the file");
    }

    public String getInput(){
        return this.parser.getInput();
    }

    public void displayGivenList(List<Task> taskList) {
        printDivider();
        for (int i = 0; i < taskList.size(); i++) {
            replyPrint(String.format("%d. %s", (i + 1), taskList.get(i)));
        }
        printDivider();
    }
}
