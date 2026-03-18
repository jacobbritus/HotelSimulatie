package facility;

import enums.FacilityType;
import simulation.HotelEventManager;

import javax.swing.*;

public class Hall extends Facility {
    public Hall(JPanel superPanel, FacilityType type, int row, int column, HotelEventManager simC) {
        super(superPanel, type, row, column, simC);
    }


}