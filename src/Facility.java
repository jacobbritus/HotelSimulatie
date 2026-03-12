import javax.swing.*;
import java.awt.*;

public class Facility extends JPanel {
    Color color1;
    Color color2;
    private int row;
    private int column;
    Tile[][] tiles;
    Facility[][] ruimtes;

    public Facility(JPanel superPanel, Color color1, Color color2, Facility[][] ruimtes, int row, int column) {
        this.row = row;
        this.column = column;
        this.ruimtes = ruimtes;
        this.color1 = color1;
        this.color2 = color2;
        this.setBackground(color2);
        this.setBorder(BorderFactory.createLineBorder(color2));
        this.setOpaque(true);

        this.addTiles();

        superPanel.add(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Facility[][] getFacilities() {
        return ruimtes;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void addTiles() {
        this.setLayout(new GridLayout(Settings.facilityTilesSize, Settings.facilityTilesSize));
        tiles = new Tile[Settings.facilityTilesSize][Settings.facilityTilesSize];

        for (int r = 0; r < Settings.facilityTilesSize; r++) {
            for (int c = 0; c < Settings.facilityTilesSize; c++) {
                Tile tile = new Tile(this,
                        color1, color2,
                        (c + r % 2) % 2 == 0,
                        r, c);

                this.add(tile);
                tiles[r][c] = tile;
            }
        }
    }

    public void reload() {
        for (int r = 0; r < Settings.facilityTilesSize; r++) {
            for (int c = 0; c < Settings.facilityTilesSize; c++) {
                this.add(tiles[r][c]);
            }
        }
    }
}
