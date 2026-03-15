package human;

import facility.Facility;
import facility.Room;
import facility.Lobby;
import facility.Tile;
import layout.Layout;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Guest extends Human {
    private boolean isCheckedIn;
    private boolean isLeaving;
    private Room kamer;
    private Color arrivalColor = Color.RED;
    private Color checkedInColor = Color.GREEN;

    public Guest(Tile tile) {
        super(tile);
        this.isCheckedIn = false;
        this.kamer = null;
        this.getTile().setBackground(arrivalColor);
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        if (this.isCheckedIn) color = checkedInColor;
        else color = arrivalColor;
        super.setTile(newTile, color);
    }

    public boolean isLeaving() {
        return this.isLeaving;
    }

    public boolean getIsCheckedIn() {
        return this.isCheckedIn;
    }

    @Override
    public void update(Layout layout) {
        if (this.activeCooldown()) {
            return;
        }

        if (this.getIsCheckedIn()) {
            if (this.getLifeTime() == null) {
                this.setLifeTime(Settings.guestBaseStayTime);
            } else {
                this.decreaseLifeTime();
            }
        }

        if (!this.isAtDestination()) {
            this.move();
        } else {
//            if (this.getTile().getFacility() == this.kamer) this.setCooldown(settings.Settings.ticks * 50); // Get an actual formula
            this.setAtDestination(false);
            Tile destination;

            // No room availability, walk around randomly in lobby. Maybe leave and note statistics (start lifetime and if they leave, add statistic)
             if (!this.isCheckedIn || getLifeTime() == null || getLifeTime() < 1) { // Check in at lobby, find a room
             destination = layout.getRandomTile(layout.getLobbies().getFirst());
//             this.setCooldown(settings.Settings.ticks * 100);
                 if ((this.getTile().getFacility() instanceof Lobby)) {
                     if (this.getLifeTime() == null) this.assignRoom(layout);
                     else this.checkOut(layout);
                 }
            } else { // Walk within room for now.
                 destination = layout.getRandomTile(this.kamer);
            }

            if (destination != null) this.bfs(destination);
        }
    }

    public void checkOut(Layout layout) {
        if (this.kamer != null) this.kamer.setGuest(null);

        this.isLeaving = true;
        this.isCheckedIn = false;
    }

    public Room getRandomRoom(ArrayList<Room> rooms, Layout layout) {
        ArrayList<Room> kamers = layout.getRooms();
        Collections.shuffle(kamers);
        Room randomRoom = null;

        Room k = null;
        for (Room kamer : kamers) {
            if (kamer.getGuest() == null && !kamer.isDirty()) {
                randomRoom = kamer;
                break;
            }
        }

        return randomRoom;
    }

    public Room getNearestRoom(ArrayList<Room> rooms) {
        Integer lowestDistance = null;
        Room nearestRoom= null;
        Facility current = this.getTile().getFacility();

        for (Room room : rooms) {
            int c = Math.abs(current.getRow() - room.getRow()) + Math.abs(current.getColumn() - room.getColumn());

            if ((room.getGuest() == null && !room.isDirty()) && (lowestDistance == null || c < lowestDistance)) {
                nearestRoom = room;
                lowestDistance = c;
            }
        }
        return nearestRoom;
    }

    public void assignRoom(Layout layout) {
        ArrayList<Room> rooms = layout.getRooms();;
        Room k = getNearestRoom(rooms);
        if (k == null) {
            return;
        }
        this.kamer = k;
        k.setGuest(this);
        // Stop at lobby for a moment and check in
//        this.setCooldown(settings.Settings.ticks * 20);  // Get an actual formula
        this.isCheckedIn = true;

    }


}
