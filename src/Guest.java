import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Guest extends Human{
    private boolean isCheckedIn;
    private boolean isLeaving;
    private Kamer kamer;

    public Guest(Tile tile) {
        super(tile);
        this.isCheckedIn = false;
        this.kamer = null;
        this.getTile().setBackground(Color.RED);
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        if (this.isCheckedIn) color = Color.GREEN;
        else color = Color.RED;
        super.setTile(newTile, color);
    }

    public boolean getIsLeaving() {
        return this.isLeaving;
    }

    public boolean getIsCheckedIn() {
        return this.isCheckedIn;
    }

    @Override
    public void update(Layout layout) {

        if (this.getIsCheckedIn()) {
            if (this.getLifeTime() == null) {
                this.setLifeTime(Settings.ticks * Math.max((int) (Math.random() * 100), 150)); // Get an actual formula
            } else {
                this.decreaseLifeTime();
            }
        }

        if (!this.isAtDestination()) {
            this.move();
        } else {

            System.out.println(layout.getRoomsAreFull());
            if (this.getTile().getFacility() == this.kamer) this.setWalkingCooldown(Settings.ticks * 50); // Get an actual formula
            this.setAtDestination(false);
            Tile destination;

            // No room availability, walk around randomly. Maybe leave and note statistics (start lifetime and if they leave, add statistic)
             if (layout.getRoomsAreFull() && this.kamer == null) {
                 destination = layout.getRandomTile(null, true, Lobby.class);
                 this.setWalkingCooldown(Settings.ticks * 100);
             } else if (!this.isCheckedIn || getLifeTime() == null || getLifeTime() < 0) { // Check in at lobby, find a room
                 destination = layout.getRandomTile(null, true, Lobby.class);
                 if ((this.getTile().getFacility() instanceof Lobby)) {
                     if (this.getLifeTime() == null) this.assignRoom(layout);
                     else this.checkOut(layout);
                 }
            } else { // Walk within room for now.
                 destination = layout.getRandomTile(this.kamer, false, null);
            }

            if (destination != null) this.bfs(destination);
        }
    }

    public void checkOut(Layout layout) {
        if (this.kamer != null) this.kamer.setGuest(null);
        layout.setRoomsAreFull(false);

        this.isLeaving = true;
        this.isCheckedIn = false;
    }

    public void assignRoom(Layout layout) {
        ArrayList<Facility> kamers = layout.getFacilityInstances(Kamer.class);
        Collections.shuffle(kamers);

        Kamer k = null;
        for (Facility facility : kamers) {
            Kamer kamer = (Kamer) facility;
            if (kamer.getGuest() == null && !kamer.isDirty()) {
                k = kamer;
                break;
            }
        }
        if (k == null) {
            layout.setRoomsAreFull(true);
            return;
        }
        this.kamer = k;
        k.setGuest(this);
        // Stop at lobby for a moment and check in
        this.setWalkingCooldown(Settings.ticks * 20);  // Get an actual formula
        this.isCheckedIn = true;

    }


}
