package automatonSimulation;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.util.List;

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

    //Fut-e a szimuláció
    public boolean isRunning(){
        return isSimulationRunning;
    }

    //Milyen időközönként történjen a frissítés (Sebesség csúszka)
    public void updateTimerDelay(int delay){
        simulationTimer.setDelay(delay);
    }

    //Mátrix JSON fájlba mentése
    public void saveMatrixToFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), automaton.getMatrix());
            JOptionPane.showMessageDialog(this, "Mátrix mentése sikeres!", "Mentés", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Hiba történt a mentés során: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Mátrix betöltése JSON fájlból
    public void loadMatrixFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            boolean[][] loadedMatrix = mapper.readValue(new File(filePath), boolean[][].class);
            // Ellenőrzés, hogy a fájlban lévő mátrix mérete egyezik-e
            if (loadedMatrix.length != automaton.getMatrix().size() || loadedMatrix[0].length != automaton.getMatrix().get(0).size()) {
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


    //Ki van-e választva legalább egy cella
    private boolean isAnyCellSelected(){
        List<List<Boolean>> matrix = automaton.getMatrix();
        for (List<Boolean> cells : matrix) {
            for (boolean cell : cells) {
                if (cell) {
                    return true;
                }
            }
        }
        return false;
    }

    //Mátrix panelének lekérdezése
    public MatrixPanel getMatrixPanel(){
        return matrixPanel;
    }

    //A sebesség lekérdezése
    public int getTimerDelay() {
        return simulationTimer.getDelay();
    }

    //Le van-e mentve a mátrix
    public boolean isMatrixSaved(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        try {
            boolean[][] savedMatrix = mapper.readValue(file, boolean[][].class);
            List<List<Boolean>> currentMatrix = automaton.getMatrix();

            // Ellenőrizzük, hogy a fájlban lévő mátrix mérete és tartalma egyezik-e az aktuális mátrixszal
            if (savedMatrix.length != currentMatrix.size() || savedMatrix[0].length != currentMatrix.get(0).size()) {
                return false;
            }

            for (int row = 0; row < savedMatrix.length; row++) {
                for (int col = 0; col < savedMatrix[row].length; col++) {
                    if (savedMatrix[row][col] != currentMatrix.get(row).get(col)) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //Megfelelően be van-e töltve a mátrix
    public boolean isMatrixLoaded(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        try {
            boolean[][] loadedMatrix = mapper.readValue(file, boolean[][].class);
            List<List<Boolean>> currentMatrix = automaton.getMatrix();

            // Ellenőrizzük, hogy a betöltött mátrix mérete megegyezik-e az aktuálissal
            if (loadedMatrix.length != currentMatrix.size() || loadedMatrix[0].length != currentMatrix.get(0).size()) {
                return false;
            }

            // Ellenőrizzük, hogy a betöltött mátrix tartalma megegyezik-e az aktuálissal
            for (int row = 0; row < loadedMatrix.length; row++) {
                for (int col = 0; col < loadedMatrix[row].length; col++) {
                    if (loadedMatrix[row][col] != currentMatrix.get(row).get(col)) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
