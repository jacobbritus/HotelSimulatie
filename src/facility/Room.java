package facility;

import enums.FacilityState;
import human.Cleaner;
import human.Guest;
import settings.Settings;
import simulation.SimulationController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

// I've to create a file that holds all colors really

public class Room extends Facility {
    private Guest guest;
    private Cleaner cleaner;
    private boolean isDirty;
    private MouseListener onHover;

    public Room(JPanel superPanel, Facility[][] ruimtes, int row, int column, SimulationController simulationController) {
        super(superPanel, ruimtes, row, column, simulationController);
        this.guest = null;
        this.isDirty = false;
        this.cleaner =  null;
    }

    public Cleaner getCleaner() {
        return this.cleaner;
    }

    public void setCleaner(Cleaner cleaner) {
        this.cleaner = cleaner;
    }

    public void clean(Cleaner cleaner) {
        changeGroundColor(getColor(FacilityState.AVAILABLE1), this.getColor(FacilityState.AVAILABLE2));
        this.cleaner = null;
        this.isDirty = false;
        this.setBorder(new LineBorder(this.getColor(FacilityState.AVAILABLE2), 2));
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setGuest(Guest guest) {
        if (guest != null) {
            this.guest = guest;
            this.setBorder(new LineBorder(getColor(FacilityState.UNAVAILABLE2) , 2));
            changeGroundColor(getColor(FacilityState.UNAVAILABLE1), getColor(FacilityState.UNAVAILABLE2));
            Room kamer = this;

            this.onHover = new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    kamer.setBorder(new LineBorder(Color.YELLOW, 2));
                    kamer.getSimulationController().setShowRoom(kamer);

                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    kamer.setBorder(new LineBorder(kamer.getColor(FacilityState.UNAVAILABLE2), 2));
                    kamer.getSimulationController().setShowRoom(null);
                }
            };
            this.addMouseListener(this.onHover);
        } else {
            this.isDirty = true;
            this.getSimulationController().setShowRoom(null);
            this.removeMouseListener(this.onHover);
            this.guest = null;



            changeGroundColor(getColor(FacilityState.DIRTY1), getColor(FacilityState.DIRTY2));
            this.setBorder(new LineBorder(getColor(FacilityState.DIRTY2), 2));
        }
    }

    public void changeGroundColor(Color color1, Color color2) {
        for (int r = 0; r < this.tiles.length; r++) {
            for (int c = 0; c <this.tiles[0].length ; c++) {
                Color activeColor;
                Tile tile = this.tiles[r][c];
                if (tile.isEven() || !Settings.setSquaresAlternatingColors) {
                    activeColor = color1;
                } else {
                    activeColor = color2;
                }
                tile.setActiveColor(activeColor);
                if (this.cleaner != null && tile == this.cleaner.getTile()) continue;
                tile.setBackground(activeColor);

            }
        }
    }

    public Guest getGuest() {
        return this.guest;
    }
}
