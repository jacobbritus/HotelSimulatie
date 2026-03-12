import javax.swing.*;
import java.awt.*;

public class Facility extends JPanel {
    Color color1;
    Color color2;
    Tile[][] tiles;
    Facility[][] ruimtes;

    public Facility(JPanel superPanel, Color color1, Color color2, Facility[][] ruimtes) {
        this.ruimtes = ruimtes;
        this.color1 = color1;
        this.color2 = color2;
        this.setBackground(color2);
        this.setBorder(BorderFactory.createLineBorder(color2));
        this.setOpaque(true);

        this.addTiles();

        superPanel.add(this);
    }

    public Facility[][] getFacilities() {
        return ruimtes;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void addTiles() {
        this.setLayout(new GridLayout(Settings.oppervlakVakjes, Settings.oppervlakVakjes));
        tiles = new Tile[Settings.oppervlakVakjes][Settings.oppervlakVakjes];

        for (int r = 0; r < Settings.oppervlakVakjes; r++) {
            for (int c = 0; c < Settings.oppervlakVakjes; c++) {
                Tile tile = new Tile(this,
                        color1, color2, (c + r % 2) % 2 == 0);

                this.add(tile);
                tiles[r][c] = tile;
            }
        }
    }

    public void reload() {
        for (int r = 0; r < Settings.oppervlakVakjes; r++) {
            for (int c = 0; c < Settings.oppervlakVakjes; c++) {
                this.add(tiles[r][c]);
            }
        }
    }
}
