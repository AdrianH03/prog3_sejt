package automatonSimulation;
import rules.Rule;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public class ControlPanel extends JPanel{
    private final JButton startStopButton;
    public ControlPanel(CellularAutomaton automaton, MainWindow mainWindow){
        //Alapok
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Szabályválasztó rész
        JPanel rulePanel = new JPanel();
        rulePanel.setLayout(new BoxLayout(rulePanel, BoxLayout.Y_AXIS));
        rulePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> ruleSelector = new JComboBox<>(new String[]{"Game of Life", "Replicator", "Seeds"});
        ruleSelector.addActionListener(_ -> {
            String selectedRule = (String) ruleSelector.getSelectedItem();
            assert selectedRule != null;
            automaton.setRule(createRule(selectedRule));
        });
        JLabel ruleLabel = new JLabel("Szabály kiválasztása:");

        //Alapértelmezett szabály beállítása
        String defaultRule = (String) ruleSelector.getSelectedItem();
        assert defaultRule != null;
        automaton.setRule(createRule(defaultRule));

        // Méretezés és igazítás beállítása
        ruleSelector.setMaximumSize(new Dimension(150, 25));
        ruleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ruleSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulePanel.add(ruleLabel);
        rulePanel.add(Box.createVerticalStrut(5));
        rulePanel.add(ruleSelector);
        add(rulePanel);

        //Színek kiválasztása
        JPanel colorPickerPanel = new JPanel();
        colorPickerPanel.setLayout(new FlowLayout());
        add(Box.createVerticalStrut(10));
        colorPickerPanel.add(new JLabel("Sejtszín kiválasztása:"));
        JButton colorPickerButton = new JButton("Válassz színt");
        colorPickerButton.addActionListener(_ -> {
            Color selectedColor = JColorChooser.showDialog(this, "Szín kiválasztása", Color.BLACK);
            if(selectedColor != null){
                mainWindow.getMatrixPanel().setCellColor(selectedColor);
                mainWindow.getMatrixPanel().repaint();
            }
        });
        colorPickerPanel.add(colorPickerButton);
        add(Box.createVerticalStrut(5));
        add(colorPickerPanel);

        //Kép beillesztése
        JPanel gifPanel = new JPanel();
        gifPanel.setLayout(new FlowLayout());
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/logo.gif")));
        JLabel gifLabel = new JLabel(originalIcon);
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gifPanel.add(Box.createVerticalStrut(10)); // Kis távolság
        gifPanel.add(gifLabel);
        add(gifPanel);

        //Sebesség csúszka
        //Minimum 1 maximum 10-es érték, kezdő érték pedig 5
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.Y_AXIS));
        speedPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSlider speedSlider = new JSlider(1,10,5);
        JLabel speedLabel = new JLabel("Sebesség:");
        speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        speedPanel.add(speedLabel);
        speedPanel.add(Box.createVerticalStrut(5));
        speedPanel.add(speedSlider);
        add(speedPanel);

        speedSlider.addChangeListener(_ -> {
            int sliderValue = speedSlider.getValue();
            int delay = 1000 / sliderValue;
            mainWindow.updateTimerDelay(delay);
        });

        //Gombok
        add(Box.createVerticalStrut(10));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loadButton = new JButton("Betöltés");
        JButton saveButton = new JButton("Mentés");

        //Mentés gomb
        saveButton.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                mainWindow.saveMatrixToFile(filePath); //Mátrix mentése egy fájlba
            }
        });

        //Betöltés gomb
        loadButton.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                mainWindow.loadMatrixFromFile(filePath);
                mainWindow.repaint();
            }
        });

        //Indítás/Megállítás gomb
        startStopButton = new JButton("Indítás");
        startStopButton.addActionListener(_ -> {
            if(mainWindow.isRunning()){
                mainWindow.stopSimulation();
            }else{
                mainWindow.startSimulation();
            }
        });
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Panel margója
        buttonPanel.add(startStopButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel);
    }

    public void updateStartButtonText(String text){
        startStopButton.setText(text);
    }

    private Rule createRule(String ruleName){
        return switch (ruleName) {
            case "Game of Life" -> new rules.GameOfLifeRule();
            case "Replicator" -> new rules.ReplicatorRule();
            case "Seeds" -> new rules.SeedsRule();
            default -> throw new IllegalArgumentException("Ismeretlen szabály!");
        };
    }
}
