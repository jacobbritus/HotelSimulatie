package simulation;

import enums.FacilityState;
import enums.SidebarPage;
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
    private final Simulation simulation;
    private final JLabel emptyLabel;
    private ArrayList<Component> componentsToUpdate;
    private HashMap <Statistic, HashMap<Statistic, String>> sections;
    private JPanel generalSection;
    private JPanel topSection;
    private final JPanel pagesButtonsPanel;

    private final ArrayList<SidebarPage> openedPages;
    private HashMap<SidebarPage, Runnable> pages;

    boolean visible;
    Room roomtoShow;



    public SimulationSidebar(Simulation simulation) {
        this.openedPages = new ArrayList<>();
        this.openedPages.add(SidebarPage.OVERVIEW);

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
        this.initializePages();
    }

    public void reset() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(emptyLabel);
    }

    public void initializePages() {
        this.pages = new HashMap<>();

        for (SidebarPage page : SidebarPage.values()) {
            switch (page) {
                case ROOMS -> this.pages.put(page, this::getRoomsPage);

                case OVERVIEW -> this.pages.put(page, this::getOverviewPage);
            }
        }
    }

    public void addTitle(String title) {
        this.topSection = new JPanel();
        this.topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        if (this.openedPages.size() > 1) {
            topSection.setBorder(new EmptyBorder(10 ,0, 10, 0));
            addBackButton();
        }

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

    public void addBackButton() {
        SidebarButton sidebarButton = new SidebarButton("<");
        sidebarButton.setMaximumSize(new Dimension(64, 32));
        sidebarButton.addActionListener(e -> {
            this.openedPages.removeLast();
            SidebarPage lastPage = this.openedPages.getLast();
            System.out.println(this.openedPages);
            System.out.println(lastPage);
            this.pages.get(lastPage).run();
        });
        this.topSection.add(sidebarButton);
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

    public void openNewPage(SidebarPage activePage) {
        this.removeAll();
        this.componentsToUpdate = new ArrayList<>();
        addTitle(activePage.getTitle());
        this.pagesButtonsPanel.removeAll();
    }

    public void updateRoomButton(JButton button, Room  room) {
        Color[] activeColors = room.getActiveColors();

//        button.setBackground(activeColors[0]);
        button.setBorderPainted(true);
        button.setBorder(new LineBorder(activeColors[1], 2));
    }


    public void getRoomsPage() {
        openNewPage(SidebarPage.ROOMS);


//        HashMap<Statistic, Supplier<String>> statistics = simulation.getStatisticsSupplierMap();
//
//        StatisticSection section = StatisticSection.Rooms;
//            JPanel sectionPanel = new SidebarSection(this, null);
//
//            this.add(sectionPanel);
//
//            for (Statistic statistic : statistics.keySet()) {
//                if (!statistic.getSection().equals(section.getString())) continue;
//                String title = statistic.getString() ;
//                Supplier<String> function = statistics.get(statistic);
//                StatRow row = new StatRow(title, this, function, statistic.getUnit());
//                this.componentsToUpdate.add(row);
//                row.update();
//                this.add(row);
//                sectionPanel.add(row);
//            }

        ArrayList<Room> rooms = this.simulation.returnLayout().getRooms();
        for (int i = 0; i < rooms.size() ; i++) {
            Room room = rooms.get(i);

            pagesButtonsPanel.setOpaque(false);
            pagesButtonsPanel.setLayout(new BoxLayout(pagesButtonsPanel, BoxLayout.Y_AXIS));
            pagesButtonsPanel.add(Box.createVerticalStrut(10));
            SidebarButton button = new SidebarButton("Room " + (i + 1));

            updateRoomButton(button, room);


            this.componentsToUpdate.add(button);
            pagesButtonsPanel.add(button);
        }

        pagesButtonsPanel.setMaximumSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte ));
        this.add(pagesButtonsPanel);

        this.revalidate();
        this.repaint();

    }

    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    public void getOverviewPage() {
        openNewPage(SidebarPage.OVERVIEW);

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
                row.update();
                this.componentsToUpdate.add(row);
                sectionPanel.add(row);
            }
        }


        for (SidebarPage page : SidebarPage.values()) {
            if (page == SidebarPage.SETTINGS) pagesButtonsPanel.add(Box.createVerticalGlue());
            else if (page == SidebarPage.OVERVIEW) continue;
            SidebarButton button = new SidebarButton(page.getTitle());
            button.addActionListener(_ -> {
                this.openedPages.add(page);
                this.pages.get(page).run();
            });
            pagesButtonsPanel.add(button);
            pagesButtonsPanel.add(Box.createVerticalStrut(10));
        }

        this.add(pagesButtonsPanel);

        this.repaint();
        this.revalidate();
    }

    // if stat page 1, else if navigation page 2
    public void update() {
        switch (openedPages.getLast()) {
            case OVERVIEW -> {
                for (Component stat : this.componentsToUpdate) {
                    ((StatRow) stat).update();
                }
            }

            case ROOMS -> {
                this.pagesButtonsPanel.getComponents();
                ArrayList<Room> rooms = this.simulation.returnLayout().getRooms();
                for (int i = 0; i < rooms.size(); i++) {
                    Room room = rooms.get(i);
                    SidebarButton button =  (SidebarButton) componentsToUpdate.get(i);

                    updateRoomButton(button, room);
                }
            }
        }

//        if (roomtoShow != null) {
//            humanCount.setText(Settings.convertTime(this.roomtoShow.getGuest().getLifeTime()));
//        } else {
//            humanCount.setText("Guests: " +  simulation.getHumans().stream().filter(human ->  human instanceof Guest).count());
//        }
    }
}
