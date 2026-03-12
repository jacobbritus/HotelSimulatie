import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Simulatie extends JPanel {
     Layout layout;
     SimulatieController simulatieController;


    public Simulatie(String[][] rauweGrid) {
        this.setLayout(new GridBagLayout()); // Zet layout in het midden
        this.setBackground(Instellingen.achtergrondKleur);
        this.setSize(new Dimension(Instellingen.schermBreedte, Instellingen.schermHoogte));
        this.setOpaque(true);

       layout = new Layout(rauweGrid);
       this.add(layout);
    }

    public void update() {
        System.out.println("yes");
    }

    public void zoom(int aantal) {
        Instellingen.oppervlakGrootte += aantal;


        // Nieuwe preferred size op basis van huidige grid
        Oppervlakte[][] r = layout.getRuimtes();
        int hoogte = r.length;
        int breedte = r[0].length;

        layout.setPreferredSize(new Dimension(
                Instellingen.oppervlakGrootte * breedte,
                Instellingen.oppervlakGrootte * hoogte
        ));

        layout.revalidate();
        layout.repaint();

//        this.layout.herlaad();
        this.layout.revalidate();


    }

}