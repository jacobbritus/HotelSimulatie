package human;

import facility.Facility;
import facility.Room;
import layout.Layout;

import java.util.ArrayList;
import java.util.Collections;

public interface RoomOccupant {
    public void assignRoom(Room room);
    public void removeRoom(Room room);
    public boolean roomFilter(Room room);



}
