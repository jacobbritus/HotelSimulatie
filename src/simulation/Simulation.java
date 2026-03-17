package simulation;

import enums.Role;
import enums.RoomStatus;
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
     private ArrayList<Human> humansLeaving;
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

    public void initializeStatisticSuppliers() {
        // Create a map that holds statistic type and their respective function returning a value in order to update it.
        // General
        this.statisticsSupplierMap.put(Statistic.GUEST_COUNT, () -> this.getHumans().stream()
                .filter(h -> h.getRole() == Role.GUEST).count() + "");

        this.statisticsSupplierMap.put(Statistic.CLEANER_COUNT, () -> this.getHumans().stream()
                .filter(h -> h.getRole() == Role.CLEANER).count() + "");
//
        // Rooms
        this.statisticsSupplierMap.put(Statistic.ROOMS_OCCUPIED, () -> {
            long occupied = layout.getRooms().stream()
                    .filter(r -> r.getStatus() == RoomStatus.UNAVAILABLE)
                    .count();

            int total = layout.getRooms().size();

            // Check for 0 to avoid "Division by Zero" errors if the hotel is empty
            if (total == 0) return "1";

            // Casting (double) forces Java to use floating-point math
            double percentage = (double) occupied / total * 100;

            // Use String.format to round to 1 decimal place (e.g., 85.5%)
            return String.format("%.1f", percentage);
        });
//
//        this.statisticsSupplierMap.put(Statistic.DirtyRooms, () -> this.layout.getRooms().stream()
//                .filter(r -> r.getStatus() == RoomStatus.DIRTY).count() + "");
//
//        this.statisticsSupplierMap.put(Statistic.TotalRoomsCleaned, () -> this.getHumans().stream()
//                .filter(h -> h instanceof Cleaner)
//                .mapToInt(i -> ((Cleaner) i).getRoomsCleaned()).sum() + "");
    }

    public void init() {
        layout = new Layout(rauweGrid, this.simulationController);
        testPanel.add(layout);
        this.add(testPanel);
        this.humans = new ArrayList<>();
        this.humansLeaving = new ArrayList<>();
    }

    public void addHumansLeaving(Human human) {
        this.humansLeaving.add(human);
    }

    @Override
    public void notify(HotelEvent hotelEvent) {
        switch (hotelEvent.getEventType()) {
            case SPAWN_GUEST -> {
                Tile tile = this.layout.getRandomTile(layout.getLobbies().getFirst());
                Guest guest = new Guest(tile, this.layout, hotelEvent.getId());
                humans.add(guest);
            }

            case CHECK_OUT -> {
                this.humansLeaving.addAll(humans.stream().filter(Human::isReadyToDespawn).toList());
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
            human.update();
        }

        humans.removeIf(h -> {
            if (h.isReadyToDespawn()) {
                removeHuman(h);
                return true;
            }
            return false;
        });

//        if (!humansLeaving.isEmpty()) {
//            for (Human human : humans) {
//                if (humansLeaving.contains(human)) {
//                    this.simulationController.removeListener(human);
//                    human.
//                }
//            }
//        }

    }

    public void removeHuman(Human h) {
        this.simulationController.removeListener(h);
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