import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Vakje extends JLabel {
    Oppervlakte oppervlakte;
    Color kleur;
    Mens mens;

    // Nieuw: buren voor pathfinding
    List<Vakje> buren = new ArrayList<>();

    public Vakje(Oppervlakte oppervlakte, Color kleur1, Color kleur2, boolean isEven) {
        this.oppervlakte = oppervlakte;

        if (isEven || !Instellingen.vakjesAlternerendeKleuren) {
            this.setBackground(kleur1);
            kleur = kleur1;
        } else {
            this.setBackground(kleur2);
            kleur = kleur2;
        }

        this.setOpaque(true);
    }

    public Oppervlakte getOppervlakte() {
        return oppervlakte;
    }

    public void zetMens(Mens mens) {
        this.mens = mens;
    }

    public boolean isVrij() {
        return mens == null;
    }

    public List<Vakje> getBuren() {
        return buren;
    }
}
