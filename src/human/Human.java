package human;

import enums.Role;
import events.HotelEventListener;
import facility.Facility;
import facility.Room;
import facility.Tile;
import layout.Layout;

import java.awt.Color;
import java.util.*;

public abstract class Human implements RoomOccupant, HotelEventListener {
    private Tile tile;
    private final Layout layout;
    private int stepsTaken;
    private ArrayList<Tile> destinationPath;
    private Tile destination;
    private Room assignedRoom;
    private final Role role;

    public Human(Tile tile, Layout layout, Role role) {
        this.role = role;
        this.layout = layout;
        this.stepsTaken = 0;
        this.tile = tile;
        this.tile.setHuman(this);
    }

    public Role getRole() {
        return this.role;
    }

    public Room getAssignedRoom() {
        return assignedRoom;
    }

    public void setAssignedRoom(Room assignedRoom) {
        this.assignedRoom = assignedRoom;
    }

    public Layout getLayout() {
        return layout;
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

    public void update() {
        if (this.getDestinationPath() != null) {
            this.move();
        }
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
                        !accessibleFacility(neighbour) || moveFilter(neighbour)) {
                    continue;
                }
                open.add(neighbour);
                breadcrumbs.put(neighbour, current);
            }
        }
    }

    public abstract boolean moveFilter(Tile neighbour);

    public boolean accessibleFacility(Tile neighbour) {
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


