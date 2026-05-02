import java.util.*;

public class EightPuzzleDFS {

    static final int N = 3;

    static class State {
        int[][] board;
        int x, y;
        State parent;

        State(int[][] b, State parent) {
            board = new int[N][N];
            for (int i = 0; i < N; i++)
                board[i] = b[i].clone();
            this.parent = parent;

            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (board[i][j] == 0) {
                        x = i;
                        y = j;
                    }
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            return Arrays.deepEquals(board, ((State) o).board);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    static int[][] goal = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    static boolean isGoal(State s) {
        return Arrays.deepEquals(s.board, goal);
    }

    static List<State> getNextStates(State s) {
        List<State> next = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int k = 0; k < 4; k++) {
            int nx = s.x + dx[k];
            int ny = s.y + dy[k];

            if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
                int[][] newBoard = new int[N][N];
                for (int i = 0; i < N; i++)
                    newBoard[i] = s.board[i].clone();

                newBoard[s.x][s.y] = newBoard[nx][ny];
                newBoard[nx][ny] = 0;

                next.add(new State(newBoard, s));
            }
        }
        return next;
    }

    // ✅ DFS with a hard depth limit — no stack overflow
    static State depthLimitedDFS(State current, int depth, Set<State> visited) {
        if (isGoal(current)) return current;
        if (depth == 0) return null; // ✅ Stop going deeper

        visited.add(current);

        for (State next : getNextStates(current)) {
            if (!visited.contains(next)) {
                State result = depthLimitedDFS(next, depth - 1, visited);
                if (result != null) return result;
            }
        }

        visited.remove(current); // backtrack
        return null;
    }

    // ✅ IDDFS — try depth 1, 2, 3... until solution found
    static List<State> iddfs(State initial) {
        for (int depth = 0; depth <= 50; depth++) {
            System.out.println("Trying depth limit: " + depth);

            Set<State> visited = new HashSet<>();
            State result = depthLimitedDFS(initial, depth, visited);

            if (result != null) {
                // Reconstruct path via parent pointers
                List<State> path = new ArrayList<>();
                for (State s = result; s != null; s = s.parent)
                    path.add(s);
                Collections.reverse(path);
                return path;
            }
        }
        return null; // no solution within max depth
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] start = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };

        State initial = new State(start, null);
        List<State> path = iddfs(initial);

        if (path != null) {
            System.out.println("Solution found in " + (path.size() - 1) + " moves:\n");
            for (State s : path)
                printBoard(s.board);
        } else {
            System.out.println("No solution found.");
        }
    }
}
