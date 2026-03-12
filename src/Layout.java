import javax.swing.*;
import java.awt.*;

public class Layout extends JPanel {
    public Oppervlakte[][] ruimtes;

    public Layout(String[][] rauweGrid, JPanel panel) {
        int hoogte = rauweGrid.length;
        int breedte = rauweGrid[0].length;

        ruimtes = new Oppervlakte[hoogte][breedte];
        this.setLayout(new GridLayout(hoogte, breedte));

        this.setPreferredSize(new Dimension(Instellingen.oppervlakGrootte * breedte, Instellingen.oppervlakGrootte * hoogte));

        this.voegRuimtes(rauweGrid, hoogte, breedte);

        panel.add(this);
        panel.revalidate();
        panel.repaint();
        panel.setVisible(true);

    }

    public Oppervlakte[][] getRuimtes() {
        return ruimtes;
    }

    public void voegRuimtes(String[][] grid, int hoogte, int breedte) {
        for (int r = 0; r < hoogte; r++) {
            for (int c = 0; c < breedte; c++) {
                String kamer = grid[r][c];

                if (kamer != null) {
                    switch (kamer) {
                        case "Kamer" -> ruimtes[r][c] = new Kamer(this, ruimtes);
                        case "Lift" -> ruimtes[r][c] = new Lift(this, ruimtes);
                        case "Trap" -> ruimtes[r][c] = new Trap(this, ruimtes);
                    }
                    // misschien
                    // ruimtes[r][c].returnLocatie
                    // returnt het vakje in het midden
                    // deze locaties kunnen gebruikt worden om mensen
                    // naar andere ruimtes te laten bewegen.
                } else {
                    JPanel leeg = ruimtes[r][c] = new Oppervlakte(this, null, null, null);
                    leeg.setBackground(Color.BLACK);
                    leeg.setOpaque(true);
                }
            }
        }
    }

    public void herlaad() {
        this.setPreferredSize(new Dimension(Instellingen.oppervlakGrootte * ruimtes[0].length,
                Instellingen.oppervlakGrootte * ruimtes.length));
        for (int r = 0; r < ruimtes.length; r++) {
            for (int c = 0; c < ruimtes[0].length; c++) {
                ruimtes[r][c].removeAll();
                ruimtes[r][c].herlaad();
                ruimtes[r][c].revalidate();

            }
        }

    }
}
