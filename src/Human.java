import java.awt.Color;
import java.util.*;

public class Human {
    private Tile tile;
    private int stepsTaken;
    private ArrayList<Tile> destinationPath;
    private boolean atDestination;
    private Integer walkingCooldown;
    private Integer lifeTime;

    public Human(Tile tile) {
        this.walkingCooldown = null;
        this.atDestination = true;
        this.stepsTaken = 0;

        this.tile = tile;
        this.tile.setHuman(this);
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isAtDestination() {
        return atDestination;
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

    public void setWalkingCooldown(Integer milliseconds) {
        if (milliseconds != null) {
            this.walkingCooldown = milliseconds;
        } else {
            if ((int) (Math.random() * 100) > 98 ) {
                this.walkingCooldown = (Settings.ticks) * (int) (Math.random() * 10);
            }
        }
    }

    public void handleWalkingCooldown() {
        this.walkingCooldown -= Settings.ticks;
        if (walkingCooldown < 0) {
            this.walkingCooldown = null;
        }
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime() {
        this.lifeTime = Settings.ticks * Math.max((int) (Math.random() * 500), 100);
    }

    public void decreaseLifeTime() {
        this.lifeTime -= Settings.ticks;
    }

    public void despawn() {
        this.tile.setHuman(null);
    }

    public void update(Layout layout) {

    }

    public void move() {
        if (walkingCooldown != null) {
            this.handleWalkingCooldown();
            return;
        }

        if (stepsTaken < destinationPath.size()) {
            setWalkingCooldown(null);
            Tile tile = destinationPath.get(stepsTaken);
            if (tile.isWalkable()) this.setTile(tile, null);
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
                if (neighbour == null || closed.contains(neighbour) || !neighbour.isWalkable()) {
                    continue;
                }

                open.add(neighbour);
                breadcrumbs.put(neighbour, current);
            }
        }
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


