import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gast extends Mens {

    private boolean despawned = false;
    private Vakje destinatie;
    private List<Vakje> pad = new ArrayList<>();

    private int wachttijd = 0;
    private static final int MAX_WACHTTIJD = 10;

    private final Random random = new Random();

    public Gast(Vakje start) {
        super(start);
        kiesNieuweDestinatie();
    }

    @Override
    public void beweeg() {
        if (despawned) return;

        leeftijd++;

        // Te oud → terug naar lobby
        if (leeftijd >= maxLeeftijd && !(destinatie.getOppervlakte() instanceof Lobby)) {
            destinatie = vindLobbyVakje();
            herberekenPadMetPredictive();
        }

        // Geen pad → probeer opnieuw
        if (pad == null || pad.isEmpty()) {
            herberekenPadMetPredictive();
            if (pad == null || pad.isEmpty()) return;
        }

        Vakje volgende = pad.remove(0);

        // Vakje bezet (en niet de bestemming) → herbereken
        if (!volgende.isVrij() && volgende != destinatie) {

            // Gast wacht even
            wachttijd++;

            // Te lang gewacht → kies nieuw pad
            if (wachttijd >= MAX_WACHTTIJD) {
                wachttijd = 0;
                herberekenPadMetPredictive();
            }

            return;
        }

        // Huidig vakje verlaten
        vakje.zetMens(null);
        if (vakje != destinatie && vakje.isVrij()) {
            vakje.resetKleur();

        }

        // Naar volgend vakje
        vakje = volgende;
        vakje.zetMens(this); // verhoogt ook congestie in Vakje
        wachttijd = 0;

        // Bestemming bereikt
        if (vakje == destinatie) {

            // In lobby + te oud → despawn
            if (vakje.getOppervlakte() instanceof Lobby && leeftijd >= maxLeeftijd) {
                vakje.zetMens(null);
                vakje.resetKleur();
                despawned = true;
                verwijderPredictiveLoad();
                return;
            }

            // Nieuwe bestemming kiezen
            kiesNieuweDestinatie();
        }
    }

    private void kiesNieuweDestinatie() {
        // Oude bestemming resetten als die vrij is
        if (destinatie != null && destinatie.isVrij()) {
            destinatie.resetKleur();
        }

        verwijderPredictiveLoad();

        Oppervlakte[][] ruimtes = vakje.getOppervlakte().getRuimtes();
        List<Vakje> mogelijke = new ArrayList<>();

        for (Oppervlakte[] rij : ruimtes) {
            for (Oppervlakte o : rij) {
                if (o == null) continue;
                Vakje[][] vakjes = o.getVakjes();
                if (vakjes == null || vakjes.length == 0) continue;
                mogelijke.add(vakjes[random.nextInt(vakjes.length)][random.nextInt(vakjes[0].length)]);
            }
        }

        if (mogelijke.isEmpty()) return;

        destinatie = mogelijke.get(random.nextInt(mogelijke.size()));
        destinatie.highlightDestinatie();

        pad = AStar.findPath(vakje, destinatie);
        voegPredictiveLoadToe();
    }

    private void herberekenPadMetPredictive() {
        verwijderPredictiveLoad();
        pad = AStar.findPath(vakje, destinatie);
        voegPredictiveLoadToe();
    }

    private void voegPredictiveLoadToe() {
        if (pad == null) return;
        for (Vakje v : pad) {
            v.toekomstigeCongestie++;
        }
    }

    private void verwijderPredictiveLoad() {
        if (pad == null) return;
        for (Vakje v : pad) {
            if (v.toekomstigeCongestie > 0) v.toekomstigeCongestie--;
        }
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