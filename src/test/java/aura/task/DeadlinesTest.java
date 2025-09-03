package aura.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Deadlines class.
 */
public class DeadlinesTest {

    /**
     * Tests the toString() method to ensure it formats the deadline details correctly
     * for display, including the 12-hour AM/PM time format.
     */
    @Test
    public void toString_formatForDisplay_correctFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 18, 0); // 6:00 PM on Aug 27, 2025
        Deadlines deadline = new Deadlines("Finish CS2103 IP", dateTime);

        String expected = "[D][ ] Finish CS2103 IP (by Aug 27 2025, 6:00 pm)";

        assertEquals(expected, deadline.toString());
    }

    /**
     * Tests the getSaveLineFormat() method to ensure it formats the deadline details
     * correctly for saving to a file, including the 24-hour time format.
     */
    @Test
    public void getSaveLineFormat_formatForSaving_correctFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 18, 0); // 6:00 PM
        Deadlines deadline = new Deadlines("Submit report", dateTime);

        deadline.markAsDone();

        String expected = "D|Submit report|1|2025-08-27 1800\n";

        assertEquals(expected, deadline.getSaveLineFormat());
    }

    /**
     * Tests the constructor that accepts a completion status to ensure the
     * object is created correctly with the status marked as done.
     */
    @Test
    public void constructor_withIsDoneTrue_taskIsMarkedAsDone() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 1, 9, 30);
        Deadlines deadline = new Deadlines("Project presentation", true, dateTime);

        String expected = "[D][X] Project presentation (by Sep 01 2025, 9:30 am)";

        assertEquals(expected, deadline.toString());
    }
}
