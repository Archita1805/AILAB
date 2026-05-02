import java.util.*;

public class AStarMissionariesCannibals {

    static class State {
        int m, c, b;

        State(int m, int c, int b) {
            this.m = m;
            this.c = c;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return m == s.m && c == s.c && b == s.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(m, c, b);
        }
    }

    static class Node {
        State s;
        int g, f;
        Node parent;

        Node(State s, int g, int f, Node parent) {
            this.s = s;
            this.g = g;
            this.f = f;
            this.parent = parent;
        }
    }

    static boolean valid(State s) {
        if (s.m < 0 || s.c < 0 || s.m > 3 || s.c > 3) return false;
        int mr = 3 - s.m;
        int cr = 3 - s.c;
        if (s.m > 0 && s.m < s.c)   return false; // left bank unsafe
        if (mr > 0 && mr < cr)       return false; // right bank unsafe
        return true;
    }

    static int heuristic(State s) {
        return (s.m + s.c + 1) / 2; // minimal boat trips remaining
    }

    static void printPath(Node n) {
        if (n == null) return;
        printPath(n.parent);
        System.out.println("M_left=" + n.s.m +
                           " C_left=" + n.s.c +
                           " boat="   + (n.s.b == 0 ? "left" : "right"));
    }

    static void astar() {
        State start = new State(3, 3, 0);
        State goal  = new State(0, 0, 1);

        List<Node>  open    = new ArrayList<>();
        Set<State>  visited = new HashSet<>();

        open.add(new Node(start, 0, heuristic(start), null));

        while (!open.isEmpty()) {

            // Pick node with minimum f
            int idx = 0;
            for (int i = 1; i < open.size(); i++)
                if (open.get(i).f < open.get(idx).f)
                    idx = i;

            Node cur = open.remove(idx);

            // Goal check
            if (cur.s.equals(goal)) {
                System.out.println("Solution found in " + cur.g + " moves:\n");
                printPath(cur);
                return;
            }

            // Skip if already visited
            if (visited.contains(cur.s)) continue;
            visited.add(cur.s);

            // Generate neighbors
            for (int dm = 0; dm <= 2; dm++) {
                for (int dc = 0; dc <= 2; dc++) {
                    if (dm + dc == 0 || dm + dc > 2) continue;

                    State ns;
                    if (cur.s.b == 0) {
                        // Boat on left -> move to right
                        ns = new State(cur.s.m - dm, cur.s.c - dc, 1);
                    } else {
                        // Boat on right -> move to left
                        ns = new State(cur.s.m + dm, cur.s.c + dc, 0);
                    }

                    if (!valid(ns))        continue;
                    if (visited.contains(ns)) continue;

                    int ng = cur.g + 1;
                    int nf = ng + heuristic(ns);
                    open.add(new Node(ns, ng, nf, cur));
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        System.out.println("=== A* Search: Missionaries and Cannibals ===\n");
        astar();
    }
}
