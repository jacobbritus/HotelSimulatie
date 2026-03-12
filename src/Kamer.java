//BELANGRIJK IMPORT NERGENS JAVA.AWT.*!! Deze importeerd ook java.awt.List en werkt de java.util.List tegen. Dan werkt pathfinding niet meer!!!

import javax.swing.*;
import java.awt.Color;


public class Kamer extends Facility {
    public Kamer(JPanel superPanel, Facility[][] ruimtes) {
        super(superPanel, new Color(255, 248, 225),
                new Color(240, 230, 190),
                ruimtes
        );
    }


}
