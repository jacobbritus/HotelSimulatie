package facility;
import enums.FacilityType;
import simulation.HotelEventManager;

import javax.swing.*;


public class Stairs extends Facility {
    public Stairs(JPanel superPanel, FacilityType type, int row, int column, HotelEventManager simC) {
        super(superPanel, type, row, column, simC);
    }
}

