package simulation.pages;

import enums.SidebarPageType;
import enums.Statistic;
import enums.StatisticSection;
import events.HotelEvent;
import facility.Tile;
import human.Guest;
import simulation.SidebarSection;
import simulation.StatRow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;

public class OverviewPage extends SidebarPage {
    private HashMap<Statistic, StatRow> statRows;
    private int guestCount;
    private int cleanerCount;
    private int eventCount;

    public OverviewPage() {
        addHeaderSection(SidebarPageType.OVERVIEW.getTitle());
        addUIdesign();
        this.guestCount = 0;
        this.cleanerCount = 0;
        this.eventCount = 0;
        this.statRows = new HashMap<>();

        for (StatisticSection section : StatisticSection.values()) {
            JPanel sectionPanel = new SidebarSection(section.getString());
            sectionPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            sectionPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            this.add(sectionPanel);
            sectionPanel.add(Box.createVerticalGlue());
            for (Statistic statistic : Statistic.values()) {
                if (statistic.getSection() != section) continue;
                String title = statistic.getTitle() ;
                StatRow row = new StatRow(title, this, statistic.getUnit());
                this.statRows.put(statistic, row);
                sectionPanel.add(row);
                sectionPanel.add(Box.createVerticalGlue());
            }
        }

        this.revalidate();
        this.repaint();
    }



    @Override
    public void reactToEvent(HotelEvent hotelEvent) {
        switch (hotelEvent.getEventType())  {
            case SPAWN_GUEST -> {
                guestCount++;
                statRows.get(Statistic.GUEST_COUNT).update(String.valueOf(guestCount));
            }
            case SPAWN_CLEANER -> {
                cleanerCount++;
                statRows.get(Statistic.CLEANER_COUNT).update(String.valueOf(cleanerCount));
            }
        }

        this.revalidate();
        this.repaint();
    }
}
