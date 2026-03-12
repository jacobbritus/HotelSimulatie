import javax.swing.*;
import java.awt.*;

public class Vakje extends JLabel {
    Oppervlakte oppervlakte;
    Color kleur;
    Mens mens;

    public Oppervlakte getOppervlakte() {
        return oppervlakte;
    }

    public void zetMens(Mens mens) {
        this.mens = mens;
    }

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
}
