package facility;

import enums.FacilityState;
import enums.FacilityType;
import human.Human;
import settings.FacilityColors;
import settings.Settings;
import simulation.SimulationController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;

public class Facility extends JPanel {
    private FacilityType type;
    private final int row;
    private final int column;
    private final SimulationController simulationController;
    private final int level;
    Tile[][] tiles;

    public Facility(JPanel superPanel, FacilityType type, int row, int column, SimulationController simC) {
        this.type = type;
        this.level = 1;
        this.row = row;
        this.column = column;
        this.setBorder(new LineBorder(this.getColor(FacilityState.DEFAULT2), 2));
        this.simulationController = simC;
        this.addTiles();
        this.setOpaque(true);

        superPanel.add(this);
    }

    public FacilityType getType() {
        return type;
    }

    public boolean notConnectedTo(Facility facility) {
        return this.getType() == FacilityType.ROOM && facility.getType() == FacilityType.ROOM;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAccessible(Human human) {
        return true;
    }

    public void onInteract(Human human) {}

    public SimulationController getSimulationController() {
        return this.simulationController;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
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
        HashMap<FacilityState, Color> map = FacilityColors.map.get(this.getType());

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
