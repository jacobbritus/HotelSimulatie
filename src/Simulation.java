import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulation extends JPanel {
     Layout layout;
     SimulationController simulationController;
     private ArrayList<Guest> guests;
     private final JPanel testPanel;
     private final String[][] rauweGrid;

    public Simulation(String[][] rauweGrid) {
        this.setLayout(new BorderLayout()); // Zet layout in het midden
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.setOpaque(true);
        this.rauweGrid = rauweGrid;
        testPanel = new JPanel(new GridBagLayout());
        testPanel.setOpaque(true);
        testPanel.setBackground(Settings.achtergrondKleur);

        testPanel.setPreferredSize(new Dimension(Settings.schermBreedte , Settings.schermHoogte - 128 ));

        start();
    }

    public void start() {
        layout = new Layout(rauweGrid);
        testPanel.add(layout);
        this.add(testPanel);

        // test
        this.guests = new ArrayList<>();


        for (int i = 0; i < 2; i++) {
            Tile tile = this.layout.getRandomTile(null, true, Lobby.class);
            System.out.println(tile.getRow());
            guests.add(new Guest(tile));
        }
    }

    public void reset() {
        testPanel.removeAll();
        start();
        this.revalidate();
        this.repaint();
    }


    public void setSimulationController(SimulationController simulatieController) {
        this.simulationController = simulatieController;
    }

    public void update() {
        for (Guest guest : this.guests) {
            guest.update(this.layout);
            if (guest.getIsLeaving()) guest.despawn();
        }

        guests.removeIf(Guest::getIsLeaving);

        // Adding guests
        if (simulationController.getRunTime() % (Settings.ticks * 50) == 0) {
            Tile tile = this.layout.getRandomTile(null, true, Lobby.class);
            guests.add(new Guest(tile));
        }
    }

    public void zoom(int aantal) {
        Settings.oppervlakGrootte += aantal;

        // Nieuwe preferred size op basis van huidige grid
        Facility[][] r = layout.getFacilities();
        int hoogte = r.length;
        int breedte = r[0].length;

        testPanel.setPreferredSize(new Dimension(
                Settings.oppervlakGrootte * breedte *3,
                Settings.oppervlakGrootte * hoogte *2));
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