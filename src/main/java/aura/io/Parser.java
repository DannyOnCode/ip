package aura.io;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import aura.command.ByeCommand;
import aura.command.Command;
import aura.command.DeadlineCommand;
import aura.command.DeleteCommand;
import aura.command.EventCommand;
import aura.command.FindCommand;
import aura.command.ListCommand;
import aura.command.MarkCommand;
import aura.command.TodoCommand;
import aura.command.UnknownCommand;
import aura.command.UnmarkCommand;

/**
 * Handles parsing of user input and date-time strings.
 */
public class Parser {
    private final Scanner scanner;

    /**
     * Constructs a Parser object.
     * Initializes a scanner to read from standard input.
     */
    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The user's input string.
     */
    public String getInput() {
        return this.scanner.nextLine();
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTime The date-time string in "yyyy-MM-dd HHmm" format.
     * @return The parsed LocalDateTime object, or null if parsing fails.
     */
    public static LocalDateTime parseStringToDate(String dateTime) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTime, inputFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Parses the raw user input string and returns the corresponding Command object.
     * This method acts as a factory for creating command objects based on the input keyword.
     *
     * @param input The full command string entered by the user.
     * @return The specific Command object (e.g., TodoCommand, MarkCommand) to be executed.
     *          Returns an UnknownCommand if the input does not match any known command format.
     **/
    public static Command parseInput(String input) {
        Command command;
        switch (input.toLowerCase()) {
            case "bye" -> {
                command = new ByeCommand(input);
            }
            case "list" -> {
                command = new ListCommand(input);
            }
            default -> {
                if (input.toLowerCase().startsWith("mark ")) {
                    assert input.toLowerCase().contains("mark") : "Entered mark but no \"mark\"";
                    command = new MarkCommand(input);
                } else if (input.toLowerCase().startsWith("unmark ")) {
                    assert input.toLowerCase().contains("unmark") : "Entered unmark but no \"unmark\"";
                    command = new UnmarkCommand(input);
                } else if (input.toLowerCase().startsWith("todo ")) {
                    assert input.toLowerCase().contains("todo") : "Entered todo but no \"todo\"";
                    command = new TodoCommand(input);
                } else if (input.toLowerCase().startsWith("deadline ")) {
                    assert input.toLowerCase().contains("deadline") : "Entered deadline but no \"deadline\"";
                    command = new DeadlineCommand(input);
                } else if (input.toLowerCase().startsWith("event ")) {
                    assert input.toLowerCase().contains("event") : "Entered event but no \"event\"";
                    command = new EventCommand(input);
                } else if (input.toLowerCase().startsWith("delete ")) {
                    assert input.toLowerCase().contains("delete") : "Entered delete but no \"delete\"";
                    command = new DeleteCommand(input);
                } else if (input.toLowerCase().startsWith("find ")) {
                    assert input.toLowerCase().contains("find") : "Entered find but no \"find\"";
                    command = new FindCommand(input);
                } else {
                    command = new UnknownCommand(input);
                }
            }
        }
        return command;
    }
}
