package aura.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the ToDos class.
 */
public class ToDosTest {

    /**
     * Tests the toString() method to ensure it formats the to-do details correctly for display.
     */
    @Test
    public void toString_formatForDisplay_correctFormat() {
        ToDos todo = new ToDos("Submit CS2103 IP");
        assertEquals("[T][ ] Submit CS2103 IP", todo.toString());
    }

    /**
     * Tests the getSaveLineFormat() method to ensure it formats the to-do details
     * correctly for saving to a file.
     */
    @Test
    public void getSaveLineFormat_formatForSaving_correctFormat() {
        ToDos todo = new ToDos("Shower");
        todo.markAsDone();
        assertEquals("T|1|Shower\n", todo.getSaveLineFormat());
    }

    /**
     * Tests the constructor that accepts a completion status to ensure the
     * object is created correctly with the status marked as done.
     */
    @Test
    public void constructor_withIsDoneTrue_taskIsMarkedAsDone() {
        ToDos todo = new ToDos("Cry about CS2103", true);
        assertEquals("[T][X] Cry about CS2103", todo.toString());
    }
}
