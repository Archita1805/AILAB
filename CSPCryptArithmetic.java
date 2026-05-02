import java.util.*;

public class CSPCryptArithmetic {

    static String s1 = "SEND";
    static String s2 = "MORE";
    static String s3 = "MONEY";

    static HashMap<Character, Integer> map = new HashMap<>();
    static boolean[] used = new boolean[10];

    // Convert string to number using assigned digits
    static int getNumber(String s) {
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            num = num * 10 + map.get(s.charAt(i));
        }
        return num;
    }

    // Solve using backtracking
    static boolean solve(List<Character> uniqueChars, int index) {

        if (index == uniqueChars.size()) {

            // Leading zero check
            if (map.get(s1.charAt(0)) == 0 || map.get(s2.charAt(0)) == 0 || map.get(s3.charAt(0)) == 0)
                return false;

            int num1 = getNumber(s1);
            int num2 = getNumber(s2);
            int num3 = getNumber(s3);

            if (num1 + num2 == num3) {
                System.out.println("Solution Found:");
                System.out.println(s1 + " = " + num1);
                System.out.println(s2 + " = " + num2);
                System.out.println(s3 + " = " + num3);
                return true;
            }
            return false;
        }

        char ch = uniqueChars.get(index);

        for (int digit = 0; digit <= 9; digit++) {

            if (!used[digit]) {

                map.put(ch, digit);
                used[digit] = true;

                if (solve(uniqueChars, index + 1))
                    return true;

                // Backtrack
                map.remove(ch);
                used[digit] = false;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        // Get unique characters
        Set<Character> set = new HashSet<>();

        for (char c : s1.toCharArray()) set.add(c);
        for (char c : s2.toCharArray()) set.add(c);
        for (char c : s3.toCharArray()) set.add(c);

        List<Character> uniqueChars = new ArrayList<>(set);

        if (!solve(uniqueChars, 0)) {
            System.out.println("No solution found");
        }
    }
}
