import java.util.*;

public class MazeDLS {

    static int N = 4;
    static int LIMIT;

    static boolean isSafe(int[][] maze, int x, int y, boolean[][] visited) {
        return (x >= 0 && x < N && y >= 0 && y < N &&
                maze[x][y] == 1 && !visited[x][y]);
    }

    static boolean dls(int[][] maze, int x, int y,
                       boolean[][] visited, int depth,
                       List<String> path) {

        path.add("(" + x + "," + y + ")");

        if (x == N - 1 && y == N - 1)
            return true;

        if (depth == LIMIT) {
            path.remove(path.size() - 1);
            return false;
        }

        if (isSafe(maze, x, y, visited)) {

            visited[x][y] = true;

            if (dls(maze, x + 1, y, visited, depth + 1, path)) return true;
            if (dls(maze, x, y + 1, visited, depth + 1, path)) return true;
            if (dls(maze, x - 1, y, visited, depth + 1, path)) return true;
            if (dls(maze, x, y - 1, visited, depth + 1, path)) return true;

            visited[x][y] = false;
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter depth limit: ");
        LIMIT = sc.nextInt();

        sc.close();

        int[][] maze = {
            {1, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 1, 0, 0},
            {1, 1, 1, 1}
        };

        boolean[][] visited = new boolean[N][N];
        List<String> path = new ArrayList<>();

        if (dls(maze, 0, 0, visited, 0, path)) {
            System.out.println("Path found within depth " + LIMIT + ":");
            for (String p : path)
                System.out.print(p + " ");
        } else {
            System.out.println("No path found within depth = " + LIMIT);
        }
    }
}
