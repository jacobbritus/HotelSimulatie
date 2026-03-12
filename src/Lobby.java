//BELANGRIJK IMPORT NERGENS JAVA.AWT.*!! Deze importeerd ook java.awt.List en werkt de java.util.List tegen. Dan werkt pathfinding niet meer!!!

import javax.swing.*;
import java.awt.Color;


public class Lobby extends Oppervlakte {
    public Lobby(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(230, 230, 200),
                new Color(240, 210, 255),
                ruimtes
        );
    }


}