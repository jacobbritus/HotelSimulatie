import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Vakje extends JPanel {

    public int gridR;
    public int gridC;

    private final Oppervlakte oppervlakte;
    private final Color baseColor;
    private Mens mens;

    public final List<Vakje> buren = new ArrayList<>();

    public Vakje(Oppervlakte oppervlakte, Color kleur1, Color kleur2, boolean variant) {
        this.oppervlakte = oppervlakte;
        this.baseColor = variant ? kleur1 : kleur2;

        this.setBackground(baseColor);
        this.setOpaque(true);

        // BELANGRIJK: vaste grootte zodat Swing het vakje correct tekent
        int size = Instellingen.oppervlakGrootte / Instellingen.oppervlakVakjes;
        this.setPreferredSize(new Dimension(size, size));
    }



    public boolean isVrij() {
        return mens == null;
    }

    public void zetMens(Mens m) {
        this.mens = m;
        updateKleur();
    }

    public Mens getMens() {
        return mens;
    }

    public void highlightDestinatie() {
        this.setBackground(Color.RED);
        repaint();
    }

    public void resetKleur() {
        this.setBackground(baseColor);
        repaint();
    }

    private void updateKleur() {
        if (mens != null) {
            this.setBackground(Color.BLUE);
        } else {
            this.setBackground(baseColor);
        }
        repaint();
    }

    public Oppervlakte getOppervlakte() {
        return oppervlakte;
    }

    public List<Vakje> getBuren() {
        return buren;
    }
}