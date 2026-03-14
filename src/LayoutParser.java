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
import java.util.Map;

import static java.util.Map.entry;

public class LayoutParser {
    Integer gridColumns;
    Integer gridRows;
    String fill; // The element to fill the grid with if a spot wasn't occupied

    public LayoutParser(){
        this.fill = "";
    }

    public boolean validGrid(Document doc) {
        Node gridNode = doc.getElementsByTagName("grid").item(0);

        if (gridNode == null) {
            System.out.println("Bestand bevat geen grid tag.");
            return false;
        }

        Element gridElement = (Element) gridNode;

        try {
            this.gridColumns = Integer.parseInt(gridElement.getAttribute("columns"));
            this.gridRows = Integer.parseInt(gridElement.getAttribute("rows"));
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Grid heeft geen rows en/of columns attributen.");
        }

        return false;
    }

    public Map<String, Integer> getLabelAttributes(Element label, int index) {
        // Read explicit position
        String rowAttr = label.getAttribute("row");
        String colAttr = label.getAttribute("column");

        int row, col;

        if (!rowAttr.isEmpty()) row = Integer.parseInt(rowAttr);
        else row = index / this.gridColumns;
        if (!colAttr.isEmpty()) col = Integer.parseInt(colAttr);
        else col = index % this.gridColumns;

        //  Read span attributes and check if it fits
        int rowSpan = label.getAttribute("rowSpan").isEmpty() ? 1 :
                Integer.parseInt(label.getAttribute("rowSpan")) + row > this.gridRows - 1 ? 1 :
                Integer.parseInt(label.getAttribute("rowSpan"));
        int colSpan = label.getAttribute("colSpan").isEmpty() ? 1 :
                Integer.parseInt(label.getAttribute("colSpan")) + col > this.gridColumns - 1 ? 1 :
                Integer.parseInt(label.getAttribute("colSpan"));

        return Map.ofEntries(
                entry("rowSpan", rowSpan),
                entry("colSpan", colSpan),
                entry("row", row),
                entry("col", col)
        );
    }


    public String[][] convertLayout(Document doc) {
        if (!validGrid(doc)) return null;

        String[][] grid = new String[this.gridRows][this.gridColumns];
        NodeList labels = doc.getElementsByTagName("label");

        boolean[][] occupied = new boolean[this.gridRows][this.gridColumns]; // Track which cells are taken

        for (int i = 0; i < labels.getLength(); i++) {
            Node child = labels.item(i);

            if (child.getNodeType() != Node.ELEMENT_NODE) continue; // skip #text nodes

            Element label = (Element) child;

            if (!label.getAttribute("fill").isEmpty()) {
                this.fill = label.getAttribute("text");
                continue;
            }

            Map<String, Integer> labelAttributes = getLabelAttributes(label, i);
            int rowSpan = labelAttributes.get("rowSpan");
            int colSpan = labelAttributes.get("colSpan");
            int row = labelAttributes.get("row");
            int col = labelAttributes.get("col");

            boolean placed = false;

            // 4. Automatic placement: scan grid
            outer:
            for (int r = 0; r < this.gridRows; r++) {
                for (int c = 0; c < this.gridColumns; c++) {
                    boolean fits = true;

                    // Check whether the label is too big or the space is already occupied


                        for (int dr = 0; dr < rowSpan; dr++)
                            for (int dc = 0; dc < colSpan; dc++)
                                if (r + dr >= this.gridRows || c + dc >= this.gridColumns || occupied[r + dr][c + dc]) {
                                    fits = false;
                                    break; // Exit this check loop
                                }

                    // Place the label text in the grid and mark the space as occupied.
                    if (fits) {
                        // Mark occupied
                        for (int dr = 0; dr < rowSpan; dr++)
                            for (int dc = 0; dc < colSpan; dc++) {
                                occupied[row + dr][col + dc] = true;
                                grid[row + dr][col + dc] = label.getAttribute("text");
                            }
                        break outer; // Labels have been placed successfully
                    }
                }
            }
        }

        for (int r = 0; r < occupied.length; r++) {
            for (int c = 0; c < occupied[0].length; c++) {
                if (!occupied[r][c]) {
                    grid[r][c] = this.fill;
                }
            }
        }
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
            System.out.println("Kon de bestand niet vinden.");
            doc = null;
        }
        if (doc != null) doc.getDocumentElement().normalize(); // Maakt het document schoon door bijv. whitespace te wissen.
        return doc;
    }
}
