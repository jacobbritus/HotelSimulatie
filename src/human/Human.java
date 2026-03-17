package human;

import events.HotelEventListener;
import facility.Facility;
import facility.Room;
import facility.Tile;
import layout.Layout;
import settings.Settings;

import java.awt.Color;
import java.util.*;

public abstract class Human implements RoomOccupant, HotelEventListener {
    private Tile tile;
    private final Layout layout;
    private int stepsTaken;
    private ArrayList<Tile> destinationPath;
    private Tile destination;
    private Integer cooldown;
    private Integer lifeTime;
    private Integer actionTime;

    private boolean isLeaving;
    private Room assignedRoom;

    public Human(Tile tile, Layout layout) {
        this.layout = layout;
        this.stepsTaken = 0;
        this.tile = tile;
        this.tile.setHuman(this);
    }

    public Layout getLayout() {
        return layout;
    }

    public boolean isLeaving() {
        return this.isLeaving;
    }

    public void setIsLeaving() {
        this.isLeaving = true;
    }

    public Tile getTile() {
        return tile;
    }


    public Tile getDestination() {
        return destination;
    }

    public void setDestination(Tile destination) {
        this.destination = destination;
    }

    public void setTile(Tile newTile, Color color) {
        this.tile.setHuman(null);
        this.tile = newTile;
        this.tile.setBackground(color);
        this.tile.setHuman(this);
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public void setCooldown(Integer milliseconds) {
        this.cooldown = milliseconds;
    }

    public boolean activeCooldown() {
        if (cooldown != null) {
            this.cooldown -= Settings.delay;
            if (cooldown < 0) this.cooldown = null;
            return true;
        }
        return false;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }
    public void setLifeTime(Integer lifeTime) {
        this.lifeTime = lifeTime;
    }
    public void decreaseLifeTime() {
        this.lifeTime = Math.max(this.lifeTime - 1000 / Settings.delay, 0);
    }

    public Integer getActionTime() {
        return actionTime;
    }
    public void setActionTime(Integer actionTime) {
        this.actionTime = actionTime;
    }
    public void decreaseActionTime() {
        this.actionTime = Math.max(this.actionTime - (1000 / Settings.delay), 0);
    }

    public void despawn() {
        this.tile.setHuman(null);
    }

    public void update(Layout layout) {
//        if (this.activeCooldown()) return;
//        if (this.getLifeTime() != null) decreaseLifeTime();
//
        if (this.getDestinationPath() != null) {
            this.move();
        }
//        } else {
//            this.decisionMaking(layout);
//            if (this.getDestination() != null) this.bfs(this.getDestination());
//        }
    }

    public abstract void decisionMaking(Layout layout);

    public void move() {
        if (stepsTaken < destinationPath.size() - 1) {
            Tile tile = destinationPath.get(stepsTaken);
            this.setTile(tile, null);
            stepsTaken++;
            stepsTaken++;
            stepsTaken++;
            stepsTaken++;
        } else {
            this.stepsTaken = 0;
            this.destination = null;
            this.destinationPath = null;
        }
    }

    public Tile returnOne(HashSet<Tile> tiles) {
        for (Tile neighbour : tiles) {
            if (neighbour != null) {
                return neighbour;
            }
        }
        return null;
    }

    public ArrayList<Tile> getDestinationPath() {
        return destinationPath;
    }

    public void bfs(Tile destination) {
        this.destinationPath = new ArrayList<>();

        HashSet<Tile> open = new HashSet<>();
        HashSet<Tile> closed = new HashSet<>();
        HashMap<Tile, Tile> breadcrumbs = new HashMap<>();

        open.add(this.tile);

        while (!open.isEmpty()) {
            Tile current = returnOne(open);
            open.remove(current);
            closed.add(current);

            if (current == destination) {
                retraceSteps(destination, breadcrumbs);
                break;
            }

            for (Tile neighbour : current.getNeighbours().values()) {
                if (neighbour == null || closed.contains(neighbour)
                        || !neighbour.isWalkable(this) ||
                        !inaccessibleFacility(neighbour) || moveFilter(neighbour)) {
                    continue;
                }
                open.add(neighbour);
                breadcrumbs.put(neighbour, current);
            }
        }
    }

    public abstract boolean moveFilter(Tile neighbour);

    public boolean inaccessibleFacility(Tile neighbour) {
        Facility facility = neighbour.getFacility();
        return facility.isAccessible(this);
    }

    public void retraceSteps(Tile destination, HashMap<Tile, Tile> breadcrumbs) {
        Tile step = destination;
        while (step != this.getTile()) {
            step = breadcrumbs.get(step);
            this.destinationPath.add(step);
        }
        Collections.reverse(this.destinationPath);
    }
}


