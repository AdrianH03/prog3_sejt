package automatonSimulation;

import java.util.Arrays;

public class CellularAutomaton {
    private boolean[][] matrix; //Sejtek állapota
    private Rule currentRule; //Aktuális szabály
    //Mátrix inicializálása
    public CellularAutomaton(int rows, int cols){
        matrix = new boolean[rows][cols];
        reset();
    }

    //Mátrix frissítése az aktuális szabály alapján
    public void update(){
        if(currentRule == null){
            throw new IllegalStateException("Nincs szabály beállítva");
        }

        boolean[][] newMatrix = new boolean[matrix.length][matrix[0].length];
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                newMatrix[row][col] = currentRule.apply(matrix, row, col);
            }
        }
        matrix = newMatrix;
    }

    //Egy cella állapotának a lekérdezése
    public boolean getCellState(int row, int col){
        validateCell(row, col);
        return matrix[row][col];
    }

    //Egy cella állapotának a módosítása
    public void setCellState(int row, int col, boolean state){
        validateCell(row, col);
        matrix[row][col] = state;
    }

    //Mátrix lekérdezése
    public boolean[][] getMatrix(){
        return matrix;
    }

    //Adott szabály beállítása
    public void setRule(Rule rule){
        this.currentRule = rule;
    }

    //Mátrix lenullázása, minden sejt halottnak nyílvánítása
    public void reset(){
        for (boolean[] booleans : matrix) {
            Arrays.fill(booleans, false);
        }
    }

    //Érvényes-e a cellakoordináták
    private void validateCell(int row, int col){
        if(row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length){
            throw new IndexOutOfBoundsException("A (" + row + "," + col + ") cella nem létezik!");
        }
    }
}
