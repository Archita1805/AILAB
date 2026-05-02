import java.util.*;

public class BestFirstSearchMissionariesCannibals {

    static final int MAX_STATES = 200;

    static class State {
        int ml, cl, boat;
        int parent;
        int h;
        boolean expanded;

        State(int ml, int cl, int boat, int parent, int h) {
            this.ml = ml;
            this.cl = cl;
            this.boat = boat;
            this.parent = parent;
            this.h = h;
            this.expanded = false;
        }
    }

    static List<State> states = new ArrayList<>();
    static boolean[][][] visited = new boolean[4][4][2];

    static int heuristic(int ml, int cl) {
        return ml + cl; // people left on left bank
    }

    static boolean isValid(int ml, int cl, int boat) {
        int mr = 3 - ml;
        int cr = 3 - cl;

        if (ml < 0 || ml > 3 || cl < 0 || cl > 3) return false;
        if (ml > 0 && cl > ml) return false;   // left bank unsafe
        if (mr > 0 && cr > mr) return false;   // right bank unsafe

        return true;
    }

    static boolean isGoal(State s) {
        return s.ml == 0 && s.cl == 0 && s.boat == 1;
    }

    static int addState(int ml, int cl, int boat, int parentIndex) {
        if (!isValid(ml, cl, boat))  return -1;
        if (visited[ml][cl][boat])   return -1;
        if (states.size() >= MAX_STATES) return -1;

        visited[ml][cl][boat] = true;
        states.add(new State(ml, cl, boat, parentIndex, heuristic(ml, cl)));
        return states.size() - 1;
    }

    static void printState(State s) {
        int mr = 3 - s.ml;
        int cr = 3 - s.cl;
        System.out.print("Left bank: M=" + s.ml + ", C=" + s.cl + "\t");
        System.out.print("Right bank: M=" + mr + ", C=" + cr + "\t");
        System.out.println("Boat: " + (s.boat == 0 ? "Left" : "Right"));
    }

    static void printPath(int goalIndex) {
        List<Integer> path = new ArrayList<>();
        int current = goalIndex;

        while (current != -1) {
            path.add(current);
            current = states.get(current).parent;
        }

        Collections.reverse(path);

        System.out.println("\nSolution path (from start to goal):\n");
        for (int i = 0; i < path.size(); i++) {
            State s = states.get(path.get(i));
            System.out.print("Step " + i + " (h=" + s.h + "): ");
            printState(s);
        }
    }

    static void bestFirstSearch() {
        System.out.println("=== Best First Search: Missionaries and Cannibals ===");

        // Start: 3M, 3C, boat on left
        int startIndex = addState(3, 3, 0, -1);
        if (startIndex == -1) {
            System.out.println("Failed to add start state.");
            return;
        }

        int goalIndex = -1;

        int[][] moves = {
            {1, 0},  // 1 missionary
            {2, 0},  // 2 missionaries
            {0, 1},  // 1 cannibal
            {0, 2},  // 2 cannibals
            {1, 1}   // 1 missionary + 1 cannibal
        };

        while (true) {
            // Pick unexpanded state with smallest h
            int bestIndex = -1;
            int bestH = Integer.MAX_VALUE;

            for (int i = 0; i < states.size(); i++) {
                State s = states.get(i);
                if (!s.expanded && s.h < bestH) {
                    bestH = s.h;
                    bestIndex = i;
                }
            }

            // Frontier empty
            if (bestIndex == -1) break;

            State current = states.get(bestIndex);
            current.expanded = true;

            System.out.print("\nExpanding state (h=" + current.h + "): ");
            printState(current);

            // Goal check
            if (isGoal(current)) {
                goalIndex = bestIndex;
                break;
            }

            int ml   = current.ml;
            int cl   = current.cl;
            int boat = current.boat;

            // Generate neighbors
            for (int[] move : moves) {
                int m = move[0];
                int c = move[1];

                if (boat == 0) {
                    // Boat on left: move people to right
                    addState(ml - m, cl - c, 1, bestIndex);
                } else {
                    // Boat on right: move people to left
                    addState(ml + m, cl + c, 0, bestIndex);
                }
            }
        }

        if (goalIndex != -1) {
            System.out.println("\nGoal state reached!");
            printPath(goalIndex);
        } else {
            System.out.println("\nNo solution found.");
        }
    }

    public static void main(String[] args) {
        bestFirstSearch();
    }
}
