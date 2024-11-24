import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import automatonSimulation.CellularAutomaton;

public class CellularAutomatonTest {
    @Test
    public void testGetCellState() {
        CellularAutomaton automaton = new CellularAutomaton(10, 10);
        automaton.setCellState(3, 3, true); // Egy cellát állítsunk aktívra
        assertTrue(automaton.getCellState(3, 3)); // Ellenőrizzük az állapotot
    }

    @Test
    public void testSetCellState() {
        CellularAutomaton automaton = new CellularAutomaton(10, 10);
        automaton.setCellState(4, 4, true);
        assertTrue(automaton.getCellState(4, 4)); // Aktív
        automaton.setCellState(4, 4, false);
        assertFalse(automaton.getCellState(4, 4)); // Inaktív
    }

    @Test
    public void testClearGrid() {
        CellularAutomaton automaton = new CellularAutomaton(5, 5);
        automaton.setCellState(2, 2, true);
        automaton.reset();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertFalse(automaton.getCellState(x, y));
            }
        }
    }
}
