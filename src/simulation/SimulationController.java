package simulation;

import facility.Room;
import helper.FontHelper;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationController extends JPanel {
    private final Simulation simulation;
    private final SimulationSidebar simulationSidebar;
    private final Timer HTEtimer;
    private final JLabel ticksLabel;
    private final JLabel timeLabel;
    private int runTime;
    private boolean paused;
    private boolean started;
    public final int[] speedMultipliers = {1, 2, 4, 8, 16, 32};
    public int activeSpeedMultiplier = 0;

    public SimulationController(Simulation simulation, SimulationSidebar simulationSidebar) {
        this.runTime = 0;
        this.simulation = simulation;
        this.simulationSidebar = simulationSidebar;

        this.timeLabel = new JLabel("00:00:00");

        this.HTEtimer = new Timer(Settings.delay, e -> {
            this.runTime += (1000 / Settings.delay);
            this.timeLabel.setText(Settings.convertTime(this.runTime));
            simulation.update();
        });

        this.setBackground(Settings.themeColor);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setPreferredSize(new Dimension(0, 48));

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(10, 10, 10, 10)));


        // Children (compose later)
        JButton menuButton = createButton("Close Menu", null);


        menuButton.addActionListener( e -> {
            if (this.simulationSidebar.toggle()) {
                menuButton.setText("Close Menu");
            } else {
                menuButton.setText("Open Menu");
            }
        });

        this.add(menuButton);


        this.add(Box.createVerticalStrut(20)); // 5px gap

        this.ticksLabel = new JLabel(this.speedMultipliers[this.activeSpeedMultiplier] + "x");
        this.ticksLabel.setFont(FontHelper.getFont("Medium").deriveFont(12f));

        this.add(createButton("-", e -> {
            this.activeSpeedMultiplier = Math.max(this.activeSpeedMultiplier - 1, 0);
            updateSpeed();
        }));

        this.add(Box.createHorizontalStrut(20));
        this.add(ticksLabel);
        this.add(Box.createHorizontalStrut(20));

        this.add(createButton("+", e -> {
            this.activeSpeedMultiplier = Math.min(this.activeSpeedMultiplier + 1, this.speedMultipliers.length - 1);
            updateSpeed();
        }));


        Component x = Box.createVerticalStrut(3);
        this.add(x); // 5px gap


        JButton pauseButton = createButton("Pause", null);

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

        JButton startButton = createButton("Start", null);
        startButton.setForeground(new Color(99, 196, 74,255));

        startButton.addActionListener(e -> {
            if (this.started) {
                this.simulation.reset();
                this.runTime = 0;
                this.HTEtimer.stop();
                startButton.setForeground(new Color(99, 196, 74,255));
                pauseButton.setForeground(Color.DARK_GRAY);
                startButton.setText("Start");
                this.timeLabel.setText("00:00:00");
                this.simulationSidebar.reset();
                this.started = false;
            } else {
                this.started = true;
                this.simulationSidebar.init();
                this.simulationSidebar.getOverviewPage();
                this.HTEtimer.start();
                startButton.setText("Reset");
                startButton.setForeground(Color.RED);
                pauseButton.setForeground(Settings.textColor);
            }
        });

        this.add(timeLabel);
        this.add(Box.createHorizontalStrut(20));
        this.add(startButton);
        this.add(pauseButton);
    }

    public int getRunTime() {
        return runTime;
    }

    public void updateSpeed() {
        Settings.delay = 1000 / this.speedMultipliers[activeSpeedMultiplier];
        this.ticksLabel.setText(this.speedMultipliers[this.activeSpeedMultiplier] + "x");
        this.HTEtimer.setDelay(Settings.delay);
    }

    // compose later
    public JButton createButton(String text, ActionListener actionListener) {
        JButton button = new SidebarButton(text);
        button.setFocusable(false);
        button.addActionListener(actionListener);
        return button;
    }

    public void setShowRoom(Room room) {
        this.simulationSidebar.setShowRoom(room);
    }
}





