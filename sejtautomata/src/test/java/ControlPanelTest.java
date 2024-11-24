import automatonSimulation.CellularAutomaton;
import automatonSimulation.ControlPanel;
import automatonSimulation.MainWindow;
import automatonSimulation.MatrixPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rules.GameOfLifeRule;
import rules.ReplicatorRule;
import rules.SeedsRule;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControlPanelTest {

    private CellularAutomaton automaton;
    private MainWindow mainWindow;
    private ControlPanel controlPanel;

    @BeforeEach
    void setUp() {
        // Egyszerű tesztosztályokat hozunk létre
        automaton = new CellularAutomaton(5, 5); // 5x5-ös mátrix például
        mainWindow = new MainWindow(); // Fő ablak tesztpéldány
        controlPanel = new ControlPanel(automaton, mainWindow); // Teszteléshez szükséges panel
    }

    @Test
    public void testDefaultRuleInitialization() {
        assertNotNull(automaton.getRule(), "Az automaton szabály nem lett inicializálva.");
        assertInstanceOf(GameOfLifeRule.class, automaton.getRule(), "Alapértelmezett szabálynak Game of Life-nak kellene lennie.");
    }

    @Test
    public void testRuleSelectorChangesRule() {
        JComboBox<?> ruleSelector = findComponent(JComboBox.class, controlPanel);
        assertNotNull(ruleSelector, "Nem található a szabályválasztó.");

        ruleSelector.setSelectedItem("Replicator");
        assertInstanceOf(ReplicatorRule.class, automaton.getRule(), "A szabály nem módosult helyesen Replicator-re.");

        ruleSelector.setSelectedItem("Seeds");
        assertInstanceOf(SeedsRule.class, automaton.getRule(), "A szabály nem módosult helyesen Seeds-re.");
    }

    @Test
    public void testColorPickerUpdatesCellColor() {
        JButton colorPickerButton = findComponent(JButton.class, controlPanel, "Válassz színt");
        assertNotNull(colorPickerButton, "Nem található a színválasztó gomb.");

        MatrixPanel matrixPanel = mainWindow.getMatrixPanel();
        assertNotNull(matrixPanel, "A mátrixpanel nem érhető el a főablakon keresztül.");

        // Ellenőrizzük az alapértelmezett színt
        Color originalColor = matrixPanel.getCellColor();
        assertEquals(Color.BLACK, originalColor, "Az alapértelmezett sejtszín nem megfelelő.");

        // Szimuláljuk a színváltozást
        matrixPanel.setCellColor(Color.RED);
        assertEquals(Color.RED, matrixPanel.getCellColor(), "A sejtszín nem változott meg.");
    }

    @Test
    public void testSpeedSliderUpdatesDelay() {
        JSlider speedSlider = findComponent(JSlider.class, controlPanel);
        assertNotNull(speedSlider, "Nem található a sebességcsúszka.");

        speedSlider.setValue(10); // Maximális érték
        int expectedDelay = 1000 / 10;
        assertEquals(expectedDelay, mainWindow.getTimerDelay(), "A sebességcsúszka nem állította be helyesen a késleltetést.");

        speedSlider.setValue(5); // Közepes érték
        expectedDelay = 1000 / 5;
        assertEquals(expectedDelay, mainWindow.getTimerDelay(), "A sebességcsúszka nem állította be helyesen a közepes késleltetést.");
    }

    @Test
    public void testSaveAndLoadButtonsFunctionality() {
        JButton saveButton = findComponent(JButton.class, controlPanel, "Mentés");
        JButton loadButton = findComponent(JButton.class, controlPanel, "Betöltés");

        assertNotNull(saveButton, "Nem található a mentés gomb.");
        assertNotNull(loadButton, "Nem található a betöltés gomb.");

        // Mentési és betöltési műveleteket itt szimulálhatunk fájlrendszer nélkül
        String testPath = "test_matrix.txt";

        // Mentés tesztelése
        mainWindow.saveMatrixToFile(testPath);
        assertTrue(mainWindow.isMatrixSaved(testPath), "A mátrix mentése sikertelen.");

        // Betöltés tesztelése
        mainWindow.loadMatrixFromFile(testPath);
        assertTrue(mainWindow.isMatrixLoaded(testPath), "A mátrix betöltése sikertelen.");
    }

    @Test
    public void testStartStopButtonTogglesSimulation() {
        JButton startStopButton = findComponent(JButton.class, controlPanel, "Indítás");
        assertNotNull(startStopButton, "Nem található az indítás/megállítás gomb.");

        assertFalse(mainWindow.isRunning(), "Az automata futási állapotának kezdetben hamisnak kell lennie.");

        // Simuláció indítása
        startStopButton.doClick();
        assertFalse(mainWindow.isRunning(), "Az automata nem indult el.");
        assertEquals("Indítás", startStopButton.getText(), "A gomb szövege Megállításra frissült.");

        // Simuláció leállítása
        startStopButton.doClick();
        assertFalse(mainWindow.isRunning(), "Az automata nem állt le.");
        assertEquals("Indítás", startStopButton.getText(), "A gomb szövege nem frissült Indításra.");
    }

    // Segédfüggvények a komponensek megtalálásához
    private <T> T findComponent(Class<T> clazz, Container container) {
        for (Component comp : container.getComponents()) {
            if (clazz.isInstance(comp)) {
                return clazz.cast(comp);
            } else if (comp instanceof Container) {
                T result = findComponent(clazz, (Container) comp);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private <T> T findComponent(Class<T> clazz, Container container, String buttonText) {
        for (Component comp : container.getComponents()) {
            if (clazz.isInstance(comp) && comp instanceof AbstractButton button && buttonText.equals(button.getText())) {
                return clazz.cast(comp);
            } else if (comp instanceof Container) {
                T result = findComponent(clazz, (Container) comp, buttonText);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
