package human;

import facility.Facility;
import facility.Room;
import facility.Tile;
import layout.Layout;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;

public class Cleaner extends Human {
    Room roomToClean;
    boolean isCleaning;
    public Cleaner(Tile tile) {
        super(tile);
        this.getTile().setBackground(Color.BLUE);
        this.roomToClean = null;
        this.isCleaning = false;
    }

    @Override
    public void setTile(Tile newTile, Color color) {
        super.setTile(newTile, Color.BLUE);
    }

    @Override
    public void update(Layout layout) {
        if (this.activeCooldown()) {
            return;
        }

        if (!this.isAtDestination()) {
            this.move();
        } else {
            this.setAtDestination(false);
            Tile destination = null;

            if (this.roomToClean == null) {
//                setCooldown(100);
                ArrayList<Room> rooms = layout.getRooms();
                Integer distance = null;
                for (Room kamer : rooms) {
                    Facility current = this.getTile().getFacility();

                    int c = Math.abs(current.getRow() - kamer.getRow()) + Math.abs(current.getColumn() - kamer.getColumn());
                    if ((kamer.isDirty() && kamer.getCleaner() == null && kamer.getGuest() == null)
                            && (distance == null || distance > c)) {
                        distance = c;
                        this.roomToClean = kamer;
                        kamer.setCleaner(this);
                    }
                }

                destination = layout.getRandomTile(this.roomToClean);

            } else if (this.getTile().getFacility() == this.roomToClean) {
                if (!this.isCleaning) {
                    this.isCleaning =true;
                    this.setCooldown(Settings.cleanerBaseCleaningTime);;
                } else {
                    this.roomToClean.clean(this);
                    this.isCleaning = false;
                    this.roomToClean = null;
                    this.getTile().setBackground(Color.BLUE);
                }
                destination = layout.getRandomTile(this.roomToClean);

            } else {
                destination = layout.getRandomTile(null);
//                setCooldown(1000);
            }

            if (destination != null) this.bfs(destination);

        }

    }
}
