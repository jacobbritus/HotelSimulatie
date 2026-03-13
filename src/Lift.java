import javax.swing.*;
import java.awt.Color;

public class Lift extends Facility {
    public Lift(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(225, 245, 254),
                new Color(179, 229, 252), ruimtes, row, column);
    }

}
