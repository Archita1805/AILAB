
public class EightPuzzleDLS {

    static final int N = 9;
    static final int MAX_STATES = 100000;
    static final int DEPTH_LIMIT = 3;

    static class State {
        int[] puzzle = new int[N];
        int parent;
    }

    static State[] states = new State[MAX_STATES];
    static int stateCount = 0;
    static int goalIndex = -1;

    // Print state
    static void printState(State s) {
        for (int i = 0; i < N; i++) {
            if (i % 3 == 0) System.out.println();
            if (s.puzzle[i] == 0)
                System.out.print("  _ ");
            else
                System.out.print("  " + s.puzzle[i] + " ");
        }
        System.out.println();
    }

    // Compare states
    static boolean isSame(State s1, State s2) {
        for (int i = 0; i < N; i++) {
            if (s1.puzzle[i] != s2.puzzle[i]) return false;
        }
        return true;
    }

    // Check visited
    static boolean isVisited(State s) {
        for (int i = 0; i < stateCount; i++) {
            if (isSame(states[i], s)) return true;
        }
        return false;
    }

    // Goal check
    static boolean isGoal(State s) {
        int[] goal = {1,2,3,4,5,6,7,8,0};
        for (int i = 0; i < N; i++) {
            if (s.puzzle[i] != goal[i]) return false;
        }
        return true;
    }

    // DLS function
    static boolean dls(State current, int parentIndex, int depth, int limit) {

        if (stateCount >= MAX_STATES) return false;

        if (isVisited(current)) return false;

        int currentIndex = stateCount;
        states[currentIndex] = new State();
        states[currentIndex].puzzle = current.puzzle.clone();
        states[currentIndex].parent = parentIndex;
        stateCount++;

        if (isGoal(states[currentIndex])) {
            goalIndex = currentIndex;
            return true;
        }

        if (depth == limit) return false;

        // find blank
        int zeroPos = -1;
        for (int i = 0; i < N; i++) {
            if (current.puzzle[i] == 0) {
                zeroPos = i;
                break;
            }
        }

        int row = zeroPos / 3;
        int col = zeroPos % 3;

        // UP
        if (row > 0) {
            State next = new State();
            next.puzzle = current.puzzle.clone();
            swap(next.puzzle, zeroPos, (row - 1) * 3 + col);

            if (dls(next, currentIndex, depth + 1, limit)) return true;
        }

        // DOWN
        if (row < 2) {
            State next = new State();
            next.puzzle = current.puzzle.clone();
            swap(next.puzzle, zeroPos, (row + 1) * 3 + col);

            if (dls(next, currentIndex, depth + 1, limit)) return true;
        }

        // LEFT
        if (col > 0) {
            State next = new State();
            next.puzzle = current.puzzle.clone();
            swap(next.puzzle, zeroPos, row * 3 + (col - 1));

            if (dls(next, currentIndex, depth + 1, limit)) return true;
        }

        // RIGHT
        if (col < 2) {
            State next = new State();
            next.puzzle = current.puzzle.clone();
            swap(next.puzzle, zeroPos, row * 3 + (col + 1));

            if (dls(next, currentIndex, depth + 1, limit)) return true;
        }

        return false;
    }

    // Swap helper
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Print path
    static void printPath(int goalIndex) {
        int[] path = new int[MAX_STATES];
        int length = 0;

        int current = goalIndex;
        while (current != -1) {
            path[length++] = current;
            current = states[current].parent;
        }

        System.out.println("\nSolution path:");
        for (int i = length - 1; i >= 0; i--) {
            System.out.println("\nStep " + (length - 1 - i) + ":");
            printState(states[path[i]]);
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Depth Limited Search (depth = " + DEPTH_LIMIT + ") ===");

        State start = new State();
        int[] startPuzzle = {
            1, 2, 3,
            4, 0, 6,
            7, 5, 8
        };

        start.puzzle = startPuzzle.clone();
        start.parent = -1;

        System.out.println("\nStart state:");
        printState(start);

        stateCount = 0;
        goalIndex = -1;

        boolean found = dls(start, -1, 0, DEPTH_LIMIT);

        if (found && goalIndex != -1) {
            System.out.println("\nGoal reached within depth limit!");
            printPath(goalIndex);
        } else {
            System.out.println("\nGoal NOT reached within depth = " + DEPTH_LIMIT);
        }
    }
}
