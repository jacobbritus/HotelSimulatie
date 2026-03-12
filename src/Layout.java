import javax.swing.*;
import java.awt.*;

public class Layout extends JPanel {

<<<<<<< Updated upstream
    private final Oppervlakte[][] ruimtes;

    public Layout(String[][] rauweGrid) {

=======
    public Layout(String[][] rauweGrid) {
>>>>>>> Stashed changes
        int hoogte = rauweGrid.length;
        int breedte = rauweGrid[0].length;

        ruimtes = new Oppervlakte[hoogte][breedte];

        // GridLayout voor de oppervlakten
        this.setLayout(new GridLayout(hoogte, breedte));

        // Zichtbare grootte
        this.setPreferredSize(new Dimension(
                Instellingen.oppervlakGrootte * breedte,
                Instellingen.oppervlakGrootte * hoogte
        ));

<<<<<<< Updated upstream
        // Maak alle oppervlakten
        voegRuimtes(rauweGrid);
=======
        this.voegRuimtes(rauweGrid, hoogte, breedte);

>>>>>>> Stashed changes

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
}