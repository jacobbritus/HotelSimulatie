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

    // Congestie-waardes
    public int congestie = 0;               // actuele drukte
    public int toekomstigeCongestie = 0;    // predictive load

    public final List<Vakje> buren = new ArrayList<>();

    public Vakje(Oppervlakte oppervlakte, Color kleur1, Color kleur2, boolean variant) {
        this.oppervlakte = oppervlakte;
        this.baseColor = variant ? kleur1 : kleur2;

        setBackground(baseColor);
        setOpaque(true);

        // Vaste pixelgrootte zodat Swing het vakje correct tekent
        int size = Instellingen.oppervlakGrootte / Instellingen.oppervlakVakjes;
        setPreferredSize(new Dimension(size, size));
    }


    // Occupatie & kleurbeheer
    public boolean isVrij() {
        return mens == null;
    }

    public void zetMens(Mens m) {
        this.mens = m;
        verhoogCongestie();
        updateKleur();
    }

    public Mens getMens() {
        return mens;
    }

    public void resetKleur() {
        setBackground(baseColor);
        repaint();
    }

    public void highlightDestinatie() {
        setBackground(Color.RED);
        repaint();
    }

    private void updateKleur() {
        setBackground(mens != null ? Color.BLUE : baseColor);
        repaint();
    }

    public void updateHeatmapKleur() {

        // Heatmap uit → reset naar normale kleur (als er geen mens staat)
        if (!Instellingen.heatmapAan) {
            if (mens == null && !getBackground().equals(Color.RED)) {
                setBackground(baseColor);
            }
            return;
        }

        // NIET overschrijven als er een mens staat
        if (mens != null) return;

        // NIET overschrijven als dit de bestemming is
        if (getBackground().equals(Color.RED)) return;

        int waarde = congestie + toekomstigeCongestie;

        if (waarde == 0) {
            setBackground(baseColor);
            return;
        }

        int max = 20;
        if (waarde > max) waarde = max;

        int rood = Math.min(255, 50 + waarde * 10);
        int groen = Math.max(0, 200 - waarde * 10);

        setBackground(new Color(rood, groen, 50));
    }


    // Congestie

    public void verhoogCongestie() {
        congestie++;
        toekomstigeCongestie += 2; // predictive boost
    }

    public void verlaagCongestie() {
        if (congestie > 0) congestie--;
        if (toekomstigeCongestie > 0) toekomstigeCongestie--;
    }

    // Structuur
    public Oppervlakte getOppervlakte() {
        return oppervlakte;
    }

    public List<Vakje> getBuren() {
        return buren;
    }
}