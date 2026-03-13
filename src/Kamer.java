import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;

// I've to create a file that holds all colors really

public class Kamer extends Facility {
    private Guest guest;
    private boolean isDirty;
    private final Color unavailableColor1;
    private final Color unavailableColor2;
    private final Color dirtyColor1;
    private final Color dirtyColor2;


    public Kamer(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(224, 246, 218),
                new Color(173, 222, 161), ruimtes, row, column);
        this.guest = null;
        this.isDirty = false;

        unavailableColor1 = new Color(246, 210, 210,255);
        unavailableColor2 = new Color(255, 138, 138,255);


       dirtyColor1 = new Color(246, 200, 173,255);
       dirtyColor2 = new Color(224, 185, 146,255);
    }

    public void clean() {
        this.isDirty = false;
        this.setBorder(new LineBorder(color2, 2));
        changeGroundColor(color1);

    }

    public boolean isDirty() {
        return this.isDirty;
    }


    public void setGuest(Guest guest) {
        if (guest != null) {
            this.isDirty = true;
            this.guest = guest;
            this.setBorder(new LineBorder(unavailableColor2 , 2));
            changeGroundColor(unavailableColor1);

        } else {
            this.guest = null;
            changeGroundColor(dirtyColor1);
            this.setBorder(new LineBorder(dirtyColor2, 2));
        }

    }

    public void changeGroundColor(Color color) {
        for (int r = 0; r < this.tiles.length; r++) {
            for (int c = 0; c <this.tiles[0].length ; c++) {
                Tile tile = this.tiles[r][c];
                tile.setBackground(color);
                tile.setActiveColor(color);
            }
        }
    }

    public Guest getGuest() {
        return this.guest;
    }
}
