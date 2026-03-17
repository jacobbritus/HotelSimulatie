import layout.LayoutParser;
import org.w3c.dom.Document;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Applicatie applicatie = new Applicatie();
        UIManager.put("Label.foreground", Settings.textColor);

        LayoutParser layoutParser = new LayoutParser();
        Document doc = layoutParser.loadFile("layouts/realistic2.layout");

        if (doc == null) {
            System.out.println("Kon layout niet laden.");
            return;
        }

        String[][] grid = layoutParser.convertLayout(doc);

        if (grid == null) {
            System.out.println("layout.Layout is ongeldig. Simulatie wordt niet gestart.");
            return;
        }

//        UUID uuid = new UUID();

        applicatie.startSimulatie(grid);
    }
}
