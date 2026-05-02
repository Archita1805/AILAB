import java.util.*;

public class MazeDFS {

    static int N = 4;

    static boolean isSafe(int[][] maze, int x, int y, boolean[][] visited) {
        return (x >= 0 && x < N && y >= 0 && y < N &&
                maze[x][y] == 1 && !visited[x][y]);
    }

    static boolean dfs(int[][] maze, int x, int y,
                       boolean[][] visited, List<String> path) {

        // If goal is reached
        if (x == N - 1 && y == N - 1) {
            path.add("(" + x + "," + y + ")");
            return true;
        }

        if (isSafe(maze, x, y, visited)) {

            visited[x][y] = true;
            path.add("(" + x + "," + y + ")");

            // Move Down
            if (dfs(maze, x + 1, y, visited, path)) return true;

            // Move Right
            if (dfs(maze, x, y + 1, visited, path)) return true;

            // Move Up
            if (dfs(maze, x - 1, y, visited, path)) return true;

            // Move Left
            if (dfs(maze, x, y - 1, visited, path)) return true;

            // Backtrack
            path.remove(path.size() - 1);
        }

        return false;
    }

    public static void main(String[] args) {

        int[][] maze = {
            {1, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 1, 0, 0},
            {1, 1, 1, 1}
        };

        boolean[][] visited = new boolean[N][N];
        List<String> path = new ArrayList<>();

        if (dfs(maze, 0, 0, visited, path)) {
            System.out.println("Path found:");
            for (String p : path) {
                System.out.print(p + " ");
            }
        } else {
            System.out.println("No path exists.");
        }
    }
}
