package human;

import facility.Facility;
import facility.Room;
import facility.Lift;
import facility.Tile;
import layout.Layout;
import settings.Settings;

import java.awt.Color;
import java.util.*;

public class Human {
    private Tile tile;
    private int stepsTaken;
    private ArrayList<Tile> destinationPath;
    private boolean atDestination;
    private Integer cooldown;
    private Integer lifeTime;
    private final boolean isUsingLift;
    Color color;

    public Human(Tile tile) {
        this.cooldown = null;
        this.atDestination = true;
        this.stepsTaken = 0;
        this.isUsingLift = false;

        this.tile = tile;
        this.tile.setHuman(this);
    }

    public boolean isUsingLift() {
        return isUsingLift;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isAtDestination() {
        return this.atDestination;
    }

    public void setAtDestination(boolean bool) {
        this.atDestination = bool;
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
            this.cooldown -= Settings.ticks;
            if (cooldown < 0) this.cooldown = null;
            return true;
        }
        return false;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void decreaseLifeTime() {
        this.lifeTime = Math.max(this.lifeTime - 1000 / Settings.ticks, 0);
    }

    public void despawn() {
        this.tile.setHuman(null);
    }

    public void update(Layout layout) {

    }

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
            this.atDestination = true;
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
                if (neighbour == null || closed.contains(neighbour) || (neighbour.getFacility() instanceof Lift && !this.isUsingLift)
                        || !neighbour.isWalkable() || isKamer(neighbour)) {
                    continue;
                }

                open.add(neighbour);
                breadcrumbs.put(neighbour, current);
            }
        }
    }

    public boolean isKamer(Tile neighbour) {
        Facility facility = neighbour.getFacility();
        if  (this instanceof Guest) return facility instanceof Room kamer && kamer.getGuest() != this;
        else if (this instanceof Cleaner cleaner)  return facility instanceof Room kamer && (kamer.getCleaner() != this && this.getTile().getFacility() != kamer);
        return false;
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


