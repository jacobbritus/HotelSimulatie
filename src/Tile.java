import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class Tile extends JLabel {
    private final Facility facility;
    private final Color color;
    private Human human;
    private final HashMap<Direction, Tile> neighbours;

    public Facility getFloor() {
        return facility;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public Tile getNeigbour(Direction direction) {
        return neighbours.get(direction);
    }

    public void setNeighbour(Direction direction, Tile tile) {
            neighbours.put(direction, tile);
    }

    public Tile(Facility facility, Color color1, Color color2, boolean isEven) {
        this.facility = facility;
        neighbours = new HashMap<>();
        neighbours.put(Direction.UP, null);
        neighbours.put(Direction.DOWN, null);
        neighbours.put(Direction.LEFT, null);
        neighbours.put(Direction.RIGHT, null);

        if (isEven || !Settings.setSquaresAlternatingColors) {
            this.setBackground(color1);
            color = color1;
        } else {
            this.setBackground(color2);
            color = color2;
        }

        this.setOpaque(true);
    }
}
