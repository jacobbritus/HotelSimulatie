package facility;

import enums.FacilityState;
import settings.FacilityColors;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;

public class Facility extends JPanel {
    private final int row;
    private final int column;
    Tile[][] tiles;
    Facility[][] facilites;

    public Facility(JPanel superPanel, Facility[][] facilites, int row, int column) {
        this.row = row;
        this.column = column;
        this.facilites = facilites;
        this.setBorder(new LineBorder(this.getColor(FacilityState.DEFAULT2), 2));

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
                        (c + r % 2) % 2 == 0,
                        r, c);

                this.add(tile);
                tiles[r][c] = tile;
            }
        }
    }

    public Color getColor(FacilityState state) {
        HashMap<FacilityState, Color> map = FacilityColors.map.get(this.getClass());

        return map.get(state);
    }

    public void reload() {
        for (int r = 0; r < Settings.facilityTilesSize; r++) {
            for (int c = 0; c < Settings.facilityTilesSize; c++) {
                this.add(tiles[r][c]);
            }
        }
    }
}
