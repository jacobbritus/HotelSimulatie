import com.sun.jdi.ClassObjectReference;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Layout extends JPanel {
    private final Facility[][] facilities;
    private final Tile entrance;
    private boolean roomsAreFull;


    public Layout(String[][] rawGrid) {
        int height = rawGrid.length;
        int width = rawGrid[0].length;

        facilities = new Facility[height][width];

        // GridLayout voor de oppervlakten
        this.setLayout(new GridLayout(height, width));

        // Zichtbare grootte
        this.setPreferredSize(new Dimension(
                Settings.oppervlakGrootte * width,
                Settings.oppervlakGrootte * height
        ));

        // Add all facilities and connect their tiles
        addFacilities(rawGrid);
        connectTiles();

        // Entrance
        Facility facility = this.facilities[height - 1][0];
        this.entrance = getRandomTile(null, true, Lobby.class);
        this.roomsAreFull = false;
    }

    public boolean getRoomsAreFull() {
        return this.roomsAreFull;
    }

    public void setRoomsAreFull(boolean roomsAreFull) {
        this.roomsAreFull = roomsAreFull;
    }

    public Tile getEntrance() {
        return this.entrance;
    }

    public Facility[][] getFacilities() {
        return facilities;
    }

    public Tile getRandomTile(Facility facility, boolean randomOfSameClass, Class<?> t) {
        Facility randomFacility = null;
        if (randomOfSameClass) {
            Class<?> c;
            if (facility != null) c = facility.getClass();
            else c = t;
            for (Facility f : getFacilityInstances(c)) {
                randomFacility = f;
                if ((int) (Math.random() * 100) > 50) break;
            }
        } else if (facility != null) {
            randomFacility = facility;
        } else {
            int r = (int) (Math.random() * this.facilities.length);
            int c = (int) (Math.random() * this.facilities.length);
            randomFacility = this.facilities[r][c];
        }
        
        int dr = (int) (Math.random() * Settings.facilityTilesSize);
        int dc = (int) (Math.random() * Settings.facilityTilesSize);

        return randomFacility.getTiles()[dr][dc];
    }

    public ArrayList<Facility> getFacilityInstances(Class <?> a) {
        ArrayList<Facility> foundFacilities = new ArrayList<>();
        for (int c = 0; c < this.facilities.length; c++) {
            for (int r = 0; r < this.facilities[0].length; r++) {
                Facility f = this.facilities[r][c];

                if (f.getClass() == a) {
                    foundFacilities.add(f);
                }
            }
        }
        return foundFacilities;
    }

    private void addFacilities(String[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {

                String type = grid[r][c];
                Facility o;

                switch (type) {
                    case "Kamer" -> o = new Kamer(this, facilities, r, c);
                    case "Lift" -> o = new Lift(this, facilities, r, c);
                    case "Trap" -> o = new Trap(this, facilities, r, c);
                    case "Lobby" -> o = new Lobby(this, facilities, r, c);
                    default -> {
                        o = new Facility(this, null, null, facilities, r, c);
                        o.setBackground(Color.BLACK);
                    }
                }

                facilities[r][c] = o;
                
                this.add(o);
            }
        }
    }

    public void reload() {
        this.setPreferredSize(new Dimension(Settings.oppervlakGrootte * this.facilities[0].length,
                Settings.oppervlakGrootte * this.facilities.length));
        for (int r = 0; r < this.facilities.length; r++) {
            for (int c = 0; c < this.facilities[0].length; c++) {
                this.facilities[r][c].removeAll();
                this.facilities[r][c].reload();
                this.facilities[r][c].revalidate();

            }
        }

    }
    
    private void connectTiles() {
        for (int r = 0; r < this.facilities.length; r++) {
            for (int c = 0; c < this.facilities[0].length; c++) {
                Facility facility = this.facilities[r][c];
                Tile[][] tiles = facility.getTiles();

                for (int dr = 0; dr < tiles.length; dr++) {
                    for (int dc = 0; dc < tiles[0].length; dc++) {
                        Tile tile = tiles[dr][dc];

                        if (r > 0 && dr == 0) {
                            tile.setNeighbour(Direction.UP, facilities[r - 1][c].getTiles()[tiles.length - 1][dc]);
                        } else if (dr > 0) tile.setNeighbour(Direction.UP, tiles[dr - 1][dc]);

                        if (dr == tiles.length - 1 && r < facilities.length - 1) {
                            tile.setNeighbour(Direction.DOWN, facilities[r + 1][c].getTiles()[0][dc]);
                        }  else if (dr < tiles.length - 1) tile.setNeighbour(Direction.DOWN, tiles[dr +1][dc]);

                        if (c > 0 && dc == 0) {
                            tile.setNeighbour(Direction.LEFT, facilities[r][c - 1].getTiles()[dr][tiles[0].length - 1]);
                        } else if (dc > 0) tile.setNeighbour(Direction.LEFT, tiles[dr][dc - 1]);

                        if (c < facilities[0].length - 1 && dc == tiles[0].length - 1) {
                            tile.setNeighbour(Direction.RIGHT, facilities[r][c+1].getTiles()[dr][0]);
                        }  else if (dc < tiles[0].length - 1) tile.setNeighbour(Direction.RIGHT, tiles[dr][dc + 1]);
                    }
                }

            }
        }
    }
}