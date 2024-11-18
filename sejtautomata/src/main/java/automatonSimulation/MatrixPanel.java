package automatonSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatrixPanel extends JPanel{
    private CellularAutomaton automaton;
    // Cella mérete pixelben
    private final int cellSize = 20;

    public MatrixPanel(CellularAutomaton automaton){
        this.automaton = automaton;

        //Egér kattintás figyelése
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int col = e.getX() / cellSize; //Oszlop kiszámítása az X koordináta és a cellaméret alapján
                int row = e.getY() / cellSize; //Sor kiszámítása

                //Cellatartomány ellenőrzése
                if(row >= 0 && row < automaton.getMatrix().length && col >= 0 && col < automaton.getMatrix()[0].length){
                    automaton.setCellState(row, col, !automaton.getCellState(row, col)); //Állapot váltás
                    repaint(); //Újrarajzolás
                }
            }
        });

        setPreferredSize(new Dimension(cellSize * automaton.getMatrix()[0].length, cellSize * automaton.getMatrix().length));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //Mátrix rajzolása
        boolean[][] matrix = automaton.getMatrix();
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                //cellák színezése az állapot alapján
                if(matrix[row][col]){
                    g.setColor(Color.BLACK);
                }else{
                    g.setColor(Color.WHITE);
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                //Rácsvonalak
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
}
