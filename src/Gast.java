import java.awt.*;
import java.util.Random;

public class Gast extends Mens {

    public Gast(Vakje start) {
        super(start);
    }

    @Override
    public void beweeg() {
        if (destinatie == null) {
            kiesNieuweDestinatie();
            return;
        }

        if (vakje == destinatie) {
            kiesNieuweDestinatie();
            return;
        }

        Vakje beste = null;
        int besteScore = Integer.MAX_VALUE;

        for (Vakje buur : vakje.getBuren()) {
            if (!buur.isVrij()) continue;

            int score = afstand(buur, destinatie);
            if (score < besteScore) {
                besteScore = score;
                beste = buur;
            }
        }

        if (beste != null) {
            vakje.setBackground(vakje.kleur);
            vakje.zetMens(null);

            vakje = beste;
            vakje.zetMens(this);
            vakje.setBackground(Color.BLUE);
        }
    }

    private void kiesNieuweDestinatie() {
        Vakje[][] vakjes = vakje.getOppervlakte().getVakjes();
        Random random = new Random();
        destinatie = vakjes[random.nextInt(vakjes.length)][random.nextInt(vakjes[0].length)];
        destinatie.setBackground(Color.RED);
    }

    private int afstand(Vakje a, Vakje b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}