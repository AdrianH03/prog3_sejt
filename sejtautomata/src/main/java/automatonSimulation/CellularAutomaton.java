package automatonSimulation;

import rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class CellularAutomaton {
    private List<List<Boolean>> matrix; // Sejtek állapota
    private Rule currentRule; // Aktuális szabály

    // Mátrix inicializálása
    public CellularAutomaton(int rows, int cols) {
        matrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Boolean> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(false); // Minden sejt alapértelmezetten halott
            }
            matrix.add(row);
        }
    }

    // Mátrix frissítése az aktuális szabály alapján
    public void update() {
        if (currentRule == null) {
            throw new IllegalStateException("Nincs szabály beállítva");
        }

        List<List<Boolean>> newMatrix = new ArrayList<>();
        for (int row = 0; row < matrix.size(); row++) {
            List<Boolean> newRow = new ArrayList<>();
            for (int col = 0; col < matrix.get(row).size(); col++) {
                boolean newState = currentRule.apply(matrix, row, col);
                newRow.add(newState);
            }
            newMatrix.add(newRow);
        }
        matrix = newMatrix;
    }

    // Egy cella állapotának a lekérdezése
    public boolean getCellState(int row, int col) {
        validateCell(row, col);
        return matrix.get(row).get(col);
    }

    // Egy cella állapotának a módosítása
    public void setCellState(int row, int col, boolean state) {
        validateCell(row, col);
        matrix.get(row).set(col, state);
    }

    // Mátrix lekérdezése
    public List<List<Boolean>> getMatrix() {
        return matrix;
    }

    // Adott szabály beállítása
    public void setRule(Rule rule) {
        this.currentRule = rule;
    }

    // Mátrix lenullázása, minden sejt halottnak nyilvánítása
    public void reset() {
        for (List<Boolean> row : matrix) {
            for (int col = 0; col < row.size(); col++) {
                row.set(col, false);
            }
        }
    }

    // Érvényes-e a cellakoordináták
    private void validateCell(int row, int col) {
        if (row < 0 || row >= matrix.size() || col < 0 || col >= matrix.get(row).size()) {
            throw new IndexOutOfBoundsException("A (" + row + "," + col + ") cella nem létezik!");
        }
    }

    // Kiválasztott szabály lekérdezése
    public Rule getRule() {
        return currentRule;
    }
}
