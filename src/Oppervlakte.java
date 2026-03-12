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
        this.verbindVakjes();

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

    // Verbind vakjes binnen dezelfde Oppervlakte
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

    // Verbind randen van oppervlakten met elkaar
    public static void verbindAlleOppervlakten(Oppervlakte[][] ruimtes) {
        for (int r = 0; r < ruimtes.length; r++) {
            for (int c = 0; c < ruimtes[0].length; c++) {

                Oppervlakte huidig = ruimtes[r][c];

                if (huidig == null) continue;

                if (r > 0)
                    verbindRanden(huidig, ruimtes[r - 1][c], "boven");

                if (r < ruimtes.length - 1)
                    verbindRanden(huidig, ruimtes[r + 1][c], "onder");

                if (c > 0)
                    verbindRanden(huidig, ruimtes[r][c - 1], "links");

                if (c < ruimtes[0].length - 1)
                    verbindRanden(huidig, ruimtes[r][c + 1], "rechts");
            }
        }
    }

    private static void verbindRanden(Oppervlakte a, Oppervlakte b, String richting) {
        if (a == null || b == null) return;

        Vakje[][] vakA = a.getVakjes();
        Vakje[][] vakB = b.getVakjes();

        int size = vakA.length;

        switch (richting) {
            case "boven" -> {
                for (int i = 0; i < size; i++) {
                    vakA[0][i].buren.add(vakB[size - 1][i]);
                }
            }
            case "onder" -> {
                for (int i = 0; i < size; i++) {
                    vakA[size - 1][i].buren.add(vakB[0][i]);
                }
            }
            case "links" -> {
                for (int i = 0; i < size; i++) {
                    vakA[i][0].buren.add(vakB[i][size - 1]);
                }
            }
            case "rechts" -> {
                for (int i = 0; i < size; i++) {
                    vakA[i][size - 1].buren.add(vakB[i][0]);
                }
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