import java.util.*;

class Node {
    int[][] state;
    int x, y;
    ArrayList<String> path;

    Node(int[][] state, int x, int y, ArrayList<String> path) {
        this.state = state;
        this.x = x;
        this.y = y;
        this.path = path;
    }
}

public class EightPuzzleBFS {

    // Convert state to string (for visited set)
    static String serialize(int[][] state) {
        String s = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s = s + state[i][j];
            }
        }
        return s;
    }

    // Copy matrix
    static int[][] copy(int[][] arr) {
        int[][] newArr = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    public static void solve(int[][] start, int[][] goal) {

        Queue<Node> queue = new LinkedList<Node>();
        HashSet<String> visited = new HashSet<String>();

        int startX = 0, startY = 0;

        // Find blank (0)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (start[i][j] == 0) {
                    startX = i;
                    startY = j;
                }
            }
        }

        queue.add(new Node(start, startX, startY, new ArrayList<String>()));

        while (queue.isEmpty() == false) {

            Node curr = queue.poll();

            String key = serialize(curr.state);

            if (visited.contains(key) == true) {
                continue;
            }

            visited.add(key);

            // Check goal
            boolean isGoal = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (curr.state[i][j] != goal[i][j]) {
                        isGoal = false;
                    }
                }
            }

            if (isGoal == true) {
                System.out.println("Solution found:");

                for (int i = 0; i < curr.path.size(); i++) {
                    System.out.println(curr.path.get(i));
                }
                return;
            }

            int x = curr.x;
            int y = curr.y;

            // MOVE UP
            if (x - 1 >= 0) {
                int[][] newState = copy(curr.state);

                newState[x][y] = newState[x - 1][y];
                newState[x - 1][y] = 0;

                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Up");

                queue.add(new Node(newState, x - 1, y, newPath));
            }

            // MOVE DOWN
            if (x + 1 < 3) {
                int[][] newState = copy(curr.state);

                newState[x][y] = newState[x + 1][y];
                newState[x + 1][y] = 0;

                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Down");

                queue.add(new Node(newState, x + 1, y, newPath));
            }

            // MOVE LEFT
            if (y - 1 >= 0) {
                int[][] newState = copy(curr.state);

                newState[x][y] = newState[x][y - 1];
                newState[x][y - 1] = 0;

                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Left");

                queue.add(new Node(newState, x, y - 1, newPath));
            }

            // MOVE RIGHT
            if (y + 1 < 3) {
                int[][] newState = copy(curr.state);

                newState[x][y] = newState[x][y + 1];
                newState[x][y + 1] = 0;

                ArrayList<String> newPath = new ArrayList<String>(curr.path);
                newPath.add("Right");

                queue.add(new Node(newState, x, y + 1, newPath));
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {

        int[][] start = {
            {1, 2, 3},
            {4, 0, 5},
            {6, 7, 8}
        };

        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        solve(start, goal);
    }
}
