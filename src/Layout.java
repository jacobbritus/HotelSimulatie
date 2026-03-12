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
                    case "Kamer" -> o = new Kamer(this, facilities);
                    case "Lift" -> o = new Lift(this, facilities);
                    case "Trap" -> o = new Trap(this, facilities);
                    default -> {
                        o = new Facility(this, null, null, facilities);
                        o.setBackground(Color.BLACK);
                    }
                }

                facilities[r][c] = o;
                
                this.add(o);
            }
        }
    }

    public void reload() {
        this.setPreferredSize(new Dimension(Settings.oppervlakGrootte * facilities[0].length,
                Settings.oppervlakGrootte * facilities.length));
        for (int r = 0; r < facilities.length; r++) {
            for (int c = 0; c < facilities[0].length; c++) {
                facilities[r][c].removeAll();
                facilities[r][c].reload();
                facilities[r][c].revalidate();

            }
        }

    }

    private void connectTile(Direction direction) {

    }
    
    private void connectTiles() {
        for (int r = 0; r < facilities.length; r++) {
            for (int c = 0; c < facilities[0].length; c++) {
                Tile[][] tiles = facilities[r][c].getTiles();
                for (int dr = 0; dr < tiles.length; dr++) {
                    for (int dc = 0; dc < tiles[0].length; dc++) {
                        Tile tile = tiles[dr][dc];

                        if (r > 0) {
                            if (dr == 0) {
                                tile.setNeighbour(Direction.UP,
                                        facilities[r - 1][c].getTiles()[tiles.length - 1][dc]
                                        );
                            } else {
                                tile.setNeighbour(Direction.UP,
                                        tiles[dr - 1][dc]
                                );
                            }
                        }

                        if (r < facilities.length - 1) {
                            if (dr == tiles.length - 1) {
                                tile.setNeighbour(Direction.DOWN,
                                        facilities[r + 1][c].getTiles()[0][dc]
                                );
                            } else {
                                tile.setNeighbour(Direction.DOWN,
                                        tiles[dr + 1][dc]
                                );
                            }
                        }

                        if (c > 0) {
                            if (dc == 0) {
                                tile.setNeighbour(Direction.LEFT,
                                        facilities[r][c - 1].getTiles()[dr][tiles[0].length -1]
                                );
                            } else {
                                tile.setNeighbour(Direction.LEFT,
                                        tiles[dr][dc - 1]
                                );
                            }
                        }

                        if (c < facilities[0].length - 1) {
                            if (dc == tiles[0].length - 1) {
                                tile.setNeighbour(Direction.RIGHT,
                                        facilities[r][c + 1].getTiles()[dr][0]
                                );
                            } else {
                                tile.setNeighbour(Direction.RIGHT,
                                        tiles[dr][dc + 1]
                                );
                            }
                        }
                    }
                }

            }
        }
    }
}