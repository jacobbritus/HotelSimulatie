package facility;
import enums.FacilityType;
import simulation.Simulation;
import simulation.SimulationController;

import javax.swing.*;


public class Stairs extends Facility {
    public Stairs(JPanel superPanel, FacilityType type, int row, int column, SimulationController simC) {
        super(superPanel, type, row, column, simC);
    }
}

