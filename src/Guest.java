import java.awt.*;
import java.util.ArrayList;

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
                this.setLifeTime();
            } else {
                this.decreaseLifeTime();
            }
        }

        if (!this.isAtDestination()) {
            this.move();
        } else {
            if (this.kamer != null && this.kamer.getGuest() == null) {
                this.kamer.setGuest(this);
            }
            if (this.getTile().getFacility() == this.kamer) this.setWalkingCooldown(Settings.ticks * 50); // Slow movement in room
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


        // Find an empty room
        Kamer k = (Kamer) kamers.get(0);
        while (k.getIsReservated() || k.getGuest() != null) {
            int r = (int) (Math.random() * kamers.size());
            k = (Kamer) kamers.get(r);
        }

        // Set Room
        this.kamer = k;
        k.setReservated(true);

//        k.setGuest(this);

        // Stop at lobby for a moment and check in
        this.setWalkingCooldown(Settings.ticks * 100);
        this.isCheckedIn = true;


        // Update room availability
        boolean full = true;

        for (Facility f : kamers) {
            Kamer kf = (Kamer) f;
            if ((!kf.getIsReservated())) {
                full = false;
            }
        }
        if (full) layout.setRoomsAreFull(true);
    }


}
