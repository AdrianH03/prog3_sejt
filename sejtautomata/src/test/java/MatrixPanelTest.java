import automatonSimulation.CellularAutomaton;
import automatonSimulation.MatrixPanel;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixPanelTest {

    @Test
    public void testSetCellColor() {
        CellularAutomaton automaton = new CellularAutomaton(10, 10); // 10x10-es mátrix
        MatrixPanel panel = new MatrixPanel(automaton);
        Color newColor = Color.RED;

        panel.setCellColor(newColor);
        assertEquals(newColor,panel.getCellColor(),"A cellaszín nem megfelelően lett beállítva.");
    }

    @Test
    public void testMatrixStateToggleOnMouseClick() {
        CellularAutomaton automaton = new CellularAutomaton(10, 10); // 10x10-es mátrix
        MatrixPanel panel = new MatrixPanel(automaton);

        // Szimuláljuk egy cellára kattintást
        int row = 2, col = 3;
        int x = col * 20; // X koordináta (cellSize * oszlop index)
        int y = row * 20; // Y koordináta (cellSize * sor index)

        // Kezdetben a cella állapota false
        assertFalse(automaton.getCellState(row, col), "A cella kezdő állapota nem false.");

        // Kattintást szimulálunk
        panel.dispatchEvent(new MouseEvent(panel, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x, y, 1, false));

        // Ellenőrizzük, hogy az állapot váltott-e
        assertTrue(automaton.getCellState(row, col), "A cella állapota nem váltott true-ra kattintás után.");
    }

    @Test
    public void testPanelPreferredSize() {
        CellularAutomaton automaton = new CellularAutomaton(10, 15); // 10 sor, 15 oszlop
        MatrixPanel panel = new MatrixPanel(automaton);

        Dimension expectedSize = new Dimension(15 * 20, 10 * 20); // cellSize * oszlop/sor méret
        assertEquals(expectedSize, panel.getPreferredSize(), "A panel mérete nem megfelelő.");
    }

    @Test
    public void testInitialCellColor() {
        CellularAutomaton automaton = new CellularAutomaton(10, 10);
        MatrixPanel panel = new MatrixPanel(automaton);

        assertEquals(Color.BLACK, panel.getCellColor(), "A kezdő cellaszínnek feketének kell lennie.");
    }
}
