import java.util.*;

public class AStarRoute {

    static class Node {
        String name;
        int g, h, f;
        Node parent;

        Node(String name, int g, int h, Node parent) {
            this.name = name;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.parent = parent;
        }
    }

    static class Edge {
        String dest;
        int cost;

        Edge(String d, int c) {
            dest = d;
            cost = c;
        }
    }

    static Map<String, List<Edge>> graph = new HashMap<>();
    static Map<String, Integer> heuristic = new HashMap<>();

    static void aStar(String start, String goal) {

        PriorityQueue<Node> pq =
            new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        Set<String> visited = new HashSet<>();

        pq.add(new Node(start, 0, heuristic.get(start), null));

        while (!pq.isEmpty()) {

            Node curr = pq.poll();

            if (curr.name.equals(goal)) {
                printPath(curr);
                return;
            }

            visited.add(curr.name);

            for (Edge e : graph.get(curr.name)) {

                if (!visited.contains(e.dest)) {
                    pq.add(new Node(
                        e.dest,
                        curr.g + e.cost,
                        heuristic.get(e.dest),
                        curr
                    ));
                }
            }
        }
    }

    static void printPath(Node node) {
        List<String> path = new ArrayList<>();

        while (node != null) {
            path.add(node.name);
            node = node.parent;
        }

        Collections.reverse(path);
        System.out.println("Path: " + path);
    }

    public static void main(String[] args) {

        graph.put("A", Arrays.asList(new Edge("B",1), new Edge("C",4)));
        graph.put("B", Arrays.asList(new Edge("D",3), new Edge("C",2)));
        graph.put("C", Arrays.asList(new Edge("D",5)));
        graph.put("D", new ArrayList<>());

        heuristic.put("A",7);
        heuristic.put("B",2);
        heuristic.put("C",4);
        heuristic.put("D",0);

        aStar("A", "D");
    }
}
