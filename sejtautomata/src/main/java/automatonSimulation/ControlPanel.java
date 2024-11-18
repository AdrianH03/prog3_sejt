package automatonSimulation;

import javax.swing.*;

public class ControlPanel extends JPanel{
    private CellularAutomaton automaton;
    private MainWindow mainWindow;

    public ControlPanel(CellularAutomaton automaton, MainWindow mainWindow){
        this.automaton = automaton;
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Szabályválasztó rész
        JComboBox<String> ruleSelector = new JComboBox<>(new String[]{"Game of Life", "Replicator", "Seeds"});
        ruleSelector.addActionListener(e -> {
            String selectedRule = (String) ruleSelector.getSelectedItem();
            automaton.setRule(createRule(selectedRule));
        });
        add(new JLabel("Szabály kiválasztása"));
        add(ruleSelector);

        //Sebesség csúszka
        //Minimum 1 maximum 10-es érték, kezdő érték pedig 5
        JSlider speedSlider = new JSlider(1,10,5);
        add(new JLabel("Sebesség:"));
        add(speedSlider);

        //Gombok
        JPanel buttonPanel = new JPanel();
        JButton loadButton = new JButton("Betöltés");
        JButton saveButton = new JButton("Mentés");

        //Mentés gomb
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                mainWindow.saveMatrixToFile(filePath); //Mátrix mentése egy fájlba
            }
        });

        //Betöltés gomb
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                mainWindow.loadMatrixFromFile(filePath);
                mainWindow.repaint();
            }
        });

        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel);
    }

    private Rule createRule(String ruleName){
        switch(ruleName){
            case "Game of Life":
                return new rules.GameOfLifeRule();
            case "Replicator":
                return new rules.ReplicatorRule();
            case "Seeds":
                return new rules.SeedsRule();
            default:
                throw new IllegalArgumentException("Ismeretlen szabály!");
        }
    }
}