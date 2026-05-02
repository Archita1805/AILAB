import java.util.*;

public class WaterJugDFS {

    static class State {
        int x, y;

        State(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof State)) return false;
            State s = (State) obj;
            return x == s.x && y == s.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static int jug1Cap, jug2Cap, target;
    static Set<State> visited = new HashSet<>();

    public static boolean dfs(State current, List<State> path) {
        path.add(current);

        if (current.x == target || current.y == target) {
            return true;
        }

        visited.add(current);

        List<State> nextStates = new ArrayList<>();

        nextStates.add(new State(jug1Cap, current.y));
        nextStates.add(new State(current.x, jug2Cap));
        nextStates.add(new State(0, current.y));
        nextStates.add(new State(current.x, 0));

        int pourToJug2 = Math.min(current.x, jug2Cap - current.y);
        nextStates.add(new State(current.x - pourToJug2, current.y + pourToJug2));

        int pourToJug1 = Math.min(current.y, jug1Cap - current.x);
        nextStates.add(new State(current.x + pourToJug1, current.y - pourToJug1));

        for (State next : nextStates) {
            if (!visited.contains(next)) {
                if (dfs(next, path)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter capacity of Jug 1: ");
        jug1Cap = sc.nextInt();

        System.out.print("Enter capacity of Jug 2: ");
        jug2Cap = sc.nextInt();

        System.out.print("Enter target amount: ");
        target = sc.nextInt();

        sc.close();

        if (target > Math.max(jug1Cap, jug2Cap)) {
            System.out.println("No solution found: target exceeds both jug capacities.");
            return;
        }

        List<State> path = new ArrayList<>();

        if (dfs(new State(0, 0), path)) {
            System.out.println("Solution Path:");
            for (State s : path) {
                System.out.println("(" + s.x + ", " + s.y + ")");
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
