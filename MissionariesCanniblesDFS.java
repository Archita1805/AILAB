import java.util.*;

public class MissionariesCannibalsDFS {

    static class State {
        int mLeft, cLeft, boat;

        State(int m, int c, int b) {
            mLeft = m;
            cLeft = c;
            boat = b;
        }

        // Check if state is valid
        boolean isValid() {
            int mRight = 3 - mLeft;
            int cRight = 3 - cLeft;

            // No negative or overflow
            if (mLeft < 0 || cLeft < 0 || mLeft > 3 || cLeft > 3)
                return false;

            // Missionaries eaten condition
            if ((mLeft > 0 && cLeft > mLeft) ||
                (mRight > 0 && cRight > mRight))
                return false;

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return mLeft == s.mLeft && cLeft == s.cLeft && boat == s.boat;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mLeft, cLeft, boat);
        }
    }

    static Set<State> visited = new HashSet<>();

    static boolean dfs(State current, List<State> path) {

        path.add(current);

        // Goal check
        if (current.mLeft == 0 && current.cLeft == 0 && current.boat == 1)
            return true;

        visited.add(current);

        // All possible moves
        int[][] moves = {
            {1, 0}, {0, 1}, {2, 0}, {0, 2}, {1, 1}
        };

        for (int[] move : moves) {
            State next;

            if (current.boat == 0) { // boat on left → move to right
                next = new State(
                    current.mLeft - move[0],
                    current.cLeft - move[1],
                    1
                );
            } else { // boat on right → move to left
                next = new State(
                    current.mLeft + move[0],
                    current.cLeft + move[1],
                    0
                );
            }

            if (next.isValid() && !visited.contains(next)) {
                if (dfs(next, path))
                    return true;
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {

        State start = new State(3, 3, 0);
        List<State> path = new ArrayList<>();

        if (dfs(start, path)) {
            System.out.println("Solution Path:\n");
            for (State s : path) {
                System.out.println(
                    "M_left=" + s.mLeft +
                    ", C_left=" + s.cLeft +
                    ", Boat=" + (s.boat == 0 ? "Left" : "Right")
                );
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
