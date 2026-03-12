import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulatie extends JPanel {
    Layout layout;
    SimulatieController simulatieController;
    List<Mens> mensen = new ArrayList<>();

    public Simulatie(JFrame frame, String[][] rauweGrid) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Instellingen.achtergrondKleur);
        this.setSize(new Dimension(640, 640));

        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setBorder(null);
        frame.getContentPane().add(scrollPane);

        layout = new Layout(rauweGrid, this);

        // Spawn 1 gast voor test
        // Zoek lobby
        Oppervlakte lobby = null;

        for (int r = 0; r < layout.getRuimtes().length; r++) {
            for (int c = 0; c < layout.getRuimtes()[0].length; c++) {
                if (layout.getRuimtes()[r][c] instanceof Lobby) {
                    lobby = layout.getRuimtes()[r][c];
                    break;
                }
            }
        }

        if (lobby == null) {
            throw new RuntimeException("Geen lobby gevonden in layout!");
        }

        // Kies een vakje in de lobby
        Vakje start = lobby.getVakjes()[0][0];

        // Spawn gast
        Gast g = new Gast(start);
        mensen.add(g);

        simulatieController = new SimulatieController(this);
        simulatieController.start();
    }

    public void update() {
        for (Mens mens : mensen) {
            mens.beweeg();
        }
        layout.repaint();
    }

    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;
        this.layout.herlaad();
        this.layout.revalidate();
    }
}