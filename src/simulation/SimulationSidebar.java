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
import simulation.pages.SidebarPage;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulationSidebar extends JPanel implements HotelEventListener {
    private JLabel emptyLabel;
    private JPanel topSection;
    private HashMap<SidebarPageType, SidebarPage> pages;
    private SimulationController simulationController;
    boolean visible;

    public SimulationSidebar() {
        this.setBackground(Settings.themeColor);
        this.setBorder(new MatteBorder(0, 0, 0, 1, Settings.themeColor2));
        this.setPreferredSize(new Dimension(Settings.sidebarWidth, Settings.schermHoogte));
        this.setLayout(new BorderLayout());
        this.visible = true;
        addNothingToShowLabel();
    }

    public void setSimulationController(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    public SimulationController getSimulationController() {
        return simulationController;
    }

    public void addNothingToShowLabel() {
        // Simulation not started
        this.emptyLabel = new MyLabel("Nothing to show yet.", FontWeight.REGULAR, TextSize.MEDIUM);
        emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(emptyLabel);
    }
    public void reset() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(emptyLabel);

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
        this.pages = new HashMap<>();
        this.pages.put(SidebarPageType.EVENTS, new EventsPage());

        this.removeAll();
        this.add(this.pages.get(SidebarPageType.EVENTS));

        for (HotelEvent event : this.simulationController.getHotelEvents()) {
            this.pages.get(SidebarPageType.EVENTS).reactToEvent(event);
        }
    }

    @Override
    public void notify(HotelEvent hotelEvent) {
        switch (hotelEvent.getEventType()) {
            case SPAWN_GUEST -> System.out.println();
        }
        this.pages.get(SidebarPageType.EVENTS).reactToEvent(hotelEvent);

    }
}
