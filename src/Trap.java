import javax.swing.*;
import java.awt.Color;


public class Trap extends Facility {
    public Trap(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(255, 248, 225),
                new Color(240, 230, 190),
                ruimtes, row, column);
    }
}

