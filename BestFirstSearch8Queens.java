import java.util.*;

public class BestFirstSearch8Queens {

    static final int NQ = 8;
    static final int MAX_NODES = 10000;

    static class QState {
        int[] pos;
        int placed;

        QState() {
            pos = new int[NQ];
            Arrays.fill(pos, -1);
            placed = 0;
        }

        QState(QState other) {
            pos = other.pos.clone();
            placed = other.placed;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof QState)) return false;
            QState s = (QState) o;
            return placed == s.placed && Arrays.equals(pos, s.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(placed, Arrays.hashCode(pos));
        }
    }

    static class Node {
        QState s;
        int h;
        int parent;

        Node(QState s, int h, int parent) {
            this.s = s;
            this.h = h;
            this.parent = parent;
        }
    }

    static List<Node> nodes      = new ArrayList<>();
    static List<Integer> openList   = new ArrayList<>();
    static Set<QState>   closedSet  = new HashSet<>();

    // Count pairwise conflicts among placed queens
    static int conflicts(QState s) {
        int c = 0;
        for (int i = 0; i < s.placed; i++) {
            for (int j = i + 1; j < s.placed; j++) {
                if (s.pos[i] == s.pos[j]) c++;
                else if ((s.pos[i] - s.pos[j]) == (i - j)) c++;
                else if ((s.pos[j] - s.pos[i]) == (i - j)) c++;
            }
        }
        return c;
    }

    // Push new node into nodes list and open list
    static int pushNode(QState s, int parent) {
        if (nodes.size() >= MAX_NODES) {
            System.err.println("Exceeded MAX_NODES");
            return -1;
        }
        int idx = nodes.size();
        nodes.add(new Node(new QState(s), conflicts(s), parent));
        openList.add(idx);
        return idx;
    }

    // Pop index from open list with min heuristic
    // Tie-breaker: prefer deeper (larger placed)
    static int popBestOpen() {
        if (openList.isEmpty()) return -1;

        int bestI   = 0;
        int bestIdx = openList.get(0);

        for (int i = 1; i < openList.size(); i++) {
            int idx = openList.get(i);
            Node curr = nodes.get(idx);
            Node best = nodes.get(bestIdx);

            if (curr.h < best.h ||
               (curr.h == best.h && curr.s.placed > best.s.placed)) {
                bestI   = i;
                bestIdx = idx;
            }
        }

        openList.remove(bestI);
        return bestIdx;
    }

    // Print solution by following parent chain
    static void printSolution(int idx) {
        List<Integer> stack = new ArrayList<>();
        int cur = idx;

        while (cur != -1) {
            stack.add(cur);
            cur = nodes.get(cur).parent;
        }

        Collections.reverse(stack);

        for (int n : stack) {
            Node node = nodes.get(n);
            if (node.s.placed == NQ && node.h == 0) {
                System.out.println("8-Queens solution (row -> column):");
                for (int r = 0; r < NQ; r++)
                    System.out.print(node.s.pos[r] + " ");
                System.out.println();
                return;
            }
        }

        System.out.println("Solution not found in recorded path.");
    }

    // Print board visually
    static void printBoard(int[] pos) {
        System.out.println("\nBoard visualization (Q = Queen, . = Empty):\n");
        for (int r = 0; r < NQ; r++) {
            for (int c = 0; c < NQ; c++)
                System.out.print(pos[r] == c ? " Q " : " . ");
            System.out.println();
        }
    }

    static boolean bestFirstSearch() {
        nodes.clear();
        openList.clear();
        closedSet.clear();

        QState start = new QState();
        int root = pushNode(start, -1);
        if (root < 0) return false;

        while (!openList.isEmpty()) {
            int curIdx = popBestOpen();
            if (curIdx < 0) break;

            Node cur = nodes.get(curIdx);

            // Skip if already in closed
            if (closedSet.contains(cur.s)) continue;
            closedSet.add(new QState(cur.s));

            // Goal check
            if (cur.s.placed == NQ && cur.h == 0) {
                System.out.println("Goal reached!\n");
                printSolution(curIdx);
                printBoard(cur.s.pos);
                return true;
            }

            // Expand: place queen in next row
            int row = cur.s.placed;
            for (int col = 0; col < NQ; col++) {
                QState ns = new QState(cur.s);
                ns.pos[row] = col;
                ns.placed   = row + 1;

                if (closedSet.contains(ns)) continue;
                if (openList.stream().anyMatch(i -> nodes.get(i).s.equals(ns))) continue;

                if (pushNode(ns, curIdx) < 0) return false;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println("=== Best First Search: 8 Queens Problem ===\n");

        if (!bestFirstSearch()) {
            System.out.println("No solution found.");
        }
    }
}
