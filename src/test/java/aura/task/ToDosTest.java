package aura.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDosTest {

    @Test
    public void toString_formatForDisplay_correctFormat() {
        ToDos todo = new ToDos("Submit CS2103 IP");
        assertEquals("[T][ ] Submit CS2103 IP", todo.toString());
    }

    @Test
    public void getSaveLineFormat_formatForSaving_correctFormat() {
        ToDos todo = new ToDos("Shower");
        todo.markAsDone();
        assertEquals("T|Shower|1\n", todo.getSaveLineFormat());
    }

    @Test
    public void constructor_withIsDoneTrue_taskIsMarkedAsDone() {
        ToDos todo = new ToDos("Cry about CS2103", true);
        assertEquals("[T][X] Cry about CS2103", todo.toString());
    }
}