import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Facility extends JPanel {
    Color availableColor1;
    Color availableColor2;
    private int row;
    private int column;
    Tile[][] tiles;
    Facility[][] facilites;

    public Facility(JPanel superPanel, Color color1, Color color2, Facility[][] facilites, int row, int column) {
        this.row = row;
        this.column = column;
        this.facilites = facilites;
        this.availableColor1 = color1;
        this.availableColor2 = color2;
        this.setBorder(new LineBorder(color2, 2));

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
        return this.facilites;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void addTiles() {
        this.setLayout(new GridLayout(Settings.facilityTilesSize, Settings.facilityTilesSize));
        tiles = new Tile[Settings.facilityTilesSize][Settings.facilityTilesSize];

        for (int r = 0; r < Settings.facilityTilesSize; r++) {
            for (int c = 0; c < Settings.facilityTilesSize; c++) {
                Tile tile = new Tile(this,
                        availableColor1, availableColor2,
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
