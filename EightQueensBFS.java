import java.util.*;

class State {
    List<Integer> queens; // index = row, value = column

    public State(List<Integer> queens) {
        this.queens = queens;
    }
}

public class EightQueensBFS {

    static final int N = 8;

    // Check if placing queen at (row, col) is safe
    static boolean isSafe(List<Integer> queens, int row, int col) {
        for (int r = 0; r < row; r++) {
            int c = queens.get(r);

            // Same column
            if (c == col) return false;

            // Diagonal check
            if (Math.abs(c - col) == Math.abs(r - row)) return false;
        }
        return true;
    }

    // BFS solution
    public static void solveBFS() {
        Queue<State> queue = new LinkedList<>();

        // Start with empty board
        queue.add(new State(new ArrayList<>()));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int row = current.queens.size();

            // If all queens placed
            if (row == N) {
                printBoard(current.queens);
                return; // remove this if you want ALL solutions
            }

            // Try placing queen in all columns of current row
            for (int col = 0; col < N; col++) {
                if (isSafe(current.queens, row, col)) {
                    List<Integer> newQueens = new ArrayList<>(current.queens);
                    newQueens.add(col);
                    queue.add(new State(newQueens));
                }
            }
        }

        System.out.println("No solution found.");
    }

    // Print board
    static void printBoard(List<Integer> queens) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (queens.get(i) == j)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        solveBFS();
    }
}
