public class CSPGraphColoring {

    static int V = 4; // number of vertices

    // Function to check if it's safe to assign color
    static boolean isSafe(int v, int graph[][], int color[], int c) {

        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && color[i] == c) {
                return false;
            }
        }
        return true;
    }

    // Backtracking function
    static boolean solve(int graph[][], int m, int color[], int v) {

        // All vertices are assigned
        if (v == V) {
            return true;
        }

        // Try all colors
        for (int c = 1; c <= m; c++) {

            if (isSafe(v, graph, color, c)) {

                color[v] = c;

                // Recur for next vertex
                if (solve(graph, m, color, v + 1)) {
                    return true;
                }

                // Backtrack
                color[v] = 0;
            }
        }

        return false;
    }

    // Print solution
    static void printSolution(int color[]) {
        System.out.println("Vertex Colors:");
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + " -> Color " + color[i]);
        }
    }

    public static void main(String[] args) {

        // Graph represented as adjacency matrix
        int graph[][] = {
            {0, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {1, 0, 1, 0}
        };

        int m = 3; // number of colors
        int color[] = new int[V];

        if (solve(graph, m, color, 0)) {
            printSolution(color);
        } else {
            System.out.println("No solution exists");
        }
    }
}
