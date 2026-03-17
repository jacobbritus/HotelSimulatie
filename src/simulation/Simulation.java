package simulation;

import enums.Statistic;
import events.HotelEvent;
import events.HotelEventListener;
import facility.Facility;
import facility.Tile;
import human.Guest;
import human.Human;
import layout.Layout;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class Simulation extends JPanel implements HotelEventListener {
     Layout layout;
     SimulationController simulationController;
     SimulationSidebar simulationSidebar;
     private ArrayList<Human> humans;
     private final JPanel testPanel;
     private final String[][] rauweGrid;
     private int millisecondsPassed;
     private int spawnCooldown;
     private HashMap <Statistic, Supplier<String>> statisticsSupplierMap;

//
    public Simulation(String[][] rauweGrid) {
        this.setLayout(new BorderLayout()); // Zet layout in het midden
        this.setSize(new Dimension(Settings.schermBreedte, Settings.schermHoogte));
        this.rauweGrid = rauweGrid;
        this.statisticsSupplierMap = new HashMap<>();
        this.spawnCooldown = Settings.guestBaseSpawnTime;

        testPanel = new JPanel(new GridBagLayout());
        testPanel.setBackground(Settings.achtergrondKleur);
        testPanel.setPreferredSize(new Dimension(Settings.schermBreedte , Settings.schermHoogte - 128 ));
    }

    public SimulationController getSimulationController() {
        return simulationController;
    }

    public Layout returnLayout() {
        return this.layout;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public HashMap<Statistic, Supplier<String>> getStatisticsSupplierMap() {
        return statisticsSupplierMap;
    }

    public void init() {
        layout = new Layout(rauweGrid, this.simulationController);
        testPanel.add(layout);
        this.add(testPanel);
        this.humans = new ArrayList<>();

        // Create a map that holds statistic type and their respective function returning a value in order to update it.
        // General
//        this.statisticsSupplierMap.put(Statistic.Guests, () -> this.getHumans().stream()
//                .filter(h -> h instanceof Guest).count() + "");
//        this.statisticsSupplierMap.put(Statistic.Cleaners, () -> this.getHumans().stream()
//                .filter(h -> h instanceof Cleaner).count() + "");
//
//        // Rooms
//        this.statisticsSupplierMap.put(Statistic.RoomsOccupied, () -> {
//            long occupied = layout.getRooms().stream()
//                    .filter(r -> r.getStatus() == RoomStatus.UNAVAILABLE)
//                    .count();
//
//            int total = layout.getRooms().size();
//
//            // Check for 0 to avoid "Division by Zero" errors if the hotel is empty
//            if (total == 0) return "1";
//
//            // Casting (double) forces Java to use floating-point math
//            double percentage = (double) occupied / total * 100;
//
//            // Use String.format to round to 1 decimal place (e.g., 85.5%)
//            return String.format("%.1f", percentage);
//        });
//
//        this.statisticsSupplierMap.put(Statistic.DirtyRooms, () -> this.layout.getRooms().stream()
//                .filter(r -> r.getStatus() == RoomStatus.DIRTY).count() + "");
//
//        this.statisticsSupplierMap.put(Statistic.TotalRoomsCleaned, () -> this.getHumans().stream()
//                .filter(h -> h instanceof Cleaner)
//                .mapToInt(i -> ((Cleaner) i).getRoomsCleaned()).sum() + "");

        // test
//        this.humans = new ArrayList<>();
//        for (int i = 0; i < 1; i++) {
//            Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
//            humans.add(new Guest(tile));
//        }
//
//        for (int i = 0; i < 7; i++) {
//            Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
//            humans.add(new Cleaner(tile));
//        }

    }


    @Override
    public void notify(HotelEvent hotelEvent) {
        switch (hotelEvent.getEventType()) {
            case SPAWN_GUEST -> {
                Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
                Guest guest = new Guest(tile, this.layout);
                humans.add(new Guest(tile, this.layout));
            }
        }
    }

    public void reset() {
        testPanel.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }


    public void setSimulationController(SimulationController simulatieController) {
        this.simulationController = simulatieController;
    }

    public void setSimulationSidebar(SimulationSidebar simulationSidebar) {
        this.simulationSidebar = simulationSidebar;
    }

    public void updateHumans() {
        for (Human human : this.humans) {
            human.update(this.layout);
        }
    }

    public void zoom(int aantal) {
        Settings.oppervlakGrootte += aantal;

        // Nieuwe preferred size op basis van huidige grid
        Facility[][] r = layout.getFacilities();
        int hoogte = r.length;
        int breedte = r[0].length;

        testPanel.setPreferredSize(new Dimension(
                Settings.oppervlakGrootte * breedte *3,
                Settings.oppervlakGrootte * hoogte *2));
        layout.setPreferredSize(new Dimension(
                Settings.oppervlakGrootte * breedte,
                Settings.oppervlakGrootte * hoogte));

        layout.revalidate();
        layout.repaint();

        this.layout.reload();
        this.layout.revalidate();
    }


}