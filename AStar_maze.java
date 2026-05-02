import java.util.*;

public class AStar_maze {

    static final int R = 6;
    static final int C = 6;

    static int[][] grid = {
        {0, 0, 0, 0, 1, 0},
        {1, 1, 0, 1, 1, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 0},
        {0, 0, 0, 0, 1, 0},
        {0, 1, 1, 0, 0, 0}
    };

    static class Node {
        int r, c;
        int g, f;
        Node parent;

        Node(int r, int c, int g, int f, Node parent) {
            this.r = r;
            this.c = c;
            this.g = g;
            this.f = f;
            this.parent = parent;
        }
    }

    static int heuristic(int r, int c, int gr, int gc) {
        return Math.abs(r - gr) + Math.abs(c - gc); // Manhattan distance
    }

    static boolean inOpen(List<Node> open, int r, int c) {
        for (Node n : open)
            if (n.r == r && n.c == c)
                return true;
        return false;
    }

    static void printPath(Node goal) {
        List<int[]> path = new ArrayList<>();
        Node p = goal;

        while (p != null) {
            path.add(new int[]{p.r, p.c});
            p = p.parent;
        }

        Collections.reverse(path);

        System.out.println("Path length = " + (path.size() - 1));
        for (int[] step : path)
            System.out.println("(" + step[0] + ", " + step[1] + ")");
    }

    static void printGridWithPath(Node goal) {
        char[][] display = new char[R][C];

        // Fill grid display
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++)
                display[i][j] = grid[i][j] == 1 ? '#' : '.';

        // Mark path
        Node p = goal;
        while (p != null) {
            display[p.r][p.c] = 'P';
            p = p.parent;
        }

        display[0][0] = 'S';
        display[5][5] = 'G';

        System.out.println("\nGrid (S=Start, G=Goal, P=Path, #=Wall, .=Free):\n");
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++)
                System.out.print(" " + display[i][j] + " ");
            System.out.println();
        }
    }

    static void astar() {
        int sr = 0, sc = 0; // start
        int gr = 5, gc = 5; // goal

        List<Node> open    = new ArrayList<>();
        boolean[][] closed = new boolean[R][C];

        Node start = new Node(sr, sc, 0, heuristic(sr, sc, gr, gc), null);
        open.add(start);

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!open.isEmpty()) {

            // Select node with minimum f
            int idx = 0;
            for (int i = 1; i < open.size(); i++)
                if (open.get(i).f < open.get(idx).f)
                    idx = i;

            Node cur = open.remove(idx);

            // Goal check
            if (cur.r == gr && cur.c == gc) {
                System.out.println("Goal reached!\n");
                printPath(cur);
                printGridWithPath(cur);
                return;
            }

            closed[cur.r][cur.c] = true;

            // Expand neighbors
            for (int k = 0; k < 4; k++) {
                int nr = cur.r + dr[k];
                int nc = cur.c + dc[k];

                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if (grid[nr][nc] == 1)  continue;
                if (closed[nr][nc])     continue;
                if (inOpen(open, nr, nc)) continue;

                int ng = cur.g + 1;
                int nf = ng + heuristic(nr, nc, gr, gc);

                open.add(new Node(nr, nc, ng, nf, cur));
            }
        }

        System.out.println("No path found.");
    }

    public static void main(String[] args) {
        System.out.println("=== A* Search: Shortest Path in Grid ===\n");
        astar();
    }
}
