import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Simulation extends JPanel {
     Layout layout;
     SimulatieController simulatieController;
     Human human;

    public Simulation(String[][] rauweGrid) {
        this.setLayout(new GridBagLayout()); // Zet layout in het midden
        this.setBackground(Settings.achtergrondKleur);
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.setOpaque(true);

        layout = new Layout(rauweGrid);
        this.add(layout);

        // test
        Tile randomTile = layout.getFacilities()[0][0].tiles[15][15];

        this.human = new Human(randomTile);
        randomTile.setHuman(new Human(randomTile));
        this.human.getRandomDestination();

        System.out.println();
        this.human.bfs();

    }

    public void update() {
//        this.human.move();
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