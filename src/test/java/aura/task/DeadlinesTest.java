package aura.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlinesTest {

    @Test
    public void toString_formatForDisplay_correctFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 18, 0); // 6:00 PM on Aug 27, 2025
        Deadlines deadline = new Deadlines("Finish CS2103 IP", dateTime);

        String expected = "[D][ ] Finish CS2103 IP (by Aug 27 2025, 6:00 pm)";

        assertEquals(expected, deadline.toString());
    }

    @Test
    public void getSaveLineFormat_formatForSaving_correctFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 27, 18, 0); // 6:00 PM
        Deadlines deadline = new Deadlines("Submit report", dateTime);

        deadline.markAsDone();

        String expected = "D|Submit report|1|2025-08-27 1800\n";

        assertEquals(expected, deadline.getSaveLineFormat());
    }

    @Test
    public void constructor_withIsDoneTrue_taskIsMarkedAsDone() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 1, 9, 30);
        Deadlines deadline = new Deadlines("Project presentation", true, dateTime);

        String expected = "[D][X] Project presentation (by Sep 01 2025, 9:30 am)";

        assertEquals(expected, deadline.toString());
    }
}