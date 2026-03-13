import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Simulation extends JPanel {
     Layout layout;
     SimulationController simulationController;
     private ArrayList<Guest> guests;

    public Simulation(String[][] rauweGrid) {
        this.setLayout(new GridBagLayout()); // Zet layout in het midden
        this.setBackground(Settings.achtergrondKleur);
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.setOpaque(true);

        layout = new Layout(rauweGrid);
        this.add(layout);

        // test
        this.guests = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            guests.add(
                    new Guest(layout.getEntrance())
            );
        }
    }

    public void setSimulationController(SimulationController simulatieController) {
        this.simulationController = simulatieController;
    }

    public void update() {
        for (Guest guest : this.guests) {
            guest.update(this.layout);
        }

        for (Guest guests : this.guests) {
            if (guests.getLifeTime() < 0) {
                this.guests.remove(guests);
                guests.despawn();
            }
        }


        // Adding guests
        if (simulationController.getRunTime() % (Settings.ticks * 100) == 0) {
            guests.add(
                    new Guest(layout.getEntrance())
            );
        }
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