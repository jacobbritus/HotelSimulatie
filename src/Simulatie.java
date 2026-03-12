import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Simulatie extends JPanel {

    private final Layout layout;
    private final SimulatieController simulatieController;
    private final List<Mens> mensen = new ArrayList<>();

    private int tickCount = 0;


    public Simulatie(JFrame frame, String[][] rauweGrid) {

        // Layout voor deze panel (hoewel we vooral de layout zelf in een scrollpane tonen)
        this.setLayout(new BorderLayout());
        this.setBackground(Instellingen.achtergrondKleur);

        // ScrollPane toont de layout
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        frame.getContentPane().add(scrollPane);

        // Bouw layout
        layout = new Layout(rauweGrid);

        // ScrollPane toont layout
        scrollPane.setViewportView(layout);

        // Eerste gast in de lobby
        Vakje lobby = vindLobbyVakje();
        if (lobby != null) {
            mensen.add(new Gast(lobby));
        }

        // Start de simulatie
        simulatieController = new SimulatieController(this);
        simulatieController.start();

    public Simulatie(String[][] rauweGrid) {
        this.setLayout(new GridBagLayout()); // Zet layout in het midden
        this.setBackground(Instellingen.achtergrondKleur);
        this.setSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setOpaque(true);

       layout = new Layout(rauweGrid);
       this.add(layout);


    }


    public void update() {
        tickCount++;

        // Laat alle mensen bewegen
        for (Mens mens : mensen) {
            mens.beweeg();
        }

        // Om de zoveel ticks een nieuwe gast
        if (tickCount % 50 == 0) {
            spawnGast();
        }

        // Verwijder despawned gasten
        mensen.removeIf(m -> m instanceof Gast g && g.isDespawned());

        // Herteken de layout
        layout.repaint();
    }

    private void spawnGast() {
        Vakje lobbyVakje = vindLobbyVakje();

        if (lobbyVakje == null) return;

        if (lobbyVakje.isVrij()) {
            mensen.add(new Gast(lobbyVakje));
            System.out.println("Nieuwe gast gespawned in lobby.");
        }
    }

    private Vakje vindLobbyVakje() {
        Oppervlakte[][] r = layout.getRuimtes();

        for (Oppervlakte[] oppervlaktes : r) {
            for (int j = 0; j < r[0].length; j++) {
                if (oppervlaktes[j] instanceof Lobby) {
                    Vakje[][] vakjes = oppervlaktes[j].getVakjes();
                    return vakjes[0][0];
                }
            }
        }
        return null;
    }

    // Zoom hoeft niet, maar laten we hem veilig houden als hij nog ergens aangeroepen wordt
    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;


        // Nieuwe preferred size op basis van huidige grid
        Oppervlakte[][] r = layout.getRuimtes();
        int hoogte = r.length;
        int breedte = r[0].length;

        layout.setPreferredSize(new Dimension(
                Instellingen.oppervlakGrootte * breedte,
                Instellingen.oppervlakGrootte * hoogte
        ));

        layout.revalidate();
        layout.repaint();

        this.layout.herlaad();
        this.layout.revalidate();


    }

    public void setTickSpeed(int speed) {
        simulatieController.setTickSpeed(speed);
    }
}