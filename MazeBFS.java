import java.util.*;

class Cell {
    int x, y;
    ArrayList<String> path;

    Cell(int x, int y, ArrayList<String> path) {
        this.x = x;
        this.y = y;
        this.path = path;
    }
}

public class MazeBFS {

    public static void solve(int[][] maze, int n, int m) {

        Queue<Cell> queue = new LinkedList<Cell>();
        boolean[][] visited = new boolean[n][m];

        ArrayList<String> startPath = new ArrayList<String>();
        queue.add(new Cell(0, 0, startPath));

        while (queue.isEmpty() == false) {

            Cell curr = queue.poll();
            int x = curr.x;
            int y = curr.y;

            // Check boundaries and walls
            if (x < 0 || x >= n || y < 0 || y >= m) {
                continue;
            }

            if (maze[x][y] == 1 || visited[x][y] == true) {
                continue;
            }

            visited[x][y] = true;

            // Check goal
            if (x == n - 1 && y == m - 1) {
                System.out.println("Path found:");

                for (int i = 0; i < curr.path.size(); i++) {
                    System.out.println(curr.path.get(i));
                }
                System.out.println("Reached (" + x + "," + y + ")");
                return;
            }

            // Move UP
            if (x - 1 >= 0) {
                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Up");
                queue.add(new Cell(x - 1, y, newPath));
            }

            // Move DOWN
            if (x + 1 < n) {
                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Down");
                queue.add(new Cell(x + 1, y, newPath));
            }

            // Move LEFT
            if (y - 1 >= 0) {
                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Left");
                queue.add(new Cell(x, y - 1, newPath));
            }

            // Move RIGHT
            if (y + 1 < m) {
                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Right");
                queue.add(new Cell(x, y + 1, newPath));
            }
        }

        System.out.println("No path found.");
    }

    public static void main(String[] args) {

        int[][] maze = {
            {0, 1, 0, 0},
            {0, 0, 0, 1},
            {1, 0, 1, 0},
            {0, 0, 0, 0}
        };  

        int n = 4;
        int m = 4;

        solve(maze, n, m);
    }
}
