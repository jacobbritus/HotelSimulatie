import javax.swing.*;
import java.awt.Color;


public class Trap extends Facility {
    public Trap(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(241, 248, 233),
                new Color(220, 237, 200), ruimtes, row, column);
    }
}
