import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import automatonSimulation.CellularAutomaton;
import rules.GameOfLifeRule;

import java.util.List;

public class CellularAutomatonTest {
    @Test
    public void testUpdateWithRule() {
        CellularAutomaton automaton = new CellularAutomaton(3, 3);
        automaton.setRule(new GameOfLifeRule()); // Game of Life szabály

        // Bemeneti mátrix inicializálása
        automaton.setCellState(1, 1, true);
        automaton.setCellState(0, 1, true);
        automaton.setCellState(1, 0, true);

        // Mátrix frissítése
        automaton.update();

        // Várt kimeneti mátrix
        List<List<Boolean>> expectedMatrix = List.of(List.of(true, true, false),
                List.of(true, true, false),
                List.of(false, false, false));

        assertTrue(expectedMatrix.equals(automaton.getMatrix()), "A mátrix frissítése nem megfelelő.");
    }

    @Test
    public void testUpdateWithoutRule() {
        CellularAutomaton automaton = new CellularAutomaton(3, 3);

        // Próbáljuk frissíteni a mátrixot szabály nélkül
        Exception exception = assertThrows(IllegalStateException.class, automaton::update);
        assertEquals("Nincs szabály beállítva", exception.getMessage());
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
