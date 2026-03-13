import javax.swing.*;
import java.awt.*;

public class Layout extends JPanel {

    private final Oppervlakte[][] ruimtes;

    public Layout(String[][] rauweGrid) {

        int hoogte = rauweGrid.length;
        int breedte = rauweGrid[0].length;

        ruimtes = new Oppervlakte[hoogte][breedte];

        // GridLayout voor de oppervlakten
        this.setLayout(new GridLayout(hoogte, breedte, 0, 0));



        // Maak alle oppervlakten
        voegRuimtes(rauweGrid);

        // Verbind alle oppervlakten
        Oppervlakte.verbindAlleOppervlakten(ruimtes);
    }



    public Oppervlakte[][] getRuimtes() {
        return ruimtes;
    }

    private void voegRuimtes(String[][] grid) {

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {

                String type = grid[r][c];
                Oppervlakte o;

                switch (type) {
                    case "Kamer" -> o = new Kamer(this, ruimtes);
                    case "Lift" -> o = new Lift(this, ruimtes);
                    case "Trap" -> o = new Trap(this, ruimtes);
                    case "Lobby" -> o = new Lobby(this, ruimtes);
                    default -> {
                        o = new Oppervlakte(this, null, null, ruimtes);
                        o.setBackground(Color.BLACK);
                    }
                }

                ruimtes[r][c] = o;


                this.add(o);
            }
        }
    }

    private Simulatie simulatie;

    public void setSimulatie(Simulatie s) {
        this.simulatie = s;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Debug overlay removed to avoid duplication and scrolling issues
    }
}