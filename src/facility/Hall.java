package facility;

import simulation.SimulationController;

import javax.swing.*;
import java.awt.*;

public class Hall extends Facility {
    public Hall(JPanel superPanel, Facility[][] ruimtes, int row, int column, SimulationController simC) {
        super(superPanel, ruimtes, row, column, simC);
    }
}