import javax.swing.*;
import java.awt.*;

public class Oppervlakte extends JPanel {
    Color kleur1;
    Color kleur2;
    Vakje[][] vakjes;
    Oppervlakte[][] ruimtes;

    public Oppervlakte(JPanel superPanel, Color kleur1, Color kleur2, Oppervlakte[][] ruimtes) {
        this.ruimtes = ruimtes;
        this.kleur1 = kleur1;
        this.kleur2 = kleur2;

        this.setBackground(kleur2);
        this.setBorder(BorderFactory.createLineBorder(kleur2));
        this.setOpaque(true);

        this.voegVakjesToe();
        this.verbindVakjes(); // Nieuw

        superPanel.add(this);
    }

    public Oppervlakte[][] getRuimtes() {
        return ruimtes;
    }

    public Vakje[][] getVakjes() {
        return vakjes;
    }

    public void voegVakjesToe() {
        this.setLayout(new GridLayout(Instellingen.oppervlakVakjes, Instellingen.oppervlakVakjes));
        vakjes = new Vakje[Instellingen.oppervlakVakjes][Instellingen.oppervlakVakjes];

        for (int r = 0; r < Instellingen.oppervlakVakjes; r++) {
            for (int c = 0; c < Instellingen.oppervlakVakjes; c++) {
                Vakje vakje = new Vakje(this, kleur1, kleur2, (c + r % 2) % 2 == 0);
                this.add(vakje);
                vakjes[r][c] = vakje;
            }
        }
    }

    // Verbind vakjes met buren
    public void verbindVakjes() {
        for (int r = 0; r < vakjes.length; r++) {
            for (int c = 0; c < vakjes[0].length; c++) {
                Vakje v = vakjes[r][c];

                if (r > 0) v.buren.add(vakjes[r - 1][c]);
                if (r < vakjes.length - 1) v.buren.add(vakjes[r + 1][c]);
                if (c > 0) v.buren.add(vakjes[r][c - 1]);
                if (c < vakjes[0].length - 1) v.buren.add(vakjes[r][c + 1]);
            }
        }
    }

    public void herlaad() {
        for (int r = 0; r < Instellingen.oppervlakVakjes; r++) {
            for (int c = 0; c < Instellingen.oppervlakVakjes; c++) {
                this.add(vakjes[r][c]);
            }
        }
    }
}