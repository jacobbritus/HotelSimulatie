// Document Object Model (DOM)
// Loads the entire XML file into the memory as a tree structure.

// Setup for parsing any XML file using DOM.

import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Applicatie applicatie = new Applicatie();

        LayoutParser layoutParser = new LayoutParser();
        Document doc = layoutParser.loadFile("layouts/small.layout");

        if (doc == null) {
            return;
        }

        applicatie.startSimulatie(layoutParser.convertLayout(doc));
    }
}
