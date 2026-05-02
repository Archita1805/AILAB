import java.util.*;

public class AstarEightQueens {

    static final int N = 8;

    static class State {
        int[] pos;      // pos[row] = column, -1 if not placed
        int placed;     // number of queens placed so far

        State() {
            pos = new int[N];
            Arrays.fill(pos, -1);
            placed = 0;
        }

        State(State other) {
            pos = other.pos.clone();
            placed = other.placed;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return placed == s.placed && Arrays.equals(pos, s.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(placed, Arrays.hashCode(pos));
        }
    }

    static class Node {
        State s;
        int g;  // cost = number of queens placed
        int f;  // f = g + h
        Node parent;

        Node(State s, int g, int f, Node parent) {
            this.s = s;
            this.g = g;
            this.f = f;
            this.parent = parent;
        }
    }

    // Heuristic: number of conflicts among placed queens
    static int heuristic(State s) {
        int conflicts = 0;
        for (int i = 0; i < s.placed; i++) {
            for (int j = i + 1; j < s.placed; j++) {
                int ci = s.pos[i];
                int cj = s.pos[j];
                if (ci == cj)                    conflicts++; // same column
                if ((ci - cj) == (i - j))        conflicts++; // major diagonal
                if ((cj - ci) == (i - j))        conflicts++; // minor diagonal
            }
        }
        return conflicts;
    }

    // Check if placing queen at (row, col) is safe
    static boolean isSafe(State s, int row, int col) {
        for (int r = 0; r < row; r++) {
            int c = s.pos[r];
            if (c == col)                return false; // same column
            if ((c - col) == (r - row)) return false; // major diagonal
            if ((col - c) == (r - row)) return false; // minor diagonal
        }
        return true;
    }

    // Print solution
    static void printSolution(Node goal) {
        System.out.println("8-Queens A* solution (row -> column):\n");
        for (int i = 0; i < N; i++)
            System.out.print(goal.s.pos[i] + " ");
        System.out.println();
        printBoard(goal.s.pos);
    }

    // Print board visualization
    static void printBoard(int[] pos) {
        System.out.println("\nBoard visualization (Q = Queen, . = Empty):\n");
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++)
                System.out.print(pos[r] == c ? " Q " : " . ");
            System.out.println();
        }
    }

    // Print path from start to goal
    static void printPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = cur.parent;
        }
        Collections.reverse(path);

        System.out.println("\nA* path (step by step):\n");
        for (int i = 0; i < path.size(); i++) {
            Node n = path.get(i);
            System.out.println("Step " + i +
                               " | Queens placed: " + n.s.placed +
                               " | g=" + n.g +
                               " | h=" + heuristic(n.s) +
                               " | f=" + n.f);
        }
    }

    static void astar() {
        // Priority queue ordered by f = g + h
        PriorityQueue<Node> open =
            new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        Set<State> visited = new HashSet<>();

        State start = new State();
        int h = heuristic(start);
        open.add(new Node(start, 0, h, null));

        while (!open.isEmpty()) {

            Node cur = open.poll();

            // Skip if already visited
            if (visited.contains(cur.s)) continue;
            visited.add(cur.s);

            // Goal check: all 8 queens placed with no conflicts
            if (cur.s.placed == N && heuristic(cur.s) == 0) {
                System.out.println("Goal reached!\n");
                printPath(cur);
                printSolution(cur);
                return;
            }

            // Expand: place queen in next row
            int row = cur.s.placed;
            if (row >= N) continue;

            for (int col = 0; col < N; col++) {
                if (!isSafe(cur.s, row, col)) continue;

                State ns = new State(cur.s);
                ns.pos[row] = col;
                ns.placed   = row + 1;

                if (visited.contains(ns)) continue;

                int ng = cur.g + 1;             // g = queens placed
                int nh = heuristic(ns);          // h = conflicts
                int nf = ng + nh;               // f = g + h

                open.add(new Node(ns, ng, nf, cur));
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        System.out.println("=== A* Search: 8 Queens Problem ===\n");
        astar();
    }
}
