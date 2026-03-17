package facility;

import enums.FacilityType;
import human.Human;
import simulation.SimulationController;

import javax.swing.*;

public class Lift extends Facility {
    public Lift(JPanel superPanel, FacilityType type, int row, int column, SimulationController simC) {
        super(superPanel, type, row, column, simC);
    }

    @Override
    public boolean isAccessible(Human human) {
        return human.getDestination().getFacility().getLevel() != human.getTile().getFacility().getLevel();
    }

}
