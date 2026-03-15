package facility;

import simulation.SimulationController;

import javax.swing.*;

public class Lift extends Facility {
    public Lift(JPanel superPanel, Facility[][] ruimtes, int row, int column, SimulationController simC) {
        super(superPanel, ruimtes, row, column, simC);
    }

}
