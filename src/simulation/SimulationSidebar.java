package simulation;

import enums.Statistic;
import enums.StatisticSection;
import facility.Room;
import helper.FontHelper;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class SimulationSidebar extends JPanel {
    private Simulation simulation;
    private JLabel humanCount;
    private JLabel title;
    private JPanel overviewPage;
    private ArrayList<SidebarRow> rows;
    private HashMap <Statistic, HashMap<Statistic, String>> sections;
    private JPanel generalSection;

    boolean visible;
    Room roomtoShow;



    public SimulationSidebar(Simulation simulation) {
        this.visible = true;
        this.roomtoShow = null;
        this.simulation = simulation;
        this.setBackground(Settings.themeColor);
        this.setPreferredSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));
        this.setBorder(new MatteBorder(0, 0, 0, 1, Settings.themeColor2));

        this.setLayout(new BorderLayout());

        this.setOpaque(true);

        JLabel label = new JLabel("Nothing to show yet.");
        label.setFont(FontHelper.getFont("Regular").deriveFont(14f));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(label);

        this.rows = new ArrayList<>();
    }

    public void addTitle() {
        this.title = new JLabel("Hotel Overview");
        title.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(20, 10, 20, 0)));
        Dimension size = title.getPreferredSize();
        title.setPreferredSize(new Dimension(size.width + 50, size.height));

        title.setMaximumSize(new Dimension(Settings.sidebarWidth, 64));

        this.title.setFont(FontHelper.getFont("Bold").deriveFont(24f));

        this.add(title);
    }

    public void toggle() {
        if (this.visible) {
            this.setPreferredSize(new Dimension(0, Settings.schermHoogte));
            this.visible = false;
        } else {
            this.visible = true;
            this.setPreferredSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));
        }
        this.revalidate();
        this.repaint();
    }

    public void setShowRoom(Room roomtoShow) {
        this.roomtoShow = roomtoShow;
    }

    public void init() {
        this.removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));

        addTitle();

        // Iterate through the map that holds statistic type and their respective function
        // and create rows that can update their value;

        HashMap<Statistic, Supplier<String>> statistics = simulation.getStatisticsSupplierMap();

        for (StatisticSection section : StatisticSection.values()) {
            JPanel sectionPanel = new SidebarSection(this, section.getString());

            this.add(sectionPanel);

            for (Statistic statistic : statistics.keySet()) {
                if (!statistic.getSection().equals(section.getString())) continue;
                String title = statistic.getString() + ": ";
                Supplier<String> function = statistics.get(statistic);
                SidebarRow row = new SidebarRow(title, this, function);

                this.rows.add(row);
               sectionPanel.add(row);
            }
        }


        this.repaint();
        this.revalidate();

        for (SidebarRow stat : this.rows) {
            stat.update();
        }
    }



    public void update() {
        for (SidebarRow stat : this.rows) {
           stat.update();
        }

//        if (roomtoShow != null) {
//            humanCount.setText(Settings.convertTime(this.roomtoShow.getGuest().getLifeTime()));
//        } else {
//            humanCount.setText("Guests: " +  simulation.getHumans().stream().filter(human ->  human instanceof Guest).count());
//        }
    }
}
