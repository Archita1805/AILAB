import java.util.*;

public class AStar8Puzzle {

    static class Node {
        int[][] mat = new int[3][3];
        int x, y;      // blank position
        int g, h, f;   // cost

        Node(int[][] m, int g) {
            // copy matrix
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    mat[i][j] = m[i][j];

            this.g = g;

            // find blank (0)
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (mat[i][j] == 0) {
                        x = i;
                        y = j;
                    }

            h = heuristic();
            f = g + h;
        }

        int heuristic() {
            int h = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int val = mat[i][j];
                    if (val != 0) {
                        int gx = (val - 1) / 3;
                        int gy = (val - 1) % 3;
                        h += Math.abs(i - gx) + Math.abs(j - gy);
                    }
                }
            }
            return h;
        }
    }

    static void print(int[][] mat) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    static String convert(int[][] mat) {
        String s = "";
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                s += mat[i][j];
        return s;
    }

    public static void main(String[] args) {

        int[][] start = {
            {1,2,3},
            {4,0,5},
            {6,7,8}
        };

        PriorityQueue<Node> pq =
            new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        HashSet<String> visited = new HashSet<>();

        pq.add(new Node(start, 0));

        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};

        while (!pq.isEmpty()) {

            Node curr = pq.poll();

            print(curr.mat);

            if (curr.h == 0) {
                System.out.println("Goal Reached!");
                return;
            }

            visited.add(convert(curr.mat));

            for (int k = 0; k < 4; k++) {

                int nx = curr.x + dx[k];
                int ny = curr.y + dy[k];

                if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {

                    int[][] newMat = new int[3][3];

                    // copy
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++)
                            newMat[i][j] = curr.mat[i][j];

                    // swap
                    newMat[curr.x][curr.y] = newMat[nx][ny];
                    newMat[nx][ny] = 0;

                    if (!visited.contains(convert(newMat))) {
                        pq.add(new Node(newMat, curr.g + 1));
                    }
                }
            }
        }

        System.out.println("No solution");
    }
}
