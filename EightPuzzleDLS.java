import java.util.*;

public class EightPuzzleDLS {

    static final int N = 3;
    static int LIMIT;

    static class State {
        int[][] board;
        int x, y;

        State(int[][] b) {
            board = new int[N][N];
            for (int i = 0; i < N; i++)
                board[i] = b[i].clone();

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

                next.add(new State(newBoard));
            }
        }

        return next;
    }

    static boolean dls(State current, int depth,
                       List<State> path, Set<State> visited) {

        path.add(current);

        if (isGoal(current))
            return true;

        if (depth == LIMIT) {
            path.remove(path.size() - 1);
            return false;
        }

        visited.add(current);

        for (State next : getNextStates(current)) {
            if (!visited.contains(next)) {
                if (dls(next, depth + 1, path, visited))
                    return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Take initial board input
        int[][] start = new int[N][N];
        System.out.println("Enter the initial board (3x3), row by row (use 0 for blank):");
        for (int i = 0; i < N; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            for (int j = 0; j < N; j++)
                start[i][j] = sc.nextInt();
        }

        // Take depth limit input
        System.out.print("Enter depth limit: ");
        LIMIT = sc.nextInt();

        sc.close();

        State initial = new State(start);

        List<State> path = new ArrayList<>();
        Set<State> visited = new HashSet<>();

        if (dls(initial, 0, path, visited)) {
            System.out.println("\nSolution found within depth " + LIMIT + ":\n");
            for (State s : path)
                printBoard(s.board);
        } else {
            System.out.println("No solution within depth = " + LIMIT);
        }
    }
}
