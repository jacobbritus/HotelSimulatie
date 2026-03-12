import java.awt.*;

public class Human {
    Tile tile;
    Tile destinatie; // this.vakje.getOppervlakte().getRuimtes()[3][3].getVakjes()[3][3] ---
    int cooldown;

    public Human(Tile tile) {
        this.tile = tile;
        this.tile.setBackground(Color.BLUE);
    }

    public void getDestinatie() {
        this.tile.getFloor().getFacilities()[0][0].getTiles()[0][0].setBackground(Color.RED);
    }

    public void zetVakje(Tile newTile) {
        this.tile.setHuman(null);
        this.tile = newTile;
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


