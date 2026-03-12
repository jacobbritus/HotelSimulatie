import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulatie extends JPanel {
    Layout layout;
    SimulatieController simulatieController;
    List<Mens> mensen = new ArrayList<>();

    public Simulatie(JFrame frame, String[][] rauweGrid) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Instellingen.achtergrondKleur);
        this.setSize(new Dimension(640, 640));

        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setBorder(null);
        frame.getContentPane().add(scrollPane);

        layout = new Layout(rauweGrid, this);

        // Spawn 1 gast voor test
        Vakje start = layout.getRuimtes()[0][0].getVakjes()[0][0];
        Gast g = new Gast(start);
        mensen.add(g);

        simulatieController = new SimulatieController(this);
        simulatieController.start();
    }

    public void update() {
        for (Mens mens : mensen) {
            mens.beweeg();
        }
        layout.repaint();
    }

    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;
        this.layout.herlaad();
        this.layout.revalidate();
    }
}
