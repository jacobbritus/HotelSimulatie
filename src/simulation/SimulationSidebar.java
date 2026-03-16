package simulation;

import enums.Statistic;
import enums.StatisticSection;
import facility.Room;
import helper.FontHelper;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
    private ArrayList<StatRow> rows;
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
                Settings.themeColor2), new EmptyBorder(20, 15, 20, 0)));
        Dimension size = title.getPreferredSize();

        title.setPreferredSize(new Dimension(size.width + 50, size.height));
        title.setMaximumSize(new Dimension(Settings.sidebarWidth, 64));
        title.setFont(FontHelper.getFont("SemiBold").deriveFont(24f));

        this.add(title);
    }

    public boolean toggle() {
        if (this.visible) {
            this.setPreferredSize(new Dimension(0, Settings.schermHoogte));
            this.visible = false;
        } else {
            this.visible = true;
            this.setPreferredSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));
        }
        this.revalidate();
        this.repaint();

        return this.visible;
    }

    public void setShowRoom(Room roomtoShow) {
        this.roomtoShow = roomtoShow;
    }

    public void init() {
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 0, 0, 0));

        addTitle();

        // Iterate through the map that holds statistic type and their respective function
        // and create rows that can update their value;

        HashMap<Statistic, Supplier<String>> statistics = simulation.getStatisticsSupplierMap();

        for (StatisticSection section : StatisticSection.values()) {
            JPanel sectionPanel = new SidebarSection(this, section.getString());

            this.add(sectionPanel);

            for (Statistic statistic : statistics.keySet()) {
                if (!statistic.getSection().equals(section.getString())) continue;
                String title = statistic.getString() ;
                Supplier<String> function = statistics.get(statistic);
                StatRow row = new StatRow(title, this, function, statistic.getUnit());

                this.rows.add(row);
               sectionPanel.add(row);
            }


        }

        JPanel buttonpanel = new JPanel();
        buttonpanel.setOpaque(false);
        buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.Y_AXIS));

        SidebarButton test = new SidebarButton("Rooms");

        JProgressBar b = new JProgressBar();

        // set initial value



        buttonpanel.add(test);
        buttonpanel.add(Box.createVerticalStrut(10));
        buttonpanel.add(new SidebarButton("Guests"));
        buttonpanel.add(Box.createVerticalStrut(10));
        buttonpanel.add(new SidebarButton("Something"));
        buttonpanel.add(Box.createVerticalStrut(10));
        buttonpanel.add(new SidebarButton("Cleaners"));

        buttonpanel.add(Box.createVerticalGlue());
        buttonpanel.add(Box.createVerticalStrut(10));
        buttonpanel.add(new SidebarButton("Settings"));
        buttonpanel.add(Box.createVerticalStrut(10));
        buttonpanel.add(new SidebarButton("Quit Simulation"));


        buttonpanel.setMaximumSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));

        buttonpanel.setBorder(new EmptyBorder(15 ,15, 15, 15));

        this.add(buttonpanel);

        this.repaint();
        this.revalidate();

        for (StatRow stat : this.rows) {
            stat.update();
        }
    }



    public void update() {
        for (StatRow stat : this.rows) {
           stat.update();
        }

//        if (roomtoShow != null) {
//            humanCount.setText(Settings.convertTime(this.roomtoShow.getGuest().getLifeTime()));
//        } else {
//            humanCount.setText("Guests: " +  simulation.getHumans().stream().filter(human ->  human instanceof Guest).count());
//        }
    }
}
