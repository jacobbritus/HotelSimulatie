import javax.swing.*;
import java.awt.*;

public class Kamer extends Oppervlakte {
    public Kamer(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(255, 248, 225),
                new Color(240, 230, 190),
                ruimtes
        );
    }


}
