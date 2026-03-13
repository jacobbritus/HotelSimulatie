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

    public SimulationController(Simulation simulation) {
        this.runTime = 0;
        this.simulation = simulation;

        this.HTEtimer = new Timer(Settings.ticks, e -> {
            this.runTime += Settings.ticks;
            simulation.update();
        });
        this.ticksLabel = new JLabel("" + (110 - Settings.ticks));

        this.setBackground(new Color(20, 20, 20 ,50));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setOpaque(false); // Avoid weird behavior when scrolling
        this.setPreferredSize(new Dimension(0, 48));
        this.setBackground(Color.WHITE);

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Color.lightGray), new EmptyBorder(10, 10, 10, 10)));

        createButton("Start", e -> this.HTEtimer.start());
        createButton("Pause", e -> this.HTEtimer.stop());
        createButton("Decrease Speed", e -> {
            Settings.ticks = Math.min(Settings.ticks + 10, 100);
            this.ticksLabel.setText("" + (110 - Settings.ticks));
            this.HTEtimer.setDelay(Settings.ticks);
        });
        createButton("Increase Speed", e -> {
            Settings.ticks = Math.max(Settings.ticks - 10, 10);
            this.ticksLabel.setText("" + (110 - Settings.ticks));
            this.HTEtimer.setDelay(Settings.ticks);
        });

        this.add(
                new JLabel("Speed: ")
        );


        this.add(ticksLabel);
    }

    public int getRunTime() {
        return runTime;
    }

    // compose later
    public void createButton(String text, ActionListener actionListener) {
        JButton startKnop = new JButton(text);
        startKnop.setFocusable(false);

        startKnop.addActionListener(actionListener);
        this.add(startKnop);
    }





}





