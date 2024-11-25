package automatonSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MatrixPanel extends JPanel{
    private final CellularAutomaton automaton;
    private Color cellColor = Color.BLACK;
    private final int cellSize = 20;

    public MatrixPanel(CellularAutomaton automaton){
        this.automaton = automaton;

        //Egér kattintás figyelése
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;

                //Cellatartomány ellenőrzése
                if(row >= 0 && row < automaton.getMatrix().size() && col >= 0 && col < automaton.getMatrix().get(0).size()){
                    automaton.setCellState(row, col, !automaton.getCellState(row, col));
                    repaint();
                }
            }
        });
        setPreferredSize(new Dimension(cellSize * automaton.getMatrix().get(0).size(), cellSize * automaton.getMatrix().size()));
    }

    //Mátrix rajzolása
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        List<List<Boolean>> matrix = automaton.getMatrix();
        for(int row = 0; row < matrix.size(); row++){
            for(int col = 0; col < matrix.get(row).size(); col++){
                //cellák színezése az állapot alapján
                if(matrix.get(row).get(col)){
                    g.setColor(cellColor);
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

    //Cellák színének lekérdezése
    public Color getCellColor() {
        return cellColor;
    }

    //Cellák színének beállítása
    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }
}
