import java.util.List;
import java.util.Random;

public class Gast extends Mens {

    private boolean despawned = false;
    private Vakje destinatie;
    private List<Vakje> pad;

    private final Random random = new Random();

    public Gast(Vakje start) {
        super(start);
        kiesNieuweDestinatie();
    }

    @Override
    public void beweeg() {

        leeftijd++;
        
        if (leeftijd % 50 == 0) {
            System.out.println("Gast beweegt! Huidi vakje: (" + vakje.gridR + "," + vakje.gridC + "), Destinatie: (" + destinatie.gridR + "," + destinatie.gridC + "), Leeftijd: " + leeftijd);
        }

        // Te oud → terug naar lobby
        if (leeftijd >= maxLeeftijd) {
            destinatie = vindLobbyVakje();
            pad = AStar.findPath(vakje, destinatie);
        }

        // Geen pad → probeer opnieuw een pad te vinden
        if (pad == null || pad.isEmpty()) {
            pad = AStar.findPath(vakje, destinatie);
            // Als er nog steeds geen pad is, kunnen we niet bewegen
            if (pad == null || pad.isEmpty()) {
                System.out.println("WAARSCHUWING: Geen pad gevonden! Huidi vakje: (" + vakje.gridR + "," + vakje.gridC + "), Destinatie: (" + destinatie.gridR + "," + destinatie.gridC + ")");
                return;
            }
        }

        Vakje volgende = pad.remove(0);

        // Als het vakje bezet is (en niet de bestemming), herbereken pad
        if (!volgende.isVrij() && volgende != destinatie) {
            pad = AStar.findPath(vakje, destinatie);
            return;
        }

        // Verlaat huidig vakje
        vakje.zetMens(null);
        // Als dit vakje niet meer de bestemming is, het vakje wordt automatisch baseColor
        // Als het WEL de bestemming is, moet het rood blijven
        if (vakje == destinatie) {
            vakje.highlightDestinatie();  // Zet het weer rood
        }


        // Ga naar volgende vakje
        vakje = volgende;
        vakje.zetMens(this);

        // Bestemming bereikt
        if (vakje == destinatie) {

            // Bestemming is bereikt, dus niet langer highlight nodig
            // updateKleur() zal dit al hebben gedaan
            
            // In lobby + te oud → despawn
            if (vakje.getOppervlakte() instanceof Lobby && leeftijd >= maxLeeftijd) {
                vakje.zetMens(null);
                vakje.resetKleur();
                despawned = true;
                return;
            }

            // Nieuwe bestemming
            kiesNieuweDestinatie();
        }
    }

    private void kiesNieuweDestinatie() {

        // Reset oude bestemming kleur (als die bestaat en er geen mens op staat)
        if (destinatie != null && destinatie.isVrij()) {
            destinatie.resetKleur();
        }

        Oppervlakte[][] ruimtes = vakje.getOppervlakte().getRuimtes();

        int r = random.nextInt(ruimtes.length);
        int c = random.nextInt(ruimtes[0].length);

        Vakje[][] vakjes = ruimtes[r][c].getVakjes();

        Vakje doel = vakjes[random.nextInt(vakjes.length)][random.nextInt(vakjes[0].length)];

        this.destinatie = doel;
        destinatie.highlightDestinatie();

        this.pad = AStar.findPath(vakje, doel);
    }

    private Vakje vindLobbyVakje() {
        Oppervlakte[][] ruimtes = vakje.getOppervlakte().getRuimtes();

        for (Oppervlakte[] ruimte : ruimtes) {
            for (int c = 0; c < ruimtes[0].length; c++) {
                if (ruimte[c] instanceof Lobby) {
                    return ruimte[c].getVakjes()[0][0];
                }
            }
        }
        return null;
    }

    public boolean isDespawned() {
        return despawned;
    }
}