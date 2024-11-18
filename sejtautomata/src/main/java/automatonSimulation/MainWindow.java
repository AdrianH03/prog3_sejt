package automatonSimulation;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;

public class MainWindow extends JFrame{
    public MainWindow(){
        //Sejtautomata modelljének inicializálása
        CellularAutomaton automaton = new CellularAutomaton(30, 30);

        //Az ablak beállításai
        setTitle("Sejtautomata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //A panelek hozzáadása
        MatrixPanel matrixPanel = new MatrixPanel(automaton);
        add(matrixPanel, BorderLayout.CENTER);

        ControlPanel controlPanel = new ControlPanel(automaton, this);
        add(controlPanel, BorderLayout.EAST);

        //Egyéb ablak beállítások beállítása
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void saveMatrixToFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), automaton.getMatrix());
            JOptionPane.showMessageDialog(this, "Mátrix mentése sikeres!", "Mentés", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Hiba történt a mentés során: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadMatrixFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            boolean[][] loadedMatrix = mapper.readValue(new File(filePath), boolean[][].class);
            // Ellenőrzés, hogy a fájlban lévő mátrix mérete egyezik-e
            if (loadedMatrix.length != automaton.getMatrix().length || loadedMatrix[0].length != automaton.getMatrix()[0].length) {
                JOptionPane.showMessageDialog(this, "A fájlban lévő mátrix mérete nem megfelelő!", "Hiba", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // A mátrix állapotának frissítése
            for (int row = 0; row < loadedMatrix.length; row++) {
                for (int col = 0; col < loadedMatrix[row].length; col++) {
                    automaton.setCellState(row, col, loadedMatrix[row][col]);
                }
            }
            JOptionPane.showMessageDialog(this, "Mátrix betöltése sikeres!", "Betöltés", JOptionPane.INFORMATION_MESSAGE);
            repaint(); // A mátrix megjelenítésének frissítése
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Hiba történt a betöltés során: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
