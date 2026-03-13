import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Tile extends JLabel {
    private final Facility facility;
    private final int row;
    private final int column;
    private final Color color;
    private Human human;
    private int gCost;
    private int hCost;
    private final HashMap<Direction, Tile> neighbours;

    public Tile(Facility facility, Color color1, Color color2, boolean isEven, int row, int column) {
        this.gCost = 0;
        this.hCost = 0;
        this.row = row;
        this.column = column;
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

    public Human getHuman() {
        return this.human;
    }

    public boolean isWalkable() {
        return human == null;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }
    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = gCost;
    }


    public int getRow() {
        return row;
    }

    public void revertColor() {
        this.setBackground(this.color);
    }

    public int getColumn() {
        return column;
    }

    public HashMap<Direction, Tile> getNeighbours() {
        return neighbours;
    }

    public Integer getGlobalPoint(String axis) {
        if (axis.equals("y")) {
            return (this.facility.getRow() * Settings.facilityTilesSize) + this.getRow();
        } else if (axis.equals("x")) {
            return (this.facility.getColumn() * Settings.facilityTilesSize) + this.getColumn();
        } else {
            return null;
        }
    }



    public Facility getFacility() {
        return facility;
    }

    public void setHuman(Human human) {
        if (human == null) {
            revertColor();
        }
        this.human = human;
    }

    public Tile getNeighbour(Direction direction) {
        return neighbours.get(direction);
    }

    public void setNeighbour(Direction direction, Tile tile) {
            neighbours.put(direction, tile);
    }
}
