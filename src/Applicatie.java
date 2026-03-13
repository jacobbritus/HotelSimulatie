// BELANGRIJK: importeer NOOIT java.awt.*
// Dit kan java.awt.List binnenhalen en dat botst met java.util.List.

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;

public class Applicatie extends JFrame implements KeyListener {

    private Simulatie simulatie;

    public Applicatie() {
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        JPanel topBar = new JPanel();
        JButton heatmapKnop = new JButton("Heatmap aan/uit");

        heatmapKnop.addActionListener(e -> {
            Instellingen.heatmapAan = !Instellingen.heatmapAan;
            System.out.println("Heatmap: " + (Instellingen.heatmapAan ? "AAN" : "UIT"));

            if (simulatie != null) {
                simulatie.repaint();
            }
        });

        topBar.add(heatmapKnop);

        // Topbar bovenaan het scherm
        this.add(topBar, BorderLayout.NORTH);
    }

    public void startSimulatie(String[][] rauweGrid) {
        simulatie = new Simulatie(this, rauweGrid);
        this.add(simulatie, BorderLayout.CENTER);
        this.setVisible(true);
        simulatie.addKeyListener(this);
        simulatie.setFocusable(true);
        simulatie.requestFocusInWindow();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (simulatie == null) return;

        switch (e.getKeyChar()) {
            case '1' -> simulatie.setTickSpeed(1000);
            case '2' -> simulatie.setTickSpeed(500);
            case '3' -> simulatie.setTickSpeed(250);
            case '4' -> simulatie.setTickSpeed(100);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
}