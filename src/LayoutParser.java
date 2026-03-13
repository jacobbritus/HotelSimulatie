//BELANGRIJK IMPORT NERGENS JAVA.AWT.*!! Deze importeert ook java.awt.List en werkt de java.util.List tegen. Dan werkt pathfinding niet meer!!!

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

public class LayoutParser {
    Integer gridColumns;
    Integer gridRows;

    public void validateGrid(Document doc) {
        Node gridNode = doc.getElementsByTagName("grid").item(0);

        if (gridNode == null) {
            System.out.println("Bestand bevat geen grid tag.");
            return;
        }

        Element gridElement = (Element) gridNode;

        try {
            this.gridColumns = Integer.parseInt(gridElement.getAttribute("columns"));
            this.gridRows = Integer.parseInt(gridElement.getAttribute("rows"));
        } catch (NumberFormatException e) {
            System.out.println("Grid heeft geen rows en/of columns attributen.");
        }
    }

    public Map<String, Integer> getLabelAttributes(Element label, int index) {
        //  1. Read span attributes
        int rowSpan = label.getAttribute("rowSpan").isEmpty() ? 1 : Integer.parseInt(label.getAttribute("rowSpan"));
        int colSpan = label.getAttribute("colSpan").isEmpty() ? 1 : Integer.parseInt(label.getAttribute("colSpan"));

        //  2. Read explicit position
        String rowAttr = label.getAttribute("row");
        String colAttr = label.getAttribute("col");

        int row , col;

        // 3. Parse attributes, else calculate using current index and grid columns
        try {
            row = Integer.parseInt(rowAttr);
            col = Integer.parseInt(colAttr);
        } catch (NumberFormatException _) {
            row = index / this.gridColumns;
            col = index % this.gridColumns;
        }

        return Map.ofEntries(
                entry("rowSpan", rowSpan),
                entry("colSpan", colSpan),
                entry("row", row),
                entry("col", col)
        );
    }


    public String[][] convertLayout(Document doc) {
        validateGrid(doc);
        if (this.gridColumns == null || this.gridRows == null) return null;

        String[][] grid = new String[this.gridRows][this.gridColumns];
        boolean[][] occupied = new boolean[this.gridRows][this.gridColumns];

        NodeList labels = doc.getElementsByTagName("label");

        // 1. Plaats ALLE labels
        for (int i = 0; i < labels.getLength(); i++) {
            Node child = labels.item(i);
            if (child.getNodeType() != Node.ELEMENT_NODE) continue;

            Element label = (Element) child;

            Map<String, Integer> labelAttributes = getLabelAttributes(label, i);
            int rowSpan = labelAttributes.get("rowSpan");
            int colSpan = labelAttributes.get("colSpan");
            int row = labelAttributes.get("row");
            int col = labelAttributes.get("col");

            // Check of label binnen grid past
            if (row + rowSpan > gridRows || col + colSpan > gridColumns) {
                System.out.println("Label past niet in grid: " + label.getAttribute("text"));
                return null;
            }

            // Check op overlap
            for (int dr = 0; dr < rowSpan; dr++) {
                for (int dc = 0; dc < colSpan; dc++) {
                    if (occupied[row + dr][col + dc]) {
                        System.out.println("Overlap gedetecteerd bij label: " + label.getAttribute("text"));
                        return null;
                    }
                }
            }

            // Plaats label
            for (int dr = 0; dr < rowSpan; dr++) {
                for (int dc = 0; dc < colSpan; dc++) {
                    occupied[row + dr][col + dc] = true;
                    grid[row + dr][col + dc] = label.getAttribute("text");
                }
            }
        }

        // validatie, kijken of alle verplichte elementen bestaan
        boolean lobbyAanwezig = false;
        boolean trapAanwezig = false;
        boolean liftAanwezig = false;

        for (String[] strings : grid) {
            for (int c = 0; c < grid[0].length; c++) {
                String cel = strings[c];
                if (cel == null) continue;

                switch (cel) {
                    case "Lobby" -> lobbyAanwezig = true;
                    case "Trap" -> trapAanwezig = true;
                    case "Lift" -> liftAanwezig = true;
                }
            }
        }

        if (!lobbyAanwezig || !trapAanwezig || !liftAanwezig) {
            System.out.println("FOUT: Layout mist verplichte elementen:");
            if (!lobbyAanwezig) System.out.println("- Lobby ontbreekt");
            if (!trapAanwezig)  System.out.println("- Trap ontbreekt");
            if (!liftAanwezig)  System.out.println("- Lift ontbreekt");
            return null;
        }

        System.out.println(Arrays.deepToString(grid));
        return grid;
    }

    public Document loadFile(String imagePath) {
        // 1. Create a File object that points to the XML file.
        File file = new File(imagePath);

        // 2. Get an instance of Document Builder Factory
        //    and use it to create a document builder
        // DBF is like a machine that producers builders.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc;

        try {
            // 3. The tool that actually performs the parsing.
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 4. Reads the XML file and turns it into a Document Object.
            doc = builder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
            System.out.println("Kon het bestand niet vinden.");
            doc = null;
        }
        if (doc != null) doc.getDocumentElement().normalize(); // Maakt het document schoon door bijv. whitespace te wissen.
        return doc;
    }
}
