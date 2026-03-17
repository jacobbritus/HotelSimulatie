import helper.MyScrollPane;
import settings.FacilityColors;
import settings.Settings;
import simulation.Simulation;
import simulation.SimulationController;
import simulation.SimulationSidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Applicatie extends JFrame implements KeyListener {
    Simulation simulation;
    boolean sidebarVisible;

    public Applicatie() {
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);

        this.setBackground(Color.BLACK);
        FacilityColors.setup();

    }

    public void startSimulatie(String[][] rauweGrid) {
        simulation = new Simulation(rauweGrid);
        sidebarVisible = true;

        this.setLayout(new BorderLayout());


        JScrollPane scrollPane = new MyScrollPane(simulation);

        this.getContentPane().add(scrollPane);

        // Put the controller (Top bar UI) at the top of the screen
//        scrollPane.setColumnHeaderView(simulationController);



        SimulationSidebar simulationSidebar = new SimulationSidebar();
        simulation.setSimulationSidebar(simulationSidebar);

        SimulationController simulationController = new SimulationController(simulation, simulationSidebar);
        simulation.setSimulationController(simulationController);
        this.add(simulationController, BorderLayout.NORTH);

        simulationSidebar.setSimulationController(simulationController);
        this.add(simulationSidebar, BorderLayout.WEST);
        this.setVisible(true);
        
        simulation.init();

        simulation.zoom(Settings.facilityTilesSize*  2);
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