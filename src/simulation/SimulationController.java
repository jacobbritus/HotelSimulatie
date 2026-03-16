package simulation;

import facility.Room;
import helper.FontHelper;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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

    public SimulationController(Simulation simulation, SimulationSidebar simulationSidebar) {
        this.runTime = 0;
        this.simulation = simulation;
        this.simulationSidebar = simulationSidebar;

        this.timeLabel = new JLabel("00:00:00  ");

        this.HTEtimer = new Timer(Settings.ticks, e -> {
            this.runTime += 1000 / Settings.ticks ;
            this.timeLabel.setText(Settings.convertTime(this.runTime) + "  ");
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

        this.ticksLabel = new JLabel("  " + (1000 / Settings.ticks) + "x  ");
        this.ticksLabel.setFont(FontHelper.getFont("Medium").deriveFont(12f));

        this.add(createButton("-", e -> {
            Settings.ticks = Math.min(Settings.ticks * 2, 1000);
            updateSpeed();
        }));


        this.add(ticksLabel);

        this.add(createButton("+", e -> {
            Settings.ticks = Math.max(Settings.ticks / 2, 31);
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
        pauseButton.setForeground(Color.LIGHT_GRAY);

        JButton startButton = createButton("Start", null);
        startButton.setForeground(new Color(99, 196, 74,255));

        startButton.addActionListener(e -> {
            if (this.started) {
                this.simulation.reset();
                this.runTime = 0;
                this.HTEtimer.stop();
                startButton.setForeground(new Color(99, 196, 74,255));
                startButton.setText("Start");
                this.timeLabel.setText("00:00:00  ");
              this.started = false;
            } else {
                this.started = true;
                this.simulationSidebar.init();
                this.HTEtimer.start();
                startButton.setText("Reset");
                startButton.setForeground(Color.RED);
                pauseButton.setForeground(Color.BLACK);
            }
        });

        this.add(timeLabel);
        this.add(startButton);
        this.add(pauseButton);
    }

    public int getRunTime() {
        return runTime;
    }

    public void updateSpeed() {
        this.ticksLabel.setText("  " + (1000 / Settings.ticks) + "x  ");
        this.HTEtimer.setDelay(Settings.ticks);
    }

    // compose later
    public JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(FontHelper.getFont("Medium").deriveFont(12f));
        button.setFocusable(false);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        button.addActionListener(actionListener);
        return button;
    }

    public void setShowRoom(Room room) {
        this.simulationSidebar.setShowRoom(room);
    }
}





