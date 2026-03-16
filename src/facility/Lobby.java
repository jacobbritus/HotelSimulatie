package facility;
import enums.FacilityType;
import human.Human;
import simulation.SimulationController;

import javax.swing.*;

public class Lobby extends Facility {
    public Lobby(JPanel superPanel, FacilityType type, int row, int column, SimulationController simC) {
        super(superPanel, type, row, column, simC);
    }

//    @Override
//    public void onInteract(Human human) {
//        if ((human.getTile().getFacility() == this)) {
//            if (human.getLifeTime() == null) human.assignRoom(layout);
//            else human.checkOut(layout);
//        }
//    }

}
