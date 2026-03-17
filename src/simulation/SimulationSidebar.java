package simulation;

import enums.FontWeight;
import enums.SidebarPageType;
import enums.TextSize;
import events.HotelEvent;
import events.HotelEventListener;
import helper.FontHelper;
import helper.MyLabel;
import settings.Settings;
import simulation.pages.EventsPage;
import simulation.pages.OverviewPage;
import simulation.pages.SidebarPage;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulationSidebar extends JPanel implements HotelEventListener {
    private JLabel emptyLabel;
    private JPanel pageHolder;
    private HashMap<SidebarPageType, SidebarPage> pages;
    private SimulationController simulationController;
    private SidebarNavigationPanel sidebarNavigationPanel;
    boolean visible;

    public SimulationSidebar() {
        this.setBackground(Settings.themeColor);
        this.setBorder(new MatteBorder(0, 0, 0, 1, Settings.themeColor2));
        this.setPreferredSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));
        this.setLayout(new BorderLayout());
        this.visible = true;
        this.pageHolder = new JPanel(new BorderLayout());
        this.pageHolder.setOpaque(false);
        this.add(pageHolder);
        addNothingToShowLabel();

        this.sidebarNavigationPanel = new SidebarNavigationPanel(this);


        this.add(sidebarNavigationPanel, BorderLayout.WEST);
    }

    public void setSimulationController(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    public SimulationController getSimulationController() {
        return simulationController;
    }

    public void addNothingToShowLabel() {
        // Simulation not started
        this.emptyLabel = new MyLabel("Nothing to show yet.", FontWeight.REGULAR, TextSize.SMALL);
        emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pageHolder.add(emptyLabel);
    }
    public void reset() {
        pageHolder.removeAll();
        pageHolder.add(emptyLabel);

        this.revalidate();
        this.repaint();
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
        pageHolder.removeAll();
        this.pages = new HashMap<>();
        this.pages.put(SidebarPageType.EVENTS, new EventsPage());
        this.pages.put(SidebarPageType.OVERVIEW, new OverviewPage());

        pageHolder.add(this.pages.get(SidebarPageType.OVERVIEW));

        for (HotelEvent event : this.simulationController.getHotelEvents()) {
            this.pages.get(SidebarPageType.EVENTS).reactToEvent(event);
        }
    }

    public void openPage(SidebarPageType page) {
        pageHolder.removeAll();
        pageHolder.add(this.pages.get(page));
        this.add(pageHolder);

        this.repaint();
        this.revalidate();
    }

    @Override
    public void notify(HotelEvent hotelEvent) {
        switch (hotelEvent.getEventType()) {
            case SPAWN_GUEST -> System.out.println();
        }

        this.pages.get(SidebarPageType.OVERVIEW).reactToEvent(hotelEvent);
        this.pages.get(SidebarPageType.EVENTS).reactToEvent(hotelEvent);

    }
}
