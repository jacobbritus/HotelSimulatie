import javax.swing.*;
import java.awt.*;

public class Lobby extends Oppervlakte {
    public Lobby(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(255, 230, 200),
                new Color(240, 210, 180),
                ruimtes
        );
    }


}