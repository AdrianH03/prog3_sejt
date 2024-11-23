package automatonSimulation;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;

public class MainWindow extends JFrame{
    //Sejtautomata modelljének inicializálása
    private final CellularAutomaton automaton = new CellularAutomaton(30, 30);
    private final Timer simulationTimer;
    private final MatrixPanel matrixPanel;
    private final ControlPanel controlPanel;
    private boolean isSimulationRunning = false;

    //Konstruktor, panelek, alapinicializálás
    public MainWindow(){

        //Az ablak beállításai
        setTitle("Sejtautomata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //A panelek hozzáadása
        matrixPanel = new MatrixPanel(automaton);
        add(matrixPanel, BorderLayout.CENTER);
        controlPanel = new ControlPanel(automaton, this);
        add(controlPanel, BorderLayout.EAST);

        //Időzítő inicializálása
        simulationTimer = new Timer(500, _ -> runSimulationStep());
        simulationTimer.setRepeats(true);

        //Egyéb ablak beállítások beállítása
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Mátrix frissítése és újrarajzolás
    private void runSimulationStep(){
        automaton.update();
        matrixPanel.repaint();
    }

    //Szimuláció elindítása
    public void startSimulation(){
        if(!isAnyCellSelected()){
            //Figyelmezetető ablak, ha nincs kijelölés
            JOptionPane.showMessageDialog(this, "Nem lehet elindítani a szimulációt, mert nincs élő sejt!", "Figyelmeztetés",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!isSimulationRunning){
            simulationTimer.start();
            isSimulationRunning = true;
            controlPanel.updateStartButtonText("Leállítás");
        }
    }

    //Szimuláció leállítása
    public void stopSimulation(){
        if(isSimulationRunning){
            simulationTimer.stop();
            isSimulationRunning = false;
            controlPanel.updateStartButtonText("Indítás");
        }
    }

    public boolean isRunning(){
        return isSimulationRunning;
    }

    public void updateTimerDelay(int delay){
        simulationTimer.setDelay(delay);
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

    private boolean isAnyCellSelected(){
        boolean[][] matrix = automaton.getMatrix();
        for (boolean[] cells : matrix) {
            for (boolean cell : cells) {
                if (cell) {
                    return true;
                }
            }
        }
        return false;
    }

    public MatrixPanel getMatrixPanel(){
        return matrixPanel;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
