import java.awt.*;
import java.util.Random;

public class Gast extends Mens {

    private boolean despawned = false;

    public Gast(Vakje start) {
        super(start);
    }

    @Override
    public void beweeg() {
        leeftijd++;

        // Leeftijd op → terug naar lobby
        if (leeftijd >= maxLeeftijd) {
            if (!(vakje.getOppervlakte() instanceof Lobby)) {
                destinatie = vindLobbyVakje();
            } else {
                // In lobby → despawn
                vakje.setBackground(vakje.kleur);
                vakje.zetMens(null);
                despawned = true;
                return;
            }
        }

        // Geen bestemming → kies er een
        if (destinatie == null) {
            kiesNieuweDestinatie();
            return;
        }

        // Bestemming bereikt
        if (vakje == destinatie) {
            if (vakje.getOppervlakte() instanceof Lobby && leeftijd >= maxLeeftijd) {
                vakje.setBackground(vakje.kleur);
                vakje.zetMens(null);
                despawned = true;
                return;
            }
            kiesNieuweDestinatie();
            return;
        }

        // Movement naar beste buur
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

    private Vakje vindLobbyVakje() {
        Oppervlakte[][] ruimtes = vakje.getOppervlakte().getRuimtes();

        for (int r = 0; r < ruimtes.length; r++) {
            for (int c = 0; c < ruimtes[0].length; c++) {
                if (ruimtes[r][c] instanceof Lobby) {
                    return ruimtes[r][c].getVakjes()[0][0];
                }
            }
        }
        return null;
    }

    private int afstand(Vakje a, Vakje b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public boolean isDespawned() {
        return despawned;
    }
}