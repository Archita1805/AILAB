import java.util.*;

public class BestFirstSearchMaze {

    static final int ROWS = 5;
    static final int COLS = 5;
    static final int MAX_STATES = ROWS * COLS * 4;

    // Maze: 0 = free, 1 = wall
    static int[][] maze = {
        {0, 1, 0, 0, 0},
        {0, 1, 0, 1, 0},
        {0, 0, 0, 1, 0},
        {1, 1, 0, 0, 0},
        {0, 0, 0, 1, 0}
    };

    static int startRow = 0, startCol = 0;
    static int goalRow  = 4, goalCol  = 4;

    static class State {
        int r, c;
        int parent;
        int h;
        boolean expanded;

        State(int r, int c, int parent, int h) {
            this.r = r;
            this.c = c;
            this.parent = parent;
            this.h = h;
            this.expanded = false;
        }
    }

    static List<State> states = new ArrayList<>();
    static boolean[][] visited = new boolean[ROWS][COLS];

    static int heuristic(int r, int c) {
        return Math.abs(r - goalRow) + Math.abs(c - goalCol); // Manhattan distance
    }

    static int addState(int r, int c, int parentIndex) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return -1;
        if (maze[r][c] == 1)   return -1;
        if (visited[r][c])     return -1;
        if (states.size() >= MAX_STATES) return -1;

        visited[r][c] = true;
        states.add(new State(r, c, parentIndex, heuristic(r, c)));
        return states.size() - 1;
    }

    static void printMazeWithPath(int goalIndex) {
        char[][] display = new char[ROWS][COLS];

        // Fill display with maze
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                display[i][j] = maze[i][j] == 1 ? '#' : '.';

        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        int current = goalIndex;
        while (current != -1) {
            path.add(current);
            current = states.get(current).parent;
        }

        // Mark path
        for (int idx : path) {
            State s = states.get(idx);
            display[s.r][s.c] = 'P';
        }

        display[startRow][startCol] = 'S';
        display[goalRow][goalCol]   = 'G';

        System.out.println("\nMaze with path (S=Start, G=Goal, P=Path, #=Wall, .=Free):\n");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.print(" " + display[i][j] + " ");
            System.out.println();
        }
    }

    static void bestFirstSearch() {
        System.out.println("=== Best First Search: Maze Problem ===");

        int startIndex = addState(startRow, startCol, -1);
        if (startIndex == -1) {
            System.out.println("Start state invalid.");
            return;
        }

        int goalIndex = -1;

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

            System.out.println("Expanding cell (" + current.r + ", " +
                                current.c + ") with h = " + current.h);

            // Goal check
            if (current.r == goalRow && current.c == goalCol) {
                goalIndex = bestIndex;
                break;
            }

            int r = current.r;
            int c = current.c;

            // Neighbors: Up, Down, Left, Right
            addState(r - 1, c, bestIndex);
            addState(r + 1, c, bestIndex);
            addState(r, c - 1, bestIndex);
            addState(r, c + 1, bestIndex);
        }

        if (goalIndex != -1) {
            System.out.println("\nGoal reached at (" + goalRow + ", " + goalCol + ")!");
            printMazeWithPath(goalIndex);
        } else {
            System.out.println("\nNo path found.");
        }
    }

    public static void main(String[] args) {
        bestFirstSearch();
    }
}
