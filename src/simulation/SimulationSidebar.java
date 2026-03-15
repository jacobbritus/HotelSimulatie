package simulation;

import human.Guest;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class SimulationSidebar extends JPanel {
    Simulation simulation;
    JLabel humanCount;
    public SimulationSidebar(Simulation simulation) {
        this.simulation = simulation;
        this.setBackground(Settings.themeColor);
        this.setPreferredSize(new Dimension(Settings.schermBreedte / 4, Settings.schermHoogte));
        this.setBorder(new MatteBorder(0, 0, 0, 1, Settings.themeColor2));

        this.setLayout(new GridBagLayout());

        this.setOpaque(true);

        this.humanCount = new JLabel();

        this.add(humanCount);
    }

    public void update() {
        humanCount.setText("Guests: " +  simulation.getHumans().stream().filter(human ->  human instanceof Guest).count());
    }
}
