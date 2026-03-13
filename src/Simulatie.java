import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Simulatie extends JPanel {

    private final Layout layout;
    private final SimulatieController simulatieController;
    private final List<Mens> mensen = new ArrayList<>();

    private int tickCount = 0;
    public int aantalGasten = 0;

    public Simulatie(JFrame frame, String[][] rauweGrid) {

        setLayout(new BorderLayout());
        setBackground(Instellingen.achtergrondKleur);

        // ScrollPane toont de layout
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);

        // Bouw layout
        layout = new Layout(rauweGrid);
        scrollPane.setViewportView(layout);

        // Eerste gast in de lobby
        Vakje lobby = vindLobbyVakje();
        if (lobby != null) {
            mensen.add(new Gast(lobby));
            aantalGasten++;
        }

        // Start simulatie
        simulatieController = new SimulatieController(this);
        simulatieController.start();
    }

    public void update() {
        tickCount++;

        // Laat alle mensen bewegen
        for (Mens mens : mensen) {
            mens.beweeg();
        }

        // Om de zoveel ticks een nieuwe gast
        if (tickCount % 2 == 0) {
            spawnGast();
        }

        // Verwijder despawned gasten
        mensen.removeIf(m -> {
            if (m instanceof Gast g && g.isDespawned()) {
                aantalGasten--;
                return true;
            }
            return false;
        });

        // Congestie langzaam laten verdwijnen
        verlaagAlleCongestie();
        updateHeatmap();

        // Herteken de layout
        layout.repaint();
    }

    private void verlaagAlleCongestie() {
        Oppervlakte[][] ruimtes = layout.getRuimtes();

        for (Oppervlakte[] rij : ruimtes) {
            for (Oppervlakte o : rij) {
                if (o == null) continue;

                Vakje[][] vakjes = o.getVakjes();
                for (Vakje[] vakRij : vakjes) {
                    for (Vakje v : vakRij) {
                        v.verlaagCongestie();
                    }
                }
            }
        }
    }

    private void updateHeatmap() {
        Oppervlakte[][] ruimtes = layout.getRuimtes();

        for (Oppervlakte[] rij : ruimtes) {
            for (Oppervlakte o : rij) {
                if (o == null) continue;

                Vakje[][] vakjes = o.getVakjes();
                for (Vakje[] vakRij : vakjes) {
                    for (Vakje v : vakRij) {
                        v.updateHeatmapKleur();
                    }
                }
            }
        }
    }

    private void spawnGast() {
        Vakje lobbyVakje = vindLobbyVakje();

        if (lobbyVakje == null) return;

        if (aantalGasten >= Instellingen.maxGasten) return;

        if (lobbyVakje.isVrij()) {
            mensen.add(new Gast(lobbyVakje));
            aantalGasten++;
        }
    }

    private Vakje vindLobbyVakje() {
        Oppervlakte[][] r = layout.getRuimtes();

        for (Oppervlakte[] oppervlaktes : r) {
            for (int j = 0; j < r[0].length; j++) {
                if (oppervlaktes[j] instanceof Lobby) {
                    return oppervlaktes[j].getVakjes()[0][0];
                }
            }
        }
        return null;
    }

    // Zoom blijft optioneel
    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;

        Oppervlakte[][] r = layout.getRuimtes();
        int hoogte = r.length;
        int breedte = r[0].length;

        layout.setPreferredSize(new Dimension(
                Instellingen.oppervlakGrootte * breedte,
                Instellingen.oppervlakGrootte * hoogte
        ));

        layout.revalidate();
        layout.repaint();
    }

    public void setTickSpeed(int speed) {
        simulatieController.setTickSpeed(speed);
    }
}