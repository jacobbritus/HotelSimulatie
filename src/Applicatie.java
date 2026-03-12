import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Applicatie extends JFrame implements KeyListener {
    Layout layout;
    Simulatie simulatie;
    // test

    public Applicatie() {
        this.setMinimumSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centers the window
        this.addKeyListener(this);
    }

    public void startSimulatie(String[][] rauweGrid) {
        simulatie = new Simulatie(this, rauweGrid);
        // test
//        Vakje randomVakje = layout.getRuimtes()[4][2].vakjes[3][3];
//        Mens randomMens = new Mens(randomVakje);
//        randomVakje.zetMens(new Mens(randomVakje));
//        randomMens.getDestinatie();

        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            simulatie.zoom(100);
        } else if (e.getKeyChar() == '-') {
            simulatie.zoom(-100);
        }
    }


}
