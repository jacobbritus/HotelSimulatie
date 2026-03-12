import javax.swing.*;
import java.awt.*;

public class Layout extends JPanel {
    private final Facility[][] facilities;


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

        // Maak alle oppervlakten
        addFacilities(rawGrid);
        connectTiles();
    }

    public Facility[][] getFacilities() {
        return facilities;
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

    private void connectTile(Direction direction) {

    }
    
    private void connectTiles() {
        for (int r = 0; r < this.facilities.length; r++) {
            for (int c = 0; c < this.facilities[0].length; c++) {
                Tile[][] tiles = this.facilities[r][c].getTiles();

                for (int dr = 0; dr < tiles.length; dr++) {
                    for (int dc = 0; dc < tiles[0].length; dc++) {
                        Tile tile = tiles[dr][dc];

                        if (r > 0 && dr == 0) {
                            tile.setNeighbour(Direction.UP, this.facilities[r - 1][c].getTiles()[tiles.length - 1][dc]);
                        } else if (dr > 0) tile.setNeighbour(Direction.UP, tiles[dr - 1][dc]);

                        if (dr == tiles.length - 1 && r < this.facilities.length - 1) {
                            tile.setNeighbour(Direction.DOWN, this.facilities[r + 1][c].getTiles()[0][dc]);
                        }  else if (dr < tiles.length - 1) tile.setNeighbour(Direction.DOWN, tiles[dr +1][dc]);

                        if (c > 0 && dc == 0) {
                            tile.setNeighbour(Direction.LEFT, this.facilities[r][c - 1].getTiles()[dr][tiles[0].length - 1]);
                        } else if (dc > 0) tile.setNeighbour(Direction.LEFT, tiles[dr][dc - 1]);

                        if (dc == tiles[0].length - 1 && dc < this.facilities[0].length - 1) {
                            tile.setNeighbour(Direction.RIGHT, this.facilities[r ][c + 1].getTiles()[dr][0]);
                        }  else if (dc < tiles[0].length - 1) tile.setNeighbour(Direction.RIGHT, tiles[dr][dc + 1]);
                    }
                }

            }
        }
    }
}