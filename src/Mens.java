import java.awt.*;

public class Mens {
    Vakje vakje;
    Vakje destinatie; // this.vakje.getOppervlakte().getRuimtes()[3][3].getVakjes()[3][3] ---
    int cooldown;

    public Mens(Vakje vakje) {
        this.vakje = vakje;
        this.vakje.setBackground(Color.BLUE);
    }

    public void getDestinatie() {
        this.vakje.getOppervlakte().getRuimtes()[0][0].getVakjes()[0][0].setBackground(Color.RED);
    }

    public void zetVakje(Vakje doelVakje) {
        this.vakje.zetMens(null);
        this.vakje = doelVakje;
    }

    public void beweeg() {
        // if this.vakje == destinatie
        // setCooldown (elk update = cooldown--)
        // if cooldown == 0
        // nieuw destinatie is this.vakje.getOppervlakte.getRuimtes().getRandomDestinatie()

        // OR CREATE PATH ONCE AND JUST MOVE TO NEXT
        // IF NOT TRAVERSABLE DONT MOVE SIMPLY

        // if this.vakje.getOppervlakte == destinatie.getOpperVlakte
        // manhattan formula en
//            if vakje == vrij
        // beweeg die richting op
        // else
        // manhattan formula en
//            if vakje == vrij
        // beweeg die richting op
    }
}


