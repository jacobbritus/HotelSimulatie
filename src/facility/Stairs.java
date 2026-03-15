package facility;
import simulation.Simulation;
import simulation.SimulationController;

import javax.swing.*;


public class Stairs extends Facility {
    public Stairs(JPanel superPanel, Facility[][] ruimtes, int row, int column, SimulationController simC) {
        super(superPanel, ruimtes, row, column, simC);
    }
}

