package facility;
import simulation.SimulationController;

import javax.swing.*;

public class Lobby extends Facility {
    public Lobby(JPanel superPanel, Facility[][] ruimtes, int row, int column, SimulationController simC) {
        super(superPanel, ruimtes, row, column, simC);
    }

}
