import javax.swing.*;
import java.awt.*;


public class Simulatie extends JPanel {
     Layout layout;
     SimulatieController simulatieController;


    public Simulatie(String[][] rauweGrid) {
        this.setLayout(new GridBagLayout()); // Zet layout in het midden
        this.setBackground(Settings.achtergrondKleur);
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.setOpaque(true);

       layout = new Layout(rauweGrid);
       this.add(layout);

        //         test
        Tile randomTile = layout.getFacilities()[4][2].tiles[15][15];
        Human randomMens = new Human(randomTile);
        randomTile.setHuman(new Human(randomTile));
        randomMens.getDestinatie();

        randomTile.getNeigbour(Direction.RIGHT).setBackground(Color.GREEN);
    }

    public void update() {
        System.out.println("yes");
    }

    public void zoom(int aantal) {
        Settings.oppervlakGrootte += aantal;

        // Nieuwe preferred size op basis van huidige grid
        Facility[][] r = layout.getFacilities();
        int hoogte = r.length;
        int breedte = r[0].length;

        layout.setPreferredSize(new Dimension(
                Settings.oppervlakGrootte * breedte,
                Settings.oppervlakGrootte * hoogte
        ));

        layout.revalidate();
        layout.repaint();

        this.layout.reload();
        this.layout.revalidate();
    }

}