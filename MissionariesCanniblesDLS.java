import java.util.*;

public class MissionariesCanniblesDLS {

    static int LIMIT;

    static class State {
        int mLeft, cLeft, boat;

        State(int m, int c, int b) {
            mLeft = m;
            cLeft = c;
            boat = b;
        }

        // String key for visited
        String getKey() {
            return mLeft + "," + cLeft + "," + boat;
        }

        boolean isValid() {
            int mRight = 3 - mLeft;
            int cRight = 3 - cLeft;

            if (mLeft < 0 || cLeft < 0 || mLeft > 3 || cLeft > 3)
                return false;

            if ((mLeft > 0 && cLeft > mLeft) ||
                (mRight > 0 && cRight > mRight))
                return false;

            return true;
        }
    }

    static boolean dls(State current, int depth,
                       List<State> path, Set<String> visited) {

        path.add(current);

        // Goal check
        if (current.mLeft == 0 && current.cLeft == 0 && current.boat == 1)
            return true;

        // Depth limit check
        if (depth == LIMIT) {
            path.remove(path.size() - 1);
            return false;
        }

        visited.add(current.getKey());

        int[][] moves = {
            {1, 0}, {0, 1}, {2, 0}, {0, 2}, {1, 1}
        };

        for (int[] move : moves) {

            State next;

            if (current.boat == 0) {
                next = new State(
                    current.mLeft - move[0],
                    current.cLeft - move[1],
                    1
                );
            } else {
                next = new State(
                    current.mLeft + move[0],
                    current.cLeft + move[1],
                    0
                );
            }

            // Use string instead of object comparison
            if (next.isValid() && !visited.contains(next.getKey())) {
                if (dls(next, depth + 1, path, visited))
                    return true;
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter depth limit: ");
        LIMIT = sc.nextInt();

        sc.close();

        State start = new State(3, 3, 0);

        List<State> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        if (dls(start, 0, path, visited)) {
            System.out.println("Solution found within depth " + LIMIT + ":\n");

            for (State s : path) {
                System.out.println(
                    "M_left=" + s.mLeft +
                    ", C_left=" + s.cLeft +
                    ", Boat=" + (s.boat == 0 ? "Left" : "Right")
                );
            }
        } else {
            System.out.println("No solution within depth = " + LIMIT);
        }
    }
}
