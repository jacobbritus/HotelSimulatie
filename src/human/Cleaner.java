package human;

import enums.RoomStatus;
import facility.Room;
import facility.Tile;
import layout.Layout;
import settings.Settings;

import java.awt.*;

public class Cleaner extends Human {
    private Room room;
    private int roomsCleaned;

    public Cleaner(Tile tile) {
        super(tile);
        this.getTile().setBackground(Color.BLUE);
        this.room = null;
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
    public void decisionMaking(Layout layout) {
        if (this.room == null) {
//                setCooldown(100);
            Room nearestRoom = layout.getNearestRoom(this); // assign room
            if (nearestRoom != null && nearestRoom.getStatus() == RoomStatus.DIRTY) {
                assignRoom(nearestRoom);
            }
            setDestination(layout.getRandomTile(this.room));


        } else if (this.getTile().getFacility() == this.room) {
            if (this.getActionTime() == null) {
                this.setActionTime(Settings.cleanerBaseCleaningTime);
                this.setDestination(layout.getRandomTile(this.room));
            }

            this.decreaseActionTime();

            if (this.getActionTime() <= 0) {
                this.removeRoom(this.room);
                this.setActionTime(null);
            }

        } else this.setDestination(layout.getRandomTile(this.room));
    }

    @Override
    public boolean moveFilter(Tile neighbour) {
        return false;
    }

    @Override
    public void assignRoom(Room room) {
        this.room = room;
        room.setOccupant(this, RoomStatus.CLEANING);
    }

    @Override
    public void removeRoom(Room room) {
        this.room = null;
        room.removeOccupant(RoomStatus.AVAILABLE);
        this.getTile().setBackground(Color.BLUE);
        this.roomsCleaned++;
    }

    @Override
    public boolean roomFilter(Room room) {
        return room.getStatus() == RoomStatus.DIRTY;
    }
}


