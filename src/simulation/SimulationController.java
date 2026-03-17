package simulation;

import enums.FontWeight;
import enums.TextSize;
import events.HotelEvent;
import events.HotelEventListener;
import events.HotelEventType;
import helper.MyButton;
import helper.MyLabel;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SimulationController extends JPanel {
    private final Simulation simulation;
    private final SimulationSidebar simulationSidebar;
    private Timer HTEtimer;
    private MyLabel speedMultiplierLabel;
    private final JLabel timeLabel;
    private final JLabel ticksLabel;
    private int clockTime;
    private boolean paused;
    private boolean started;
    public final int[] speedMultipliers = {1, 2, 4, 8, 16, 32};
    public int activeSpeedMultiplier = 0;
    private ArrayList<HotelEvent> hotelEvents;

    private int eventTicks;
    private final ArrayList<HotelEventListener> hotelEventListeners;


    public SimulationController(Simulation simulation, SimulationSidebar simulationSidebar) {
        this.setBackground(Settings.themeColor);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setPreferredSize(new Dimension(0, 48));
        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(10, 10, 10, 10)));
        this.clockTime = 0;
        this.eventTicks = 0;
        this.simulation = simulation;
        this.simulationSidebar = simulationSidebar;
        this.timeLabel = new MyLabel("00:00:00", FontWeight.MEDIUM, TextSize.SMALL);
        this.ticksLabel = new MyLabel("Ticks: 0", FontWeight.MEDIUM, TextSize.SMALL);
        this.ticksLabel.setPreferredSize(new Dimension(75, 30));

        hotelEventListeners  = new ArrayList<>();
        hotelEventListeners.add(this.simulation);
        hotelEventListeners.add(this.simulationSidebar);

        initializeTimer();
        addControlButtons();
    }

    public ArrayList<HotelEvent> getHotelEvents() {
        return hotelEvents;
    }

    public void initializeTimer() {
        this.hotelEvents = new ArrayList<>();
        hotelEvents.add(new HotelEvent(HotelEventType.SPAWN_CLEANER, 10, 0, 255));
        hotelEvents.add(new HotelEvent(HotelEventType.SPAWN_CLEANER, 10, 0, 255));
        hotelEvents.add(new HotelEvent(HotelEventType.SPAWN_CLEANER, 10, 1, 255));
        hotelEvents.add(new HotelEvent(HotelEventType.SPAWN_CLEANER, 10, 2, 255));
        hotelEvents.add(new HotelEvent(HotelEventType.SPAWN_CLEANER, 10, 3, 255));

        hotelEvents.add(new HotelEvent(HotelEventType.CLEAN_ROOM, 150, 0, 255));
        hotelEvents.add(new HotelEvent(HotelEventType.GO_DIRTY_ROOM, 30, 0, 255));

        System.out.println();
        hotelEvents.sort(Comparator.comparing(HotelEvent::getTime));


        this.HTEtimer = new Timer(Settings.delay, e -> {
            this.clockTime += (1000 / Settings.delay);
            this.eventTicks++;
            this.timeLabel.setText(Settings.convertTime(this.clockTime));
            this.ticksLabel.setText("Ticks: " + String.valueOf(this.eventTicks));

            for (HotelEvent event : hotelEvents) {
                if (event.getTime() == this.eventTicks) {
                    for (HotelEventListener listener : hotelEventListeners) {
                        listener.notify(event);
                    }
                    if (event.getEventType() == HotelEventType.SPAWN_GUEST || event.getEventType() == HotelEventType.SPAWN_CLEANER) {
                        simulation.getHumans().stream()
                                .filter(h -> !this.hotelEventListeners.contains(h))
                                .forEach(this::registerListener);
                    }
                }
            }
            simulation.updateHumans();

        });
    }

    public void registerListener(HotelEventListener hotelEventListener) {
        this.hotelEventListeners.add(hotelEventListener);
    }

    public void removeListener(HotelEventListener hotelEventListener) {
        this.hotelEventListeners.remove(hotelEventListener);
    }


    public boolean isStarted() {
        return started;
    }

    public int getClockTime() {
        return clockTime;
    }

    public void updateSpeed() {
        Settings.delay = 1000 / this.speedMultipliers[activeSpeedMultiplier];
        this.speedMultiplierLabel.setText(this.speedMultipliers[this.activeSpeedMultiplier] + "x");
        this.HTEtimer.setDelay(Settings.delay);
    }

    public void addControlButtons() {
        MyButton menuButton = new MyButton("Close Menu", null);

        menuButton.addActionListener( e -> {
            if (this.simulationSidebar.toggle()) {
                menuButton.setText("Close Menu");
            } else {
                menuButton.setText("Open Menu");
            }
        });

        this.add(menuButton);
        this.add(Box.createVerticalStrut(20)); // 5px gap

        this.speedMultiplierLabel = new MyLabel(this.speedMultipliers[this.activeSpeedMultiplier] + "x",
                FontWeight.MEDIUM, TextSize.SMALL );

        this.add(new MyButton("-", e -> {
            this.activeSpeedMultiplier = Math.max(this.activeSpeedMultiplier - 1, 0);
            updateSpeed();
        }));

        this.add(Box.createHorizontalStrut(20));
        this.add(speedMultiplierLabel);
        this.add(Box.createHorizontalStrut(20));

        this.add(new MyButton("+", e -> {
            this.activeSpeedMultiplier = Math.min(this.activeSpeedMultiplier + 1, this.speedMultipliers.length - 1);
            updateSpeed();
        }));

        Component x = Box.createVerticalStrut(3);
        this.add(x); // 5px gap

        MyButton pauseButton = createPauseButton();
        MyButton startButton = createStartButton(pauseButton);

        this.add(ticksLabel);
        this.add(Box.createHorizontalStrut(20));
        this.add(timeLabel);
        this.add(Box.createHorizontalStrut(20));
        this.add(startButton);
        this.add(pauseButton);
    }

    private MyButton createPauseButton() {
        MyButton pauseButton = new MyButton("Pause", null);
        pauseButton.addActionListener(e -> {
            if (!started) return;
            if (this.paused) {
                pauseButton.setText("Pause");
                this.HTEtimer.start();
                this.paused = false;
            } else {
                pauseButton.setText("Resume");
                this.HTEtimer.stop();
                this.paused = true;
            }
        });
        pauseButton.setForeground(Color.DARK_GRAY);
        return pauseButton;
    }

    private MyButton createStartButton(JButton pauseButton) {
        MyButton startButton = new MyButton("Start", null);
        startButton.setForeground(new Color(99, 196, 74,255));
        startButton.addActionListener(e -> {
            if (this.started) {
                this.simulation.reset();
                this.clockTime = 0;
                this.eventTicks = 0;
                this.HTEtimer.stop();
                startButton.setForeground(new Color(99, 196, 74,255));
                pauseButton.setForeground(Color.DARK_GRAY);
                startButton.setText("Start");
                this.timeLabel.setText("00:00:00");
                this.ticksLabel.setText("Ticks: 0");
                this.simulationSidebar.reset();
                this.started = false;
            } else {
                this.started = true;
                this.simulation.initializeStatisticSuppliers();
                this.simulationSidebar.init();
                this.HTEtimer.start();
                startButton.setText("Reset");
                startButton.setForeground(Color.RED);
                pauseButton.setForeground(Settings.textColor);
            }
        });
        return startButton;
    }

    public SimulationSidebar getSimulationSidebar() {
        return simulationSidebar;
    }
}





