import javax.swing.*;
import java.awt.*;

public class Lobby extends Oppervlakte {
    public Lobby(JPanel superPanel, Oppervlakte[][] ruimtes) {
        super(superPanel, new Color(230, 230, 200),
                new Color(240, 210, 255),
                ruimtes
        );
    }


}