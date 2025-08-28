package aura.io;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

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
}
