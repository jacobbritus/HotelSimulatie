// BELANGRIJK: importeer NOOIT java.awt.*
// Dit kan java.awt.List binnenhalen en dat botst met java.util.List.

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Applicatie extends JFrame implements KeyListener {
<<<<<<< Updated upstream

    private Simulatie simulatie;
=======
    Simulatie simulatie;
>>>>>>> Stashed changes

    public Applicatie() {
        this.setSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
    }

    public void startSimulatie(String[][] rauweGrid) {
<<<<<<< Updated upstream
        simulatie = new Simulatie(this, rauweGrid);
=======
        simulatie = new Simulatie(rauweGrid);

        SimulatieController simulatieController = new SimulatieController(simulatie);

        JScrollPane scrollPane = new JScrollPane(simulatie);

        // Change scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(5);

        scrollPane.setBackground(Instellingen.achtergrondKleur);
        scrollPane.setBorder(null); // It has a border for some reason

        this.getContentPane().add(scrollPane);

        // Put the controller (Top bar UI) at the top of the screen
        scrollPane.setColumnHeaderView(simulatieController);


        // This is done to avoid empty spots around the border
        JPanel cornerTopRight = new JPanel();
        cornerTopRight.setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, cornerTopRight);

        JPanel cornerBottomRight = new JPanel();
        cornerTopRight.setBackground(Color.WHITE);
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerBottomRight);


        // test
//        Vakje randomVakje = layout.getRuimtes()[4][2].vakjes[3][3];
//        Mens randomMens = new Mens(randomVakje);
//        randomVakje.zetMens(new Mens(randomVakje));
//        randomMens.getDestinatie();

>>>>>>> Stashed changes
        this.setVisible(true);
    }

    @Override
<<<<<<< Updated upstream
    public void keyReleased(KeyEvent e) {
        if (simulatie == null) return;

        switch (e.getKeyChar()) {
            case '1' -> simulatie.setTickSpeed(1000);
            case '2' -> simulatie.setTickSpeed(500);
            case '3' -> simulatie.setTickSpeed(250);
            case '4' -> simulatie.setTickSpeed(100);
        }
=======
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            simulatie.zoom(Instellingen.oppervlakVakjes);
        } else if (e.getKeyChar() == '-') {
            simulatie.zoom(Instellingen.oppervlakVakjes * -1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

>>>>>>> Stashed changes
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
}