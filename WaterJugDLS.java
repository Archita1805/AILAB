import java.util.*;

public class WaterJugDLS {

    static class State {
        int x, y;

        State(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Convert state to string
        String getKey() {
            return x + "," + y;
        }
    }

    static int jug1Cap, jug2Cap, target, LIMIT;

    static boolean dls(State current, int depth, List<State> path, Set<String> visited) {

        path.add(current);

        // Goal check
        if (current.x == target || current.y == target)
            return true;

        // Depth limit reached
        if (depth == LIMIT) {
            path.remove(path.size() - 1);
            return false;
        }

        visited.add(current.getKey());

        List<State> nextStates = new ArrayList<>();

        // Fill Jug1
        nextStates.add(new State(jug1Cap, current.y));

        // Fill Jug2
        nextStates.add(new State(current.x, jug2Cap));

        // Empty Jug1
        nextStates.add(new State(0, current.y));

        // Empty Jug2
        nextStates.add(new State(current.x, 0));

        // Pour Jug1 → Jug2
        int pourToJug2 = Math.min(current.x, jug2Cap - current.y);
        nextStates.add(new State(current.x - pourToJug2, current.y + pourToJug2));

        // Pour Jug2 → Jug1
        int pourToJug1 = Math.min(current.y, jug1Cap - current.x);
        nextStates.add(new State(current.x + pourToJug1, current.y - pourToJug1));

        for (State next : nextStates) {
            if (!visited.contains(next.getKey())) {
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

        System.out.print("Enter capacity of Jug 1: ");
        jug1Cap = sc.nextInt();

        System.out.print("Enter capacity of Jug 2: ");
        jug2Cap = sc.nextInt();

        System.out.print("Enter target amount: ");
        target = sc.nextInt();

        System.out.print("Enter depth limit: ");
        LIMIT = sc.nextInt();

        sc.close();

        if (target > Math.max(jug1Cap, jug2Cap)) {
            System.out.println("No solution found: target exceeds both jug capacities.");
            return;
        }

        List<State> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        if (dls(new State(0, 0), 0, path, visited)) {
            System.out.println("Solution within depth limit:");
            for (State s : path) {
                System.out.println("(" + s.x + ", " + s.y + ")");
            }
        } else {
            System.out.println("No solution found within depth = " + LIMIT);
        }
    }
}
