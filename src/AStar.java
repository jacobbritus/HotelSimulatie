import java.util.*;

public class AStar {

    public static List<Vakje> findPath(Vakje start, Vakje doel) {

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<Vakje, Node> nodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, h(start, doel));
        open.add(startNode);
        nodes.put(start, startNode);

        while (!open.isEmpty()) {
            Node current = open.poll();

            // Doel bereikt → reconstruct pad
            if (current.vakje == doel) {
                return reconstruct(current);
            }

            for (Vakje buur : current.vakje.getBuren()) {

                // Niet door bezette vakjes, behalve de bestemming
                if (!buur.isVrij() && buur != doel) continue;

                // -----------------------------
                // COST = afstand + congestie + predictive congestie
                // -----------------------------
                int movementCost = 1
                        + buur.congestie
                        + buur.toekomstigeCongestie;

                int g = current.g + movementCost;

                Node next = nodes.getOrDefault(buur, new Node(buur));
                nodes.put(buur, next);

                if (g < next.g) {
                    next.g = g;
                    next.h = h(buur, doel);
                    next.f = next.g + next.h;
                    next.parent = current;
                    open.add(next);
                }
            }
        }

        return null; // geen pad
    }

    // Manhattan distance op globale schaal
    private static int h(Vakje a, Vakje b) {

        Oppervlakte oppA = a.getOppervlakte();
        Oppervlakte oppB = b.getOppervlakte();
        Oppervlakte[][] grid = oppA.getRuimtes();

        int oppRA = 0, oppCA = 0, oppRB = 0, oppCB = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == oppA) {
                    oppRA = r;
                    oppCA = c;
                }
                if (grid[r][c] == oppB) {
                    oppRB = r;
                    oppCB = c;
                }
            }
        }

        int globalRA = oppRA * Instellingen.oppervlakVakjes + a.gridR;
        int globalCA = oppCA * Instellingen.oppervlakVakjes + a.gridC;
        int globalRB = oppRB * Instellingen.oppervlakVakjes + b.gridR;
        int globalCB = oppCB * Instellingen.oppervlakVakjes + b.gridC;

        return Math.abs(globalRA - globalRB) + Math.abs(globalCA - globalCB);
    }

    private static List<Vakje> reconstruct(Node node) {
        List<Vakje> pad = new ArrayList<>();
        while (node != null) {
            pad.add(node.vakje);
            node = node.parent;
        }
        Collections.reverse(pad);

        // Eerste element is het startvakje → verwijderen
        if (!pad.isEmpty()) pad.remove(0);

        return pad;
    }

    private static class Node {
        Vakje vakje;
        Node parent;
        int g = Integer.MAX_VALUE;
        int h;
        int f;

        Node(Vakje v) {
            this.vakje = v;
        }

        Node(Vakje v, Node parent, int g, int h) {
            this.vakje = v;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}