import java.awt.*;

public class Mens {
    protected Vakje vakje;
    protected Vakje destinatie;

    public Mens(Vakje vakje) {
        this.vakje = vakje;
        vakje.zetMens(this);
        vakje.setBackground(Color.BLUE);
    }

    public void beweeg() {
        // Basis: doet niets
    }
}