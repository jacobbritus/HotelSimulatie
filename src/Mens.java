import java.awt.*;
import java.util.Random;

public class Mens {
    Vakje vakje;
    Vakje destinatie; // this.vakje.getOppervlakte().getRuimtes()[3][3].getVakjes()[3][3] ---
    int cooldown; //Hoe lang blijft iemand op zijn destinatie
    int leeftijd; //ticks geleefd
    int maxLeeftijd = 500; //voorbeeld leeftijd voor nu (later wss een random leeftijd tussen range van ticks)

    public Mens(Vakje vakje) {
        this.vakje = vakje;
        this.vakje.zetMens(this);
        this.vakje.setBackground(Color.BLUE);


    }

    public void kiesNieuweDestinatie(){
        //Simpele versie, later uitbreiden (kiest nu nog een random vakje in dezelfde ruimte)
        Vakje[][] vakjes = vakje.getOppervlakte().getVakjes();
        Random random = new Random();
        int r = random.nextInt(vakjes.length);
        int c = random.nextInt(vakjes[0].length);
        destinatie = vakjes[r][c];
        destinatie.setBackground(Color.RED); //debug
    }

    public void getDestinatie() {
        this.vakje.getOppervlakte().getRuimtes()[0][0].getVakjes()[0][0].setBackground(Color.RED);
    }

    public void zetVakje(Vakje doelVakje) {
        this.vakje.zetMens(null);
        this.vakje = doelVakje;
    }

    public void beweeg() {
        if (destinatie == null) {
            kiesNieuweDestinatie();
            return;
        }

        if (vakje == destinatie) {
            //bestemming bereikt
            leeftijd++;
            if (leeftijd > maxLeeftijd) {
                //Voor nu despawnen, nog geen lobby om naar toe te gaan
                vakje.setBackground(vakje.kleur);
                vakje.zetMens(null);
            } else{
                kiesNieuweDestinatie();
            }
            return;
        }

        //kies buur die dichste bij destinatie ligt
        Vakje beste = null;
        int besteScore = Integer.MAX_VALUE;

        for (Vakje buur : vakje.getBuren()){
            if (!buur.isVrij()){
                continue;
            };

            int dr = Math.abs(buur.getY() - destinatie.getY());
            int dc = Math.abs(buur.getX() - destinatie.getX());
            int score = dr + dc;

            if (score < besteScore){
                besteScore = score;
                beste = buur;
            }

            if (beste != null){
                //verplaats
                vakje.setBackground(vakje.kleur);
                vakje.zetMens(null);

                vakje = beste;
                vakje.zetMens(this);
                vakje.setBackground(Color.BLUE);
            } else{
                //geen buur die destinatie kan halen -> nieuwe destinatie kiezen
                kiesNieuweDestinatie();
            }
        }



        //Creeër een destinatie waar je heen wilt.
        //Pak kortste route voeg toe aan array
        //Loop door array en beweeg naar volgende vakje in array.
        //Als array leeg: maak array vol, als niet leeg door bewegen.
        //Later wanneer meer complex, checken of meerdere mensen door zelfde pad gaan of zelfde lift, opnieuw berekenen wat sneller is.
        //
    }
}


