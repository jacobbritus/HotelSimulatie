// BELANGRIJK: importeer NOOIT java.awt.*
// Dit kan java.awt.List binnenhalen en dat botst met java.util.List.

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;

public class Applicatie extends JFrame implements KeyListener {

    private Simulatie simulatie;
    private final JFrame debugFrame;
    private final JLabel debugLabel;

    public Applicatie() {
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        debugFrame = new JFrame("Debug Info");
        debugFrame.setSize(300, 200);
        debugFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        debugLabel = new JLabel();
        debugFrame.add(debugLabel);

        debugFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                Instellingen.debugAan = false;
                System.out.println("Debug overlay: UIT");
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        JPanel topBar = new JPanel();
        JButton heatmapKnop = new JButton("Heatmap aan/uit");

        heatmapKnop.addActionListener(e -> {
            Instellingen.heatmapAan = !Instellingen.heatmapAan;
            System.out.println("Heatmap: " + (Instellingen.heatmapAan ? "AAN" : "UIT"));

            if (simulatie != null) {
                simulatie.updateHeatmap();
                simulatie.repaint();
            }
            this.requestFocusInWindow();
        });

        topBar.add(heatmapKnop);

        // Topbar bovenaan het scherm
        this.add(topBar, BorderLayout.NORTH);
    }

    public void startSimulatie(String[][] rauweGrid) {
        simulatie = new Simulatie(this, rauweGrid);
        this.add(simulatie, BorderLayout.CENTER);

        this.setVisible(true); // eerst zichtbaar maken

        simulatie.setFocusable(false);

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }

    @Override
     public void keyReleased(KeyEvent e) {
        if (simulatie == null) return;

        switch (e.getKeyChar()) {
            case '1' -> simulatie.setTickSpeed(1000);
            case '2' -> simulatie.setTickSpeed(500);
            case '3' -> simulatie.setTickSpeed(250);
            case '4' -> simulatie.setTickSpeed(100);

            case '0' -> {
                Instellingen.debugAan = !Instellingen.debugAan;
                System.out.println("Debug overlay: " + (Instellingen.debugAan ? "AAN" : "UIT"));
                debugFrame.setVisible(Instellingen.debugAan);
                simulatie.repaint(); // direct effect
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}

    public void updateDebug(int gasten, int tick, boolean heatmap, int max) {
        debugLabel.setText("<html>Gasten: " + gasten + "<br>Tick: " + tick + "<br>Heatmap: " + (heatmap ? "AAN" : "UIT") + "<br>Debug: AAN<br>Max gasten: " + max + "</html>");
    }
}