import java.util.*;

public class EightPuzzleBestFirst {

    static final int N = 3;

    static class State {
        int[][] board;
        int x, y;
        int h; // heuristic

        State(int[][] b) {
            board = new int[N][N];
            for (int i = 0; i < N; i++)
                board[i] = b[i].clone();

            // find blank
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (board[i][j] == 0) {
                        x = i;
                        y = j;
                    }

            h = calculateHeuristic();
        }

        int calculateHeuristic() {
            int[][] goal = {
                {1,2,3},
                {4,5,6},
                {7,8,0}
            };

            int count = 0;
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (board[i][j] != 0 && board[i][j] != goal[i][j])
                        count++;

            return count;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            return Arrays.deepEquals(board, ((State)o).board);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    static List<State> getNextStates(State s) {
        List<State> next = new ArrayList<>();

        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};

        for (int k = 0; k < 4; k++) {
            int nx = s.x + dx[k];
            int ny = s.y + dy[k];

            if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
                int[][] newBoard = new int[N][N];
                for (int i = 0; i < N; i++)
                    newBoard[i] = s.board[i].clone();

                // swap blank
                newBoard[s.x][s.y] = newBoard[nx][ny];
                newBoard[nx][ny] = 0;

                next.add(new State(newBoard));
            }
        }

        return next;
    }

    static void bestFirstSearch(int[][] start) {

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.h));
        Set<State> visited = new HashSet<>();

        State initial = new State(start);
        pq.add(initial);

        while (!pq.isEmpty()) {
            State current = pq.poll();

            // print state
            for (int[] row : current.board) {
                for (int val : row)
                    System.out.print(val + " ");
                System.out.println();
            }
            System.out.println("h = " + current.h + "\n");

            // goal check
            if (current.h == 0) {
                System.out.println("Goal reached!");
                return;
            }

            visited.add(current);

            for (State next : getNextStates(current)) {
                if (!visited.contains(next)) {
                    pq.add(next);
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {

        int[][] start = {
            {1,2,3},
            {4,0,5},
            {6,7,8}
        };

        bestFirstSearch(start);
    }
}
