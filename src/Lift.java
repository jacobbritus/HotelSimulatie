import javax.swing.*;
import java.awt.*;

public class Lift extends Oppervlakte {
    public Lift(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(225, 245, 254),
                new Color(179, 229, 252), ruimtes);
    }

}
