import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Applicatie extends JFrame implements KeyListener {
    Layout layout;
    JPanel layoutHouder;
    // test

    public Applicatie() {
        this.setMinimumSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centers the window
        this.addKeyListener(this);
    }

    public void startSimulatie(String[][] rauweGrid) {
        layoutHouder = new JPanel(new GridBagLayout()); // Zet layout in het midden
        layoutHouder.setBackground(Instellingen.achtergrondKleur);
        layoutHouder.setSize(new Dimension(640, 640));

        JScrollPane scrollPane = new JScrollPane(layoutHouder);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(5);

        scrollPane.setBackground(Instellingen.achtergrondKleur);
        scrollPane.setBorder(null);

        this.getContentPane().add(scrollPane);

        layout = new Layout(
                rauweGrid,
                layoutHouder
        );
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
            zoom(100);
        } else if (e.getKeyChar() == '-') {
            zoom(-100);
        }
    }

    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;
        System.out.println(Instellingen.oppervlakGrootte);
        this.layout.herlaad();
        this.layout.revalidate();
    }
}
