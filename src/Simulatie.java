import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulatie extends JPanel {

    private final Layout layout;
    private final SimulatieController simulatieController;
    private final JFrame applicatieFrame;
    private final List<Mens> mensen = new ArrayList<>();

    public int tickCount = 0;
    public int aantalGasten = 0;

    public Simulatie(JFrame frame, String[][] rauweGrid) {

        this.applicatieFrame = frame;

        setLayout(new BorderLayout());
        setBackground(Instellingen.achtergrondKleur);

        // ScrollPane toont de layout
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);

        // Bouw layout
        layout = new Layout(rauweGrid);
        layout.setSimulatie(this);
        scrollPane.setViewportView(layout);

        // Eerste gast in de lobby
        Vakje lobby = vindLobbyVakje();
        if (lobby != null) {
            mensen.add(new Gast(lobby));
            aantalGasten++;
        }

        scrollPane.setFocusable(false);
        layout.setFocusable(false);

        // Start simulatie
        simulatieController = new SimulatieController(this);
        simulatieController.start();
    }


    // UPDATE LOOP

    public void update() {
        tickCount++;

        // Laat alle mensen bewegen
        for (Mens mens : mensen) {
            mens.beweeg();
        }

        // Spawn nieuwe gasten
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

        // Congestie updates
        verlaagAlleCongestie();
        berekenNabijheidsCongestie();
        updateHeatmap();

        // Herteken
        layout.repaint();
        repaint(); // voor debug overlay

        if (Instellingen.debugAan) {
            ((Applicatie)applicatieFrame).updateDebug(aantalGasten, tickCount, Instellingen.heatmapAan, Instellingen.maxGasten);
        }
    }


    // CONGESTIE

    private void verlaagAlleCongestie() {
        for (Oppervlakte[] rij : layout.getRuimtes()) {
            for (Oppervlakte o : rij) {
                if (o == null) continue;

                for (Vakje[] vakRij : o.getVakjes()) {
                    for (Vakje v : vakRij) {
                        v.verlaagCongestie();
                    }
                }
            }
        }
    }

    private void berekenNabijheidsCongestie() {

        // Reset
        for (Oppervlakte[] rij : layout.getRuimtes()) {
            for (Oppervlakte o : rij) {
                for (Vakje[] vakRij : o.getVakjes()) {
                    for (Vakje v : vakRij) {
                        v.nabijheidsCongestie = 0;
                    }
                }
            }
        }

        // Voor elke gast → verhoog congestie rondom hem
        for (Mens m : mensen) {
            Vakje v = m.vakje;

            for (Vakje buur : v.getBuren()) {
                buur.nabijheidsCongestie += 3; // tweakbaar
            }
        }
    }

    public void updateHeatmap() {
        for (Oppervlakte[] rij : layout.getRuimtes()) {
            for (Oppervlakte o : rij) {
                if (o == null) continue;

                for (Vakje[] vakRij : o.getVakjes()) {
                    for (Vakje v : vakRij) {
                        v.updateHeatmapKleur();
                    }
                }
            }
        }
    }


    // SPAWN

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
        for (Oppervlakte[] rij : layout.getRuimtes()) {
            for (Oppervlakte o : rij) {
                if (o instanceof Lobby) {
                    return o.getVakjes()[0][0];
                }
            }
        }
        return null;
    }


    // ZOOM

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


    // TICK SPEED

    public void setTickSpeed(int speed) {
        simulatieController.setTickSpeed(speed);
    }


    // DEBUG OVERLAY

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Debug now uses a separate frame
    }
}