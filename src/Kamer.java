import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;

// I've to create a file that holds all colors really

public class Kamer extends Facility {
    private Guest guest;
    private boolean isDirty;
    private Color unavailableColor1;
    private Color unavailableColor2;
    private boolean isReservated;


    public Kamer(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(224, 246, 218),
                new Color(173, 222, 161), ruimtes, row, column);
        this.guest = null;
        this.isDirty = false;
        this.isReservated = false;

        unavailableColor1 = new Color(246, 210, 210,255);
        unavailableColor2 = new Color(255, 138, 138,255);
    }

    public void setReservated(boolean bool) {
        this.isReservated = bool;
    }

    public boolean getIsReservated() {
        return this.isReservated;
    }

    public void setGuest(Guest guest) {
        if (guest != null) {
            this.guest = guest;
            this.setBorder(new LineBorder(unavailableColor2 , 2));

        } else {
            this.isReservated = false;
            this.guest = null;
            this.isDirty = true;
            this.setBorder(new LineBorder(color2, 2));
        }
        changeGroundColor(guest);
    }

    public void changeGroundColor(Guest guest) {
        for (int r = 0; r < this.tiles.length; r++) {
            for (int c = 0; c <this.tiles[0].length ; c++) {
                Color color = null;
                Tile tile = this.tiles[r][c];
                if (guest == null) color = this.color1;
                else color = unavailableColor1;

                tile.setBackground(color);
                tile.setActiveColor(color);
            }
        }
    }

    public Guest getGuest() {
        return this.guest;
    }
}
