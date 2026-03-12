import java.awt.Color;
import java.util.*;

public class Human {
    private Tile tile;
    private int stepsTaken;
    private List<Tile> destinationPath;


    public Tile getTile() {
        return tile;
    }

    public Human(Tile tile) {
        this.destinationPath = null;
        stepsTaken = 0;
        this.tile = tile;
        this.tile.setBackground(Color.BLUE);
    }

    public Tile getRandomDestination() {
        this.tile.getFacility().getFacilities()[2][2].tiles[4][4].setBackground(Color.RED);
        return this.tile.getFacility().getFacilities()[4][4].tiles[0][0];
    }

    public void setTile(Tile newTile) {
        this.tile.revertColor();
        this.tile.setHuman(null);
        this.tile = newTile;
        this.tile.setBackground(Color.BLUE);
    }

    public void move() {

    }

    public void expand(Tile tile) {
        for (Tile neighbour : tile.getNeighbours().values()) {
            if (neighbour != null) {
                neighbour.setBackground(Color.GREEN);

            }
        }
    }



    public void bfs() {
        ArrayList<Tile> open = new ArrayList<>();
        open.add(this.tile);
        ArrayList<Tile> closed = new ArrayList<>();

        // Start at the starting node and expand outwards
        for (Tile neighbour : this.tile.getNeighbours().values()) {
            if (neighbour != null) {
                neighbour.setBackground(Color.GREEN);
                expand(neighbour);
            }

            // save parent in map to retrace later
        }
        while (!open.isEmpty()) {
            Tile current = open.getFirst();
            open.remove(current);
            closed.add(current);

            System.out.println(current);
            for (Tile neighbour : current.getNeighbours().values())
                if (neighbour != null) {
                    open.add(neighbour);
                    break;
                }

        }

    }


}


