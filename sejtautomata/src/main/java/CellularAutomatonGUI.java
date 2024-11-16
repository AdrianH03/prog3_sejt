import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellularAutomatonGUI extends JFrame {
    private final boolean[][] matrix; // A sejtek állapotát tároló mátrix
    private final JPanel matrixPanel;
    private final int cellSize = 20; // Egy cella mérete (pixelben)

    public CellularAutomatonGUI() {
        setTitle("Sejtautomata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Mátrix inicializálása
        int rows = 30; // Sorok száma
        int cols = 30; // Oszlopok száma
        matrix = new boolean[rows][cols]; // Mátrix méretének beállítása

        // Mátrix panel létrehozása
        matrixPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMatrix(g);
            }
        };
        matrixPanel.setPreferredSize(new Dimension(cellSize * cols, cellSize * rows));
        add(matrixPanel, BorderLayout.CENTER);

        // Egérkattintás kezelése
        matrixPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;

                if (row >= 0 && row < matrix.length && col >= 0 && col < matrix[row].length) {
                    matrix[row][col] = !matrix[row][col]; // Állapot váltása
                    matrixPanel.repaint(); // Panel újrarajzolása
                }
            }
        });

        // Jobb oldali vezérlőpanel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Szabályválasztó lista
        JComboBox<String> ruleSelector = new JComboBox<>(new String[]{"Game of Life", "Replicator", "Seeds"});
        controlPanel.add(new JLabel("Szabály kiválasztása:"));
        controlPanel.add(ruleSelector);

        // Sebesség csúszka
        JSlider speedSlider = new JSlider(1, 10, 5);
        controlPanel.add(new JLabel("Sebesség:"));
        controlPanel.add(speedSlider);

        // Gombok panel (Mentés és Betöltés)
        JPanel buttonPanel = new JPanel();
        JButton loadButton = new JButton("Betöltés");
        JButton saveButton = new JButton("Mentés");
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        controlPanel.add(buttonPanel);

        add(controlPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void drawMatrix(Graphics g) {
        // Mátrix rajzolása
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col]) {
                    g.setColor(Color.BLACK); // Élő sejt
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(Color.WHITE); // Halott sejt
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
                g.setColor(Color.LIGHT_GRAY); // Rácsvonalak
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CellularAutomatonGUI::new);
    }
}
