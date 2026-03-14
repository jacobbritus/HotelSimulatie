import javax.swing.*;
import java.awt.*;

public class Hall extends Facility {
    public Hall(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(222, 222, 222),
                new Color(203, 203, 203), ruimtes, row, column);
    }
}