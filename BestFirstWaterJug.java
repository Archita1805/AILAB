import java.util.*;

public class BestFirstSearchWaterJug {

    static final int CAP_A = 4;
    static final int CAP_B = 3;
    static final int MAX_STATES = 200;

    static class State {
        int a, b;
        int parent;
        int h;
        boolean expanded;

        State(int a, int b, int parent, int h) {
            this.a = a;
            this.b = b;
            this.parent = parent;
            this.h = h;
            this.expanded = false;
        }
    }

    static List<State> states = new ArrayList<>();
    static boolean[][] visited = new boolean[CAP_A + 1][CAP_B + 1];

    static int heuristic(int a, int b) {
        return Math.abs(a - 2); // |a - 2|
    }

    static int addState(int a, int b, int parentIndex) {
        if (a < 0 || a > CAP_A || b < 0 || b > CAP_B) return -1;
        if (visited[a][b]) return -1;
        if (states.size() >= MAX_STATES) return -1;

        visited[a][b] = true;

        states.add(new State(a, b, parentIndex, heuristic(a, b)));
        return states.size() - 1;
    }

    static boolean isGoal(State s) {
        return s.a == 2;
    }

    static void printPath(int goalIndex) {
        List<int[]> path = new ArrayList<>();

        int current = goalIndex;
        while (current != -1) {
            State s = states.get(current);
            path.add(new int[]{s.a, s.b, s.h});
            current = s.parent;
        }

        Collections.reverse(path);

        System.out.println("\nSolution path (A, B):");
        for (int[] step : path) {
            System.out.println("(" + step[0] + ", " + step[1] + ")  h=" + step[2]);
        }
    }

    static void bestFirstSearch() {
        System.out.println("=== Best First Search: Water Jug Problem (4L, 3L) ===");

        // Add start state (0, 0)
        int startIndex = addState(0, 0, -1);
        if (startIndex == -1) {
            System.out.println("Failed to add start state.");
            return;
        }

        int goalIndex = -1;

        while (true) {
            // Find unexpanded state with smallest heuristic
            int bestIndex = -1;
            int bestH = Integer.MAX_VALUE;

            for (int i = 0; i < states.size(); i++) {
                State s = states.get(i);
                if (!s.expanded && s.h < bestH) {
                    bestH = s.h;
                    bestIndex = i;
                }
            }

            // No more states to expand
            if (bestIndex == -1) break;

            State current = states.get(bestIndex);
            current.expanded = true;

            System.out.println("\nExpanding state: (A=" + current.a +
                               ", B=" + current.b + "), h=" + current.h);

            // Goal check
            if (isGoal(current)) {
                goalIndex = bestIndex;
                break;
            }

            int a = current.a;
            int b = current.b;

            // 1. Fill jug A
            if (a < CAP_A)
                addState(CAP_A, b, bestIndex);

            // 2. Fill jug B
            if (b < CAP_B)
                addState(a, CAP_B, bestIndex);

            // 3. Empty jug A
            if (a > 0)
                addState(0, b, bestIndex);

            // 4. Empty jug B
            if (b > 0)
                addState(a, 0, bestIndex);

            // 5. Pour A -> B
            if (a > 0 && b < CAP_B) {
                int pour = Math.min(a, CAP_B - b);
                addState(a - pour, b + pour, bestIndex);
            }

            // 6. Pour B -> A
            if (b > 0 && a < CAP_A) {
                int pour = Math.min(b, CAP_A - a);
                addState(a + pour, b - pour, bestIndex);
            }
        }

        if (goalIndex != -1) {
            System.out.println("\nGoal reached: 2 liters in Jug A.");
            printPath(goalIndex);
        } else {
            System.out.println("\nNo solution found.");
        }
    }

    public static void main(String[] args) {
        bestFirstSearch();
    }
}
