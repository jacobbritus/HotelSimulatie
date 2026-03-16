package human;

import enums.FacilityType;
import enums.RoomStatus;
import facility.Room;
import facility.Tile;
import layout.Layout;
import settings.Settings;

import java.awt.*;


// Status CHECKEDIN, ARRIVED, CHECKINGIN, LEAVING, CHECKINGOUT
public class Guest extends Human {
    private boolean isCheckedIn;
    private Room room;
    private final Color arrivalColor = Color.RED;
    private final Color checkedInColor = Color.GREEN;

    public Guest(Tile tile) {
        super(tile);
        this.isCheckedIn = false;
        this.room = null;
        this.getTile().setBackground(arrivalColor);
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        if (this.isCheckedIn) color = checkedInColor;
        else color = arrivalColor;
        super.setTile(newTile, color);
    }

    public boolean getIsCheckedIn() {
        return this.isCheckedIn;
    }


    @Override
    public void decisionMaking(Layout layout) {
        // No room availability, walk around randomly in lobby. Maybe leave and note statistics (start lifetime and if they leave, add statistic)
        if (!this.isCheckedIn || this.getLifeTime() == null || this.getLifeTime() <= 0) { // Check in at lobby, find a room
//             this.setCooldown(settings.Settings.ticks * 100);
            if ((this.getTile().getFacility().getType() == FacilityType.LOBBY)) {
                if (this.getLifeTime() == null) {
                    Room nearestRoom = layout.getNearestRoom(this); // assign room
                    if (nearestRoom == null) {
                        return;
                    }
                    this.assignRoom(nearestRoom);
                }
                else this.checkOut(layout);
            }
            setDestination(layout.getRandomTile(layout.getLobbies().getFirst()));
        } else { // Walk within room for now.
            setDestination(layout.getRandomTile(this.room));
        }
    }

    @Override
    public boolean moveFilter(Tile neighbour) {
        return false;
    }

    public void checkOut(Layout layout) {
        if (this.room != null) this.removeRoom(this.room);

        this.setIsLeaving();
        this.isCheckedIn = false;
    }

    @Override
    public void assignRoom(Room room) {
        this.room = room;

        room.setOccupant(this, RoomStatus.UNAVAILABLE);
        // room state == OCCUPIED



//        room.setGuest(this);

        // Stop at lobby for a moment and check in
//        this.setCooldown(settings.Settings.ticks * 20);  // Get an actual formula
        this.setCooldown(Settings.guestBaseCheckInTime);
        this.isCheckedIn = true;
        this.setLifeTime(Settings.guestBaseStayTime);
    }

    @Override
    public void removeRoom(Room room) {
        this.room = null;
        room.removeOccupant(RoomStatus.DIRTY);
    }

    @Override
    public boolean roomFilter(Room room) {
        return (room.getStatus() == RoomStatus.AVAILABLE);
    }

}
