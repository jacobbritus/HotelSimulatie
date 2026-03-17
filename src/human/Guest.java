package human;

import enums.Role;
import enums.RoomStatus;
import events.HotelEvent;
import events.HotelEventType;
import facility.Room;
import facility.Tile;
import layout.Layout;

import java.awt.*;


public class Guest extends Human {
    private boolean isCheckedIn;
    private final Color arrivalColor = Color.RED;
    private final Color checkedInColor = Color.GREEN;
    private int guestId;

    public Guest(Tile tile, Layout layout) {
        super(tile, layout, Role.GUEST);
        this.isCheckedIn = false;
        this.getTile().setBackground(arrivalColor);
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        if (this.isCheckedIn) color = checkedInColor;
        else color = arrivalColor;
        super.setTile(newTile, color);
    }


    @Override
    public boolean moveFilter(Tile neighbour) {
        return false;
    }

    public void checkOut(Layout layout) {
        if (this.getAssignedRoom() != null) this.removeRoom(this.getAssignedRoom());
        this.isCheckedIn = false;
    }

    @Override
    public void assignRoom(Room room) {
        this.setAssignedRoom(room);
        room.setOccupant(this, RoomStatus.UNAVAILABLE);
        // Stop at lobby for a moment and check in
//        this.setCooldown(settings.Settings.ticks * 20);  // Get an actual formula
        this.isCheckedIn = true;
    }

    @Override
    public void removeRoom(Room room) {
        this.setAssignedRoom(null);
        room.removeOccupant(RoomStatus.DIRTY);
    }

    @Override
    public boolean roomFilter(Room room) {
        return (room.getStatus() == RoomStatus.AVAILABLE);
    }

    @Override
    public void notify(HotelEvent hotelEvent) {
        if (hotelEvent.getGuestId() != null && hotelEvent.getGuestId() != this.guestId) return;

        System.out.println(hotelEvent.getEventType());
        System.out.println(this.guestId);
        System.out.println(hotelEvent.getGuestId());

        System.out.println();


//        System.out.println(hotelEvent.getEventType());
//        if (hotelEvent.getEventType() == HotelEventType.GO_ROOM) {
//        }
        switch (hotelEvent.getEventType()) {
            case ASSIGN_ROOM -> {
                Room nearestRoom = this.getLayout().getNearestRoom(this); // assign room
                if (nearestRoom == null) {
                    return;
                }
                this.assignRoom(nearestRoom);
            }
            case GO_ROOM -> {
                this.setDestination(this.getLayout().getRandomTile(this.getAssignedRoom()));
                this.bfs(getDestination());
            }
        }
    }
}
