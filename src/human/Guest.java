package human;

import enums.FacilityType;
import enums.GuestStatus;
import enums.Role;
import enums.RoomStatus;
import events.HotelEvent;
import facility.Facility;
import facility.Room;
import facility.Tile;
import layout.Layout;

import java.awt.*;


public class Guest extends Human {
    private final Color arrivalColor = Color.RED;
    private final Color checkedInColor = Color.GREEN;
    private GuestStatus status;

    public Guest(Tile tile, Layout layout, int id) {
        super(tile, layout, Role.GUEST, id);
        this.getTile().setBackground(arrivalColor);
        this.status = GuestStatus.ARRIVED;
    }



    @Override
    public void setTile(Tile newTile, Color color) {
        if (this.status == GuestStatus.CHECKED_IN) color = checkedInColor;
        else color = arrivalColor;
        super.setTile(newTile, color);
    }

    @Override
    public void onFacilityInteract(Facility facility) {
        if (facility.getType() == FacilityType.LOBBY && this.status == GuestStatus.CHECKING_OUT) {
            this.setReadyToDespawn();
            this.getTile().revertColor();
        }
    }


    @Override
    public boolean moveFilter(Tile neighbour) {
        return false;
    }


    @Override
    public void assignRoom(Room room) {
        this.setAssignedRoom(room);
        room.setOccupant(this, RoomStatus.UNAVAILABLE);
        // Stop at lobby for a moment and check in
//        this.setCooldown(settings.Settings.ticks * 20);  // Get an actual formula
        this.status = GuestStatus.CHECKED_IN;
    }

    public void removeRoom(Room room) {
        this.setAssignedRoom(null);
        room.removeOccupant(RoomStatus.DIRTY);
        this.status = GuestStatus.CHECKING_OUT;
    }

    @Override
    public boolean roomFilter(Room room) {
        return (room.getStatus() == RoomStatus.AVAILABLE);
    }

    @Override
    public void notify(HotelEvent hotelEvent) {
        if (hotelEvent.getHumanId() != null && hotelEvent.getHumanId() != this.getId()) return;

        switch (hotelEvent.getEventType()) {
            case ASSIGN_ROOM -> {
                Room nearestRoom = this.getLayout().getNearestRoom(this);
                if (nearestRoom == null) {
                    return;
                }
                this.assignRoom(nearestRoom);
            }
            case GO_ROOM -> {
                if (this.getAssignedRoom() == null) return;
                this.setDestination(this.getLayout().getRandomTile(this.getAssignedRoom()));
            }
            case CHECK_OUT -> {
                this.setDestination(this.getLayout().getRandomTile(this.getLayout().getLobbies().getFirst()));
                this.removeRoom(this.getAssignedRoom());
            }
        }
    }
}
