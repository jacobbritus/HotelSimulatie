package simulation;

import enums.SidebarPage;
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
import java.util.Map;
import java.util.function.Supplier;

public class SimulationSidebar extends JPanel {
    private final Simulation simulation;
    private final JLabel emptyLabel;
    private ArrayList<Component> componentsToUpdate;
    private HashMap <Statistic, HashMap<Statistic, String>> sections;
    private JPanel generalSection;
    private JPanel topSection;
    private final JPanel pagesButtonsPanel;

    private final ArrayList<String> openedPages;
    private Map<String, Runnable> pages;

    boolean visible;
    Room roomtoShow;



    public SimulationSidebar(Simulation simulation) {
        this.openedPages = new ArrayList<>();
        this.openedPages.add(SidebarPage.OVERVIEW.title);
        this.visible = true;
        this.roomtoShow = null;
        this.simulation = simulation;
        this.setBackground(Settings.themeColor);
        this.setPreferredSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));
        this.setBorder(new MatteBorder(0, 0, 0, 1, Settings.themeColor2));
        this.setLayout(new BorderLayout());
        this.setOpaque(true);

        // Simulation not started
        this.emptyLabel = new JLabel("Nothing to show yet.");
        emptyLabel.setFont(FontHelper.getFont("Regular").deriveFont(14f));
        emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(emptyLabel);

        // button page Panel to hold navigation to pages
         this.pagesButtonsPanel = new JPanel();

        pagesButtonsPanel.setOpaque(false);
        pagesButtonsPanel.setLayout(new BoxLayout(pagesButtonsPanel, BoxLayout.Y_AXIS));

        pagesButtonsPanel.setMaximumSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));

        pagesButtonsPanel.setBorder(new EmptyBorder(40 ,40, 30, 40));
//        pagesButtonsPanel.setBorder(new LineBorder(Color.RED, 5));

        // Stats to update
        this.componentsToUpdate = new ArrayList<>();
    }

    public ArrayList<String> getOpenedPages() {
        return openedPages;
    }

    public void reset() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(emptyLabel);
    }

    public void addTitle(String title) {
        this.topSection = new JPanel();
        this.topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(20, 15, 20, 0)));
        Dimension size = titleLabel.getPreferredSize();

        titleLabel.setPreferredSize(new Dimension(size.width + 50, size.height));
        titleLabel.setMaximumSize(new Dimension(Settings.sidebarWidth, 64));
        titleLabel.setFont(FontHelper.getFont("SemiBold").deriveFont(24f));

        topSection.add(titleLabel);

        this.add(topSection);
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



    public void init() {
        this.removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    public void getOverviewPage() {
        addTitle(SidebarPage.OVERVIEW.getTitle());


        // Iterate through the map that holds statistic type and their respective function
        // and create rows that can update their value;
        HashMap<Statistic, Supplier<String>> statistics = simulation.getStatisticsSupplierMap();

        for (StatisticSection section : StatisticSection.values()) {
            JPanel sectionPanel = new SidebarSection(this, section.getString());
            this.add(sectionPanel);
            for (Statistic statistic : statistics.keySet()) {
                if (statistic.getSection() != section) continue;

                String title = statistic.getTitle() ;
                Supplier<String> function = statistics.get(statistic);
                StatRow row = new StatRow(title, this, function, statistic.getUnit());
                row.update();
                this.componentsToUpdate.add(row);
                sectionPanel.add(row);
            }
        }

        this.repaint();
        this.revalidate();
    }

    // if stat page 1, else if navigation page 2
    public void update() {
        for (Component stat : this.componentsToUpdate) {
            ((StatRow) stat).update();
        }
    }
}
