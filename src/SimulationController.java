import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class SimulationController extends JPanel {
    private int runTime;
    private final Simulation simulation;
    private final Timer HTEtimer;

    public SimulationController(Simulation simulation) {
        this.runTime = 0;
        this.simulation = simulation;

        this.HTEtimer = new Timer(Settings.ticks, e -> {
            this.runTime += Settings.ticks;
            simulation.update();
        });

        this.setBackground(new Color(20, 20, 20 ,50));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setOpaque(false); // Avoid weird behavior when scrolling
        this.setPreferredSize(new Dimension(0, 48));
        this.setBackground(Color.WHITE);

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 2, 0,
                Color.LIGHT_GRAY), new EmptyBorder(10, 10, 10, 10)));

        this.addButtons();
    }

    public int getRunTime() {
        return runTime;
    }

    // Setup toolbar
    public void addButtons() {
        JButton startKnop = new JButton("Start");
        startKnop.setPreferredSize(new Dimension(32, 32));
        startKnop.setFocusable(false);
        startKnop.setAlignmentX(Component.RIGHT_ALIGNMENT);
        startKnop.setAlignmentY(Component.CENTER_ALIGNMENT);
        startKnop.addActionListener(e -> HTEtimer.start());
        this.add(startKnop);

        JButton pauzeerKnop = new JButton("Pause");
        pauzeerKnop.setPreferredSize(new Dimension(32, 32));
        pauzeerKnop.setFocusable(false);

        pauzeerKnop.addActionListener(e -> HTEtimer.stop());
        this.add(pauzeerKnop);
    }



}





