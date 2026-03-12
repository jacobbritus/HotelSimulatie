import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulatie extends JPanel {
    Layout layout;
    SimulatieController simulatieController;
    List<Mens> mensen = new ArrayList<>();

    int tickCount = 0;

    public Simulatie(JFrame frame, String[][] rauweGrid) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Instellingen.achtergrondKleur);
        this.setSize(new Dimension(640, 640));

        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setBorder(null);
        frame.getContentPane().add(scrollPane);

        layout = new Layout(rauweGrid, this);

        // Spawn 1 gast voor test
        Vakje start = vindLobbyVakje();
        if (start == null) {
            throw new RuntimeException("Geen lobby gevonden in layout!");
        }

        mensen.add(new Gast(start));

        simulatieController = new SimulatieController(this);
        simulatieController.start();
    }

    public void update() {
        tickCount++;

        for (Mens mens : mensen) {
            mens.beweeg();
        }

        // Elke 50 ticks een nieuwe gast
        if (tickCount % 50 == 0) {
            spawnGast();
        }

        // Verwijder gasten die despawnen
        mensen.removeIf(m -> m instanceof Gast g && g.isDespawned());

        layout.repaint();
    }

    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;
        this.layout.herlaad();
        this.layout.revalidate();
    }

    private void spawnGast() {
        Vakje lobbyVakje = vindLobbyVakje();
        if (lobbyVakje != null && lobbyVakje.isVrij()) {
            mensen.add(new Gast(lobbyVakje));
        }
    }

    private Vakje vindLobbyVakje() {
        for (int r = 0; r < layout.getRuimtes().length; r++) {
            for (int c = 0; c < layout.getRuimtes()[0].length; c++) {
                if (layout.getRuimtes()[r][c] instanceof Lobby) {
                    return layout.getRuimtes()[r][c].getVakjes()[0][0];
                }
            }
        }
        return null;
    }
}