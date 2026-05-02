import java.util.*;

class State {
    int jug1, jug2;
    List<String> path;

    State(int jug1, int jug2, List<String> path) {
        this.jug1 = jug1;
        this.jug2 = jug2;
        this.path = path;
    }
}

public class WaterJugBFS {

    public static void solve(int x, int y, int target) {
        Set<String> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();

        queue.add(new State(0, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            int jug1 = curr.jug1;
            int jug2 = curr.jug2;

            String stateKey = jug1 + "," + jug2;
            if (visited.contains(stateKey)) continue;

            visited.add(stateKey);

            List<String> newPath = new ArrayList<>(curr.path);
            newPath.add("(" + jug1 + ", " + jug2 + ")");

            if (jug1 == target || jug2 == target) {
                System.out.println("Solution found:");
                for (String step : newPath) {
                    System.out.println(step);
                }
                return;
            }

            queue.add(new State(x, jug2, newPath));
            queue.add(new State(jug1, y, newPath));
            queue.add(new State(0, jug2, newPath));
            queue.add(new State(jug1, 0, newPath));

            int pourToJug2 = Math.min(jug1, y - jug2);
            queue.add(new State(jug1 - pourToJug2, jug2 + pourToJug2, newPath));

            int pourToJug1 = Math.min(jug2, x - jug1);
            queue.add(new State(jug1 + pourToJug1, jug2 - pourToJug1, newPath));
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter capacity of Jug 1: ");
        int x = sc.nextInt();

        System.out.print("Enter capacity of Jug 2: ");
        int y = sc.nextInt();

        System.out.print("Enter target amount: ");
        int target = sc.nextInt();

        sc.close();

        if (target > Math.max(x, y)) {
            System.out.println("No solution found: target exceeds both jug capacities.");
        } else {
            solve(x, y, target);
        }
    }
}
