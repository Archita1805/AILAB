import java.util.*;

public class BestFirstSearchRoute {

    static final int V = 5;

    static class Edge {
        int to;
        double weight;

        Edge(int to, double weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class OpenEntry {
        int vertex;
        int parent;
        double h;
        double costFromStart;

        OpenEntry(int vertex, int parent, double h, double costFromStart) {
            this.vertex = vertex;
            this.parent = parent;
            this.h = h;
            this.costFromStart = costFromStart;
        }
    }

    // Graph as adjacency list
    static List<List<Edge>> graph = new ArrayList<>();

    // Coordinates for heuristic (Euclidean distance)
    static double[][] coords = {
        {0.0, 0.0},   // 0: A
        {2.0, 1.0},   // 1: B
        {3.0, 4.0},   // 2: C
        {5.0, 2.0},   // 3: D
        {6.0, 0.0}    // 4: E (goal)
    };

    static void addEdge(int u, int v, double w) {
        graph.get(u).add(new Edge(v, w));
    }

    static double heuristic(int u, int goal) {
        double dx = coords[u][0] - coords[goal][0];
        double dy = coords[u][1] - coords[goal][1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    static void bestFirstSearch(int start, int goal) {

        // Priority queue ordered by heuristic value only (greedy)
        PriorityQueue<OpenEntry> pq =
            new PriorityQueue<>(Comparator.comparingDouble(e -> e.h));

        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        double[] costTo = new double[V];

        Arrays.fill(parent, -1);
        Arrays.fill(costTo, Double.MAX_VALUE);

        pq.add(new OpenEntry(start, -1, heuristic(start, goal), 0.0));
        costTo[start] = 0.0;

        while (!pq.isEmpty()) {
            OpenEntry curr = pq.poll();
            int u = curr.vertex;

            if (visited[u]) continue;
            visited[u] = true;
            parent[u] = curr.parent;
            costTo[u] = curr.costFromStart;

            System.out.println("Visiting: " + (char)('A' + u));

            if (u == goal) {
                // Reconstruct path
                List<Integer> path = new ArrayList<>();
                for (int x = goal; x != -1; x = parent[x])
                    path.add(x);
                Collections.reverse(path);

                System.out.print("\nGreedy Best-First path (goal = node "
                    + (char)('A' + goal) + "):\n");

                for (int i = 0; i < path.size(); i++) {
                    System.out.print((char)('A' + path.get(i)));
                    if (i != path.size() - 1)
                        System.out.print(" -> ");
                }

                System.out.printf("\nTotal path cost: %.2f%n", costTo[goal]);
                return;
            }

            // Expand neighbors
            for (Edge edge : graph.get(u)) {
                if (!visited[edge.to]) {
                    double newCost = curr.costFromStart + edge.weight;
                    pq.add(new OpenEntry(
                        edge.to,
                        u,
                        heuristic(edge.to, goal),
                        newCost
                    ));
                }
            }
        }

        System.out.println("No path found by Greedy Best-First.");
    }

    public static void main(String[] args) {

        // Initialize adjacency list
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        // Build graph (bidirectional)
        addEdge(0, 1, 2.2); addEdge(1, 0, 2.2);
        addEdge(0, 2, 3.6); addEdge(2, 0, 3.6);
        addEdge(1, 3, 3.0); addEdge(3, 1, 3.0);
        addEdge(2, 3, 2.2); addEdge(3, 2, 2.2);
        addEdge(3, 4, 2.5); addEdge(4, 3, 2.5);

        int start = 0, goal = 4;
        bestFirstSearch(start, goal);
    }
}
