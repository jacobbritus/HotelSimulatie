import javax.swing.*;
import java.awt.*;

public class Hall extends Facility {
    public Hall(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(241, 220, 193),
                new Color(238, 200, 157), ruimtes, row, column);
    }
}