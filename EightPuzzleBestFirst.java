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

        // String key
        String getKey() {
            StringBuilder sb = new StringBuilder();
            for (int[] row : board)
                for (int val : row)
                    sb.append(val).append(",");
            return sb.toString();
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

                newBoard[s.x][s.y] = newBoard[nx][ny];
                newBoard[nx][ny] = 0;

                next.add(new State(newBoard));
            }
        }

        return next;
    }

    static void bestFirstSearch(int[][] start) {

        PriorityQueue<State> pq =
                new PriorityQueue<>(Comparator.comparingInt(s -> s.h));

        Set<String> visited = new HashSet<>();

        State initial = new State(start);
        pq.add(initial);

        while (!pq.isEmpty()) {

            State current = pq.poll();

            // Skip if already visited
            if (visited.contains(current.getKey()))
                continue;

            visited.add(current.getKey());

            // Print state
            for (int[] row : current.board) {
                for (int val : row)
                    System.out.print(val + " ");
                System.out.println();
            }
            System.out.println("h = " + current.h + "\n");

            // Goal check
            if (current.h == 0) {
                System.out.println("Goal reached!");
                return;
            }

            for (State next : getNextStates(current)) {
                if (!visited.contains(next.getKey())) {
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
