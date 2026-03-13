import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Applicatie extends JFrame implements KeyListener {

    Simulation simulation;

    public Applicatie() {
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
    }

    public void startSimulatie(String[][] rauweGrid) {


        simulation = new Simulation(rauweGrid);

        SimulationController simulationController = new SimulationController(simulation);
        simulation.setSimulationController(simulationController);

        JScrollPane scrollPane = new JScrollPane(simulation);

        // Change scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(5);

        scrollPane.setBackground(Settings.achtergrondKleur);
        scrollPane.setBorder(null); // It has a border for some reason

        this.getContentPane().add(scrollPane);

        // Put the controller (Top bar UI) at the top of the screen
        scrollPane.setColumnHeaderView(simulationController);


        // This is done to avoid empty spots around the border
        JPanel cornerTopRight = new JPanel();
        cornerTopRight.setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, cornerTopRight);

        JPanel cornerBottomRight = new JPanel();
        cornerTopRight.setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerBottomRight);




        this.setVisible(true);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            simulation.zoom(Settings.facilityTilesSize);
        } else if (e.getKeyChar() == '-') {
            simulation.zoom(Settings.facilityTilesSize * -1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override public void keyTyped(KeyEvent e) {}
}