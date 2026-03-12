import javax.swing.*;
import java.awt.*;

public class Simulatie extends JPanel {
    Layout layout;
    SimulatieController simulatieController;

    public Simulatie(JFrame frame, String[][] rauweGrid) {
        this.setLayout(new GridBagLayout()); // Zet layout in het midden
        this.setBackground(Instellingen.achtergrondKleur);
        this.setSize(new Dimension(640, 640));

        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(5);

        scrollPane.setBackground(Instellingen.achtergrondKleur);
        scrollPane.setBorder(null);

        frame.getContentPane().add(scrollPane);

        layout = new Layout(
                rauweGrid,
                this
        );

        simulatieController = new SimulatieController(this);
        simulatieController.start();
    }

    public void update() {
        System.out.println("tik");
    }


    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;
        System.out.println(Instellingen.oppervlakGrootte);
        this.layout.herlaad();
        this.layout.revalidate();
    }
}
