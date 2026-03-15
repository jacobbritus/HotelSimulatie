package layout;

import enums.Direction;
import facility.*;
import settings.Settings;
import simulation.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Layout extends JPanel {
    private final Facility[][] facilities;
    private ArrayList<Lobby> lobbies;
    private ArrayList<Room> rooms;


    public Layout(String[][] rawGrid, SimulationController simulationController) {
        int height = rawGrid.length;
        int width = rawGrid[0].length;
        facilities = new Facility[height][width];
        lobbies = new ArrayList<>();
        rooms = new ArrayList<>();

        // GridLayout voor de oppervlakten
        this.setLayout(new GridLayout(height, width));

        // Zichtbare grootte
        this.setPreferredSize(new Dimension(
                Settings.oppervlakGrootte * width,
                Settings.oppervlakGrootte * height
        ));

        // Add all facilities and connect their tiles
        addFacilities(rawGrid, simulationController);
        connectTiles();

        // Store important facilities once
        for (int r = 0; r < facilities.length; r++) {
            for (int c = 0; c < facilities[0].length; c++) {
                Facility facility = this.facilities[r][c];
                if (facility == null) continue;
                if (facility instanceof Lobby lobby) lobbies.add(lobby);
                if (facility instanceof Room kamer) rooms.add(kamer);
            }
        }

    }

    public ArrayList<Lobby> getLobbies() {
        Collections.shuffle(this.lobbies);
        return lobbies;
    }

    public ArrayList<Room> getRooms() {
        Collections.shuffle(this.rooms);
        return this.rooms;
    }

    public Facility[][] getFacilities() {
        return facilities;
    }

    public Tile getRandomTile(Facility facility) {

        Facility randomFacility = facility;
        if (facility == null) {
            int r = (int) (Math.random() * this.facilities.length);
            int c = (int) (Math.random() * this.facilities.length);
            randomFacility = this.facilities[r][c];

            while (randomFacility == null) {
                r = (int) (Math.random() * this.facilities.length);
                c = (int) (Math.random() * this.facilities.length);
                randomFacility = this.facilities[r][c];
            }
        }

        
        int dr = (int) (Math.random() * Settings.facilityTilesSize);
        int dc = (int) (Math.random() * Settings.facilityTilesSize);

        return randomFacility.getTiles()[dr][dc];
    }

    private void addFacilities(String[][] grid, SimulationController simulationController) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {

                String type = grid[r][c];
                Facility o;
                switch (type) {
                    case "Kamer" -> o = new Room(this, facilities, r, c, simulationController);
                    case "Lift" -> o = new Lift(this, facilities, r, c, simulationController);
                    case "Trap" -> o = new Stairs(this, facilities, r, c, simulationController);
                    case "Lobby" -> o = new Lobby(this, facilities, r, c, simulationController);
                    case "Hall" -> o = new Hall(this, facilities, r, c, simulationController);
                    default -> {
                        JPanel inaccessible = new JPanel();
                        inaccessible.setBackground(Settings.achtergrondKleur);
                        this.add(inaccessible);
                        continue;
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
                Facility facility = this.facilities[r][c];
                if (facility == null) continue;
                facility.removeAll();
                facility.reload();
                facility.revalidate();

            }
        }

    }
    
    private void connectTiles() {
        for (int r = 0; r < this.facilities.length; r++) {
            for (int c = 0; c < this.facilities[0].length; c++) {
                Facility facility = this.facilities[r][c];
                if (facility == null) continue;
                Tile[][] tiles = facility.getTiles();

                for (int dr = 0; dr < tiles.length; dr++) {
                    for (int dc = 0; dc < tiles[0].length; dc++) {
                        Tile tile = tiles[dr][dc];

                        if (r > 0 && dr == 0) {
                            if ((facilities[r - 1][c]) == null) continue;
                            tile.setNeighbour(Direction.UP, facilities[r - 1][c].getTiles()[tiles.length - 1][dc]);
                        } else if (dr > 0) tile.setNeighbour(Direction.UP, tiles[dr - 1][dc]);

                        if (dr == tiles.length - 1 && r < facilities.length - 1) {
                            if ((facilities[r + 1][c]) == null) continue;
                            tile.setNeighbour(Direction.DOWN, facilities[r + 1][c].getTiles()[0][dc]);
                        }  else if (dr < tiles.length - 1) tile.setNeighbour(Direction.DOWN, tiles[dr +1][dc]);

                        if (c > 0 && dc == 0) {
                            if ((facilities[r][c - 1]) == null) continue;
                            tile.setNeighbour(Direction.LEFT, facilities[r][c - 1].getTiles()[dr][tiles[0].length - 1]);
                        } else if (dc > 0) tile.setNeighbour(Direction.LEFT, tiles[dr][dc - 1]);

                        if (c < facilities[0].length - 1 && dc == tiles[0].length - 1) {
                            if ((facilities[r][c + 1]) == null) continue;
                            tile.setNeighbour(Direction.RIGHT, facilities[r][c+1].getTiles()[dr][0]);
                        }  else if (dc < tiles[0].length - 1) tile.setNeighbour(Direction.RIGHT, tiles[dr][dc + 1]);
                    }
                }
            }
        }
    }
}