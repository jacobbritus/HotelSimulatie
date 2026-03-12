import javax.swing.*;
import java.awt.*;

public class Trap extends Oppervlakte {
    public Trap(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(241, 248, 233),
                new Color(220, 237, 200), ruimtes);
    }
}
