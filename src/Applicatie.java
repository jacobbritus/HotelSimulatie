import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Applicatie extends JFrame implements KeyListener {
    Simulatie simulatie;

    public Applicatie() {
        this.setMinimumSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
    }

    public void startSimulatie(String[][] rauweGrid) {
        simulatie = new Simulatie(this, rauweGrid);
        this.setVisible(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            simulatie.zoom(100);
        } else if (e.getKeyChar() == '-') {
            simulatie.zoom(-100);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
}