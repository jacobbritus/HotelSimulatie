import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationController extends JPanel {
    private int runTime;
    private final Simulation simulation;
    private final Timer HTEtimer;
    private final JLabel ticksLabel;
    private boolean paused;
    private boolean started;

    public SimulationController(Simulation simulation, ActionListener actionListener) {
        this.runTime = 0;
        this.simulation = simulation;

        this.HTEtimer = new Timer(Settings.ticks, e -> {
            this.runTime += Settings.ticks;
            simulation.update();
        });
        this.ticksLabel = new JLabel("" + (110 - Settings.ticks));

        this.setBackground(Settings.themeColor);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
//        this.setOpaque(true); // Avoid weird behavior when scrolling
        this.setPreferredSize(new Dimension(0, 48));

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(10, 10, 10, 10)));


        this.add(createButton("≡", actionListener));

        this.add(Box.createVerticalStrut(20)); // 5px gap

        this.add(createButton("-", e -> {
            Settings.ticks = Math.min(Settings.ticks + 10, 100);
            this.ticksLabel.setText("" + (110 - Settings.ticks));
            this.HTEtimer.setDelay(Settings.ticks);
        }));

        this.add(
                new JLabel("Speed: ")
        );
        this.add(ticksLabel);

        this.add(createButton("+", e -> {
            Settings.ticks = Math.max(Settings.ticks - 10, 10);
            this.ticksLabel.setText("" + (110 - Settings.ticks));
            this.HTEtimer.setDelay(Settings.ticks);
        }));


        Component x = Box.createVerticalStrut(3);
        this.add(x); // 5px gap


        JButton startButton = createButton("Start", null);


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
                this.HTEtimer.stop();
                startButton.setText("Start");
                pauseButton.setForeground(Color.GRAY);
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




//        createButton("Reset", e -> {
//            this.simulation.reset();
//            this.HTEtimer.stop();
//        });
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





