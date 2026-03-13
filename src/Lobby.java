import javax.swing.*;
import java.awt.Color;

public class Lobby extends Facility {
    public Lobby(JPanel superPanel, Facility[][] ruimtes, int row, int column) {
        super(superPanel, new Color(251, 225, 254),
                new Color(247, 179, 252), ruimtes, row, column);
    }

}
