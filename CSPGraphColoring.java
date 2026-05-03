import java.util.*;

public class CSPGraphColoring {

    static int V; // number of vertices

    // Check if it's safe to assign color c to vertex v
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

        if (v == V) {
            return true;
        }

        for (int c = 1; c <= m; c++) {

            if (isSafe(v, graph, color, c)) {

                color[v] = c;

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
        System.out.println("\nVertex Colors:");
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + " -> Color " + color[i]);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input number of vertices
        System.out.print("Enter number of vertices: ");
        V = sc.nextInt();

        int graph[][] = new int[V][V];

        // Input adjacency matrix
        System.out.println("Enter adjacency matrix (0 or 1):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        // Input number of colors
        System.out.print("Enter number of colors: ");
        int m = sc.nextInt();

        int color[] = new int[V];

        // Solve
        if (solve(graph, m, color, 0)) {
            printSolution(color);
        } else {
            System.out.println("No solution exists");
        }

        sc.close();
    }
}
