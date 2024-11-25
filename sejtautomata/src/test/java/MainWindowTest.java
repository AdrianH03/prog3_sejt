import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import automatonSimulation.MainWindow;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

class MainWindowTest {

    @Test
    void testStartSimulationWithoutAnyCellSelected() {
        MainWindow mainWindow = new MainWindow();

        // A mátrix minden cellája halott
        mainWindow.startSimulation();

        // Ellenőrizzük, hogy a szimuláció nem futott el
        assertFalse(mainWindow.isRunning(), "A szimulációnak nem szabadott volna elindulnia.");
    }

    @Test
    void testStartSimulationWithCellsSelected() {
        MainWindow mainWindow = new MainWindow();

        // Betöltünk egy működő mátrixot
        mainWindow.loadMatrixFromFile("src/main/resources/valid_matrix.json");

        // Indítsuk el a szimulációt
        mainWindow.startSimulation();

        // Ellenőrizzük, hogy a szimuláció fut-e
        assertTrue(mainWindow.isRunning(), "A szimulációnak el kellett volna indulnia.");

        mainWindow.stopSimulation();
        assertFalse(mainWindow.isRunning(), "A szimulációnak le kellett volna állnia.");
    }
    @Test
    void testStopSimulation() {
        MainWindow mainWindow = new MainWindow();

        // Állítsuk be, hogy a szimuláció fut
        mainWindow.startSimulation();
        assertFalse(mainWindow.isRunning(), "A szimulációnak futnia kellene.");

        // Állítsuk le a szimulációt
        mainWindow.stopSimulation();
        assertFalse(mainWindow.isRunning(), "A szimulációnak le kellett volna állnia.");
    }
    @Test
    void testIsMatrixSavedFileDoesNotExist() {
        MainWindow mainWindow = new MainWindow();
        String nonExistentFilePath = "non_existent_file.json";

        assertFalse(mainWindow.isMatrixSaved(nonExistentFilePath), "A mátrix nem létező fájl esetén nem lehet mentett.");
    }

    @Test
    void testIsMatrixSavedInvalidMatrixSize() {
        MainWindow mainWindow = new MainWindow();
        String invalidFilePath = "invalid_matrix_size.json";

        // Mentünk egy mátrixot más mérettel
        boolean[][] invalidMatrix = {{true, false}, {false, true}};
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(invalidFilePath), invalidMatrix);
        } catch (IOException e) {
            fail("Nem sikerült létrehozni az érvénytelen méretű fájlt.");
        }

        assertFalse(mainWindow.isMatrixSaved(invalidFilePath), "A mátrix méretének eltérése esetén nem lehet mentett.");
    }

    @Test
    void testIsMatrixSavedValidMatrix() {
        MainWindow mainWindow = new MainWindow();
        String validFilePath = "valid_matrix.json";

        // Mentjük a mátrixot
        mainWindow.saveMatrixToFile(validFilePath);

        assertTrue(mainWindow.isMatrixSaved(validFilePath), "A helyesen mentett mátrixnak mentettként kellene jelennie.");
    }
    @Test
    void testIsMatrixLoadedFileDoesNotExist() {
        MainWindow mainWindow = new MainWindow();
        String nonExistentFilePath = "non_existent_file.json";

        assertFalse(mainWindow.isMatrixLoaded(nonExistentFilePath), "A mátrix nem létező fájl esetén nem lehet betöltve.");
    }

    @Test
    void testIsMatrixLoadedInvalidContent() {
        MainWindow mainWindow = new MainWindow();
        String invalidFilePath = "invalid_matrix.json";

        // Létrehozunk egy hibás fájlt
        try {
            File file = new File(invalidFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            fail("Nem sikerült létrehozni a hibás fájlt.");
        }

        assertFalse(mainWindow.isMatrixLoaded(invalidFilePath), "Hibás fájl tartalom esetén a mátrix nem lehet betöltve.");
    }

    @Test
    void testIsMatrixLoadedValidMatrix() {
        MainWindow mainWindow = new MainWindow();
        String validFilePath = "valid_matrix.json";

        // Mentjük a mátrixot
        mainWindow.saveMatrixToFile(validFilePath);

        // Betöltjük a mátrixot
        assertTrue(mainWindow.isMatrixLoaded(validFilePath), "A helyesen mentett mátrixot betöltöttnek kellene jelölni.");
    }
}
