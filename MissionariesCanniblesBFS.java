import java.util.*;

class State {
    int mLeft, cLeft;
    boolean boatLeft;
    State parent;

    public State(int mLeft, int cLeft, boolean boatLeft, State parent) {
        this.mLeft = mLeft;
        this.cLeft = cLeft;
        this.boatLeft = boatLeft;
        this.parent = parent;
    }

    // Convert state to string key
    public String getKey() {
        return mLeft + "," + cLeft + "," + boatLeft;
    }

    // Check if state is valid
    public boolean isValid() {
        int mRight = 3 - mLeft;
        int cRight = 3 - cLeft;

        if ((mLeft > 0 && mLeft < cLeft) ||
            (mRight > 0 && mRight < cRight))
            return false;

        if (mLeft < 0 || cLeft < 0 || mLeft > 3 || cLeft > 3)
            return false;

        return true;
    }

    // Check goal
    public boolean isGoal() {
        return mLeft == 0 && cLeft == 0 && !boatLeft;
    }
}

public class MissionariesCannibalsBFS {

    static List<State> getNextStates(State s) {
        List<State> next = new ArrayList<>();

        int[][] moves = {
            {1, 0}, {2, 0},
            {0, 1}, {0, 2},
            {1, 1}
        };

        for (int[] move : moves) {
            int m = move[0];
            int c = move[1];

            State newState;

            if (s.boatLeft) {
                newState = new State(s.mLeft - m, s.cLeft - c, false, s);
            } else {
                newState = new State(s.mLeft + m, s.cLeft + c, true, s);
            }

            if (newState.isValid()) {
                next.add(newState);
            }
        }

        return next;
    }

    static void printSolution(State goal) {
        List<State> path = new ArrayList<>();

        while (goal != null) {
            path.add(goal);
            goal = goal.parent;
        }

        Collections.reverse(path);

        for (State s : path) {
            System.out.println(
                "Left: M=" + s.mLeft + " C=" + s.cLeft +
                " | Right: M=" + (3 - s.mLeft) + " C=" + (3 - s.cLeft) +
                " | Boat: " + (s.boatLeft ? "Left" : "Right")
            );
        }
    }

    public static void bfs() {

        State start = new State(3, 3, true, null);

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start.getKey());

        while (!queue.isEmpty()) {

            State current = queue.poll();

            if (current.isGoal()) {
                printSolution(current);
                return;
            }

            for (State next : getNextStates(current)) {

                if (!visited.contains(next.getKey())) {
                    queue.add(next);
                    visited.add(next.getKey());
                }
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        bfs();
    }
}
