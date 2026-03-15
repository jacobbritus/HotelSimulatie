import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class Simulation extends JPanel {
     Layout layout;
     SimulationController simulationController;
     SimulationSidebar simulationSidebar;
     private ArrayList<Human> humans;
     private final JPanel testPanel;
     private final String[][] rauweGrid;
     private int millisecondsPassed;

    public Simulation(String[][] rauweGrid) {
        this.setLayout(new BorderLayout()); // Zet layout in het midden
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.rauweGrid = rauweGrid;


        testPanel = new JPanel(new GridBagLayout());
        testPanel.setOpaque(true);
        testPanel.setBackground(Settings.achtergrondKleur);
        testPanel.setPreferredSize(new Dimension(Settings.schermBreedte , Settings.schermHoogte - 128 ));

        start();
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void start() {
        layout = new Layout(rauweGrid);
        testPanel.add(layout);
        this.add(testPanel);


        // test
        this.humans = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
            humans.add(new Guest(tile));
        }

        for (int i = 0; i < 7; i++) {
            Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
            humans.add(new Cleaner(tile));
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

    public void setSimulationSidebar(SimulationSidebar simulationSidebar) {
        this.simulationSidebar = simulationSidebar;
    }

    public void update() {
        for (Human human : this.humans) {
            human.update(this.layout);
            if (human instanceof Guest guest &&  guest.isLeaving()) guest.despawn();
        }
        this.simulationSidebar.update();

        humans.removeIf(g -> g instanceof Guest guest&& guest.isLeaving());

//        // Adding guests
        if (simulationController.getRunTime() % (Settings.ticks * 200) == 0) {
            Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
            humans.add(new Guest(tile));
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