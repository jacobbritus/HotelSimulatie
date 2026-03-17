//BELANGRIJK IMPORT NERGENS JAVA.AWT.*!! Deze importeerd ook java.awt.List en werkt de java.util.List tegen. Dan werkt pathfinding niet meer!!!

import javax.swing.*;
import java.awt.Color;


public class Trap extends Oppervlakte {
    public Trap(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(241, 248, 233),
                new Color(220, 237, 200), ruimtes);
    }
}
