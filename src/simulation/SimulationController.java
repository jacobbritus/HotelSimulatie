package simulation;

import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationController extends JPanel {
    private final Simulation simulation;
    private final Timer HTEtimer;
    private final JLabel ticksLabel;
    private final JLabel timeLabel;
    private int runTime;
    private boolean paused;
    private boolean started;

    public SimulationController(Simulation simulation, ActionListener actionListener) {
        this.runTime = 0;
        this.simulation = simulation;

        this.timeLabel = new JLabel("00:00:00");

        this.HTEtimer = new Timer(Settings.ticks, e -> {
            this.runTime += 1000 / Settings.ticks ;
            this.timeLabel.setText(Settings.convertTime(this.runTime));
            simulation.update();
        });

        this.setBackground(Settings.themeColor);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setPreferredSize(new Dimension(0, 48));

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(10, 10, 10, 10)));


        // Children (compose later)


        this.add(createButton("Menu", actionListener));

        this.add(timeLabel);

        this.add(Box.createVerticalStrut(20)); // 5px gap

        this.ticksLabel = new JLabel("x" + (1000 / Settings.ticks));

        this.add(createButton("-", e -> {
            Settings.ticks = Math.min(Settings.ticks * 2, 1000);
            this.ticksLabel.setText("x" + (1000 / Settings.ticks));
            this.HTEtimer.setDelay(Settings.ticks);
        }));


        this.add(ticksLabel);

        this.add(createButton("+", e -> {
            Settings.ticks = Math.max(Settings.ticks / 2, 31);
            System.out.println(Settings.ticks);
            this.ticksLabel.setText("x" + (1000 / Settings.ticks));
            this.HTEtimer.setDelay(Settings.ticks);
        }));


        Component x = Box.createVerticalStrut(3);
        this.add(x); // 5px gap


        JButton startButton = createButton("Start", null);
        startButton.setForeground(Color.GREEN);


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

        startButton.addActionListener(e -> {
            if (this.started) {
                this.simulation.reset();
                this.runTime = 0;
                this.HTEtimer.stop();
                startButton.setForeground(Color.GREEN);
                startButton.setText("Start");
                this.timeLabel.setText("00:00:00");
              this.started = false;
            } else {
                this.started = true;
                this.HTEtimer.start();
                startButton.setText("Reset");
                startButton.setForeground(Color.RED);
                pauseButton.setForeground(Color.BLACK);
            }
        });
        this.add(startButton);
        this.add(pauseButton);
    }

    public int getRunTime() {
        return runTime;
    }




    // compose later
    public JButton createButton(String text, ActionListener actionListener) {
        JButton startKnop = new JButton(text);
        startKnop.setFocusable(false);
        startKnop.setAlignmentX(Component.RIGHT_ALIGNMENT);
        startKnop.addActionListener(actionListener);
        return startKnop;
    }
}





