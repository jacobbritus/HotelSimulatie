//BELANGRIJK IMPORT NERGENS JAVA.AWT.*!! Deze importeerd ook java.awt.List en werkt de java.util.List tegen. Dan werkt pathfinding niet meer!!!

import javax.swing.*;
import java.awt.Color;


public class Lift extends Facility {
    public Lift(JPanel superPanel, Facility[][] ruimtes) {
        super(superPanel, new Color(225, 245, 254),
                new Color(179, 229, 252), ruimtes);
    }

}
