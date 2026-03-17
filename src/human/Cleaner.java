package human;

import enums.Role;
import enums.RoomStatus;
import events.HotelEvent;
import facility.Room;
import facility.Tile;
import layout.Layout;

import java.awt.*;

public class Cleaner extends Human {
    private int roomsCleaned;

    public Cleaner(Tile tile, Layout layout) {
        super(tile, layout, Role.CLEANER);
        this.getTile().setBackground(Color.BLUE);
        this.roomsCleaned = 0;
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        super.setTile(newTile, Color.BLUE);
    }

    public int getRoomsCleaned() {
        return roomsCleaned;
    }


    @Override
    public boolean moveFilter(Tile neighbour) {
        return false;
    }

    @Override
    public void assignRoom(Room room) {
        this.setAssignedRoom(room);
        room.setOccupant(this, RoomStatus.CLEANING);
    }

    @Override
    public void removeRoom(Room room) {
        this.setAssignedRoom(null);
        room.removeOccupant(RoomStatus.AVAILABLE);
        this.getTile().setBackground(Color.BLUE);
        this.roomsCleaned++;
    }

    @Override
    public boolean roomFilter(Room room) {
        return room.getStatus() == RoomStatus.DIRTY;
    }

    @Override
    public void notify(HotelEvent hotelEvent) {

    }
}


