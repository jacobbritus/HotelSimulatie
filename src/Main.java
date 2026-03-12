import org.w3c.dom.Document;

public class Main {
    public static void main(String[] args) {
        Applicatie applicatie = new Applicatie();

        LayoutParser layoutParser = new LayoutParser();
        Document doc = layoutParser.loadFile("layouts/LobbyTest.layout");

        if (doc == null) {
            System.out.println("Kon layout niet laden.");
            return;
        }

        String[][] grid = layoutParser.convertLayout(doc);

        if (grid == null) {
            System.out.println("Layout is ongeldig. Simulatie wordt niet gestart.");
            return;
        }

        applicatie.startSimulatie(grid);
    }
}
