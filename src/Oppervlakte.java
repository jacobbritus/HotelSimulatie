import javax.swing.*;
import java.awt.*;

public class Oppervlakte extends JPanel {

    private final Color kleur1;
    private final Color kleur2;
    private final Oppervlakte[][] ruimtes;
    private Vakje[][] vakjes;

    public Oppervlakte(JPanel parent, Color kleur1, Color kleur2, Oppervlakte[][] ruimtes) {

        this.kleur1 = kleur1 != null ? kleur1 : Color.GRAY;
        this.kleur2 = kleur2 != null ? kleur2 : Color.DARK_GRAY;
        this.ruimtes = ruimtes;

        // GridLayout voor vakjes
        this.setLayout(new GridLayout(Instellingen.oppervlakVakjes, Instellingen.oppervlakVakjes));

        // Maak vakjes
        voegVakjesToe();

        // Verbind vakjes binnen deze oppervlakte
        verbindVakjes();

    }



    public Vakje[][] getVakjes() {
        return vakjes;
    }

    public Oppervlakte[][] getRuimtes() {
        return ruimtes;
    }

    private void voegVakjesToe() {
        vakjes = new Vakje[Instellingen.oppervlakVakjes][Instellingen.oppervlakVakjes];

        for (int r = 0; r < vakjes.length; r++) {
            for (int c = 0; c < vakjes[0].length; c++) {

                Vakje v = new Vakje(this, kleur1, kleur2, (r + c) % 2 == 0);
                v.gridR = r;
                v.gridC = c;

                vakjes[r][c] = v;
                this.add(v);
            }
        }
    }

    private void verbindVakjes() {
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

    public static void verbindAlleOppervlakten(Oppervlakte[][] ruimtes) {

        for (int r = 0; r < ruimtes.length; r++) {
            for (int c = 0; c < ruimtes[0].length; c++) {

                Oppervlakte huidig = ruimtes[r][c];
                if (huidig == null) continue;

                if (r > 0) verbindRanden(huidig, ruimtes[r - 1][c], "boven");
                if (r < ruimtes.length - 1) verbindRanden(huidig, ruimtes[r + 1][c], "onder");
                if (c > 0) verbindRanden(huidig, ruimtes[r][c - 1], "links");
                if (c < ruimtes[0].length - 1) verbindRanden(huidig, ruimtes[r][c + 1], "rechts");
            }
        }
    }

    private static void verbindRanden(Oppervlakte a, Oppervlakte b, String richting) {

        if (a == null || b == null) return;

        Vakje[][] A = a.getVakjes();
        Vakje[][] B = b.getVakjes();

        int size = A.length;

        switch (richting) {
            case "boven" -> {
                for (int i = 0; i < size; i++) {
                    A[0][i].buren.add(B[size - 1][i]);
                }
            }
            case "onder" -> {
                for (int i = 0; i < size; i++) {
                    A[size - 1][i].buren.add(B[0][i]);
                }
            }
            case "links" -> {
                for (int i = 0; i < size; i++) {
                    A[i][0].buren.add(B[i][size - 1]);
                }
            }
            case "rechts" -> {
                for (int i = 0; i < size; i++) {
                    A[i][size - 1].buren.add(B[i][0]);
                }
            }
        }
    }

    // herlaad() VERWIJDERD — veroorzaakte bugs
}