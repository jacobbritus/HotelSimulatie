import enums.FacilityState;
import facility.Facility;
import facility.Room;
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


        SimulationSidebar simulationSidebar = new SimulationSidebar(simulation);
        simulation.setSimulationSidebar(simulationSidebar);

        SimulationController simulationController = new SimulationController(simulation, e -> {
            System.out.println(this.sidebarVisible);
            if (this.sidebarVisible) {
                simulationSidebar.setPreferredSize(new Dimension(0, Settings.schermHoogte));
                this.sidebarVisible = false;
            } else {
                this.sidebarVisible = true;
                simulationSidebar.setPreferredSize(new Dimension(Settings.schermBreedte / 4, Settings.schermHoogte));
            }

            simulationSidebar.revalidate();
            simulationSidebar.repaint();
        });
        simulation.setSimulationController(simulationController);
        this.add(simulationController, BorderLayout.NORTH);

        this.add(simulationSidebar, BorderLayout.WEST);
        this.setVisible(true);

        simulation.zoom(Settings.facilityTilesSize * -1);
    }



    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
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