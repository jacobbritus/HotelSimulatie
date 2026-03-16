package facility;

import enums.FacilityType;
import simulation.SimulationController;

import javax.swing.*;
import java.awt.*;

public class Hall extends Facility {
    public Hall(JPanel superPanel, FacilityType type, int row, int column, SimulationController simC) {
        super(superPanel, type, row, column, simC);
    }
}