import java.util.Scanner;

public class EightQueensDLS {

    static final int N = 8;
    static int LIMIT;

    static boolean isSafe(int[][] board, int row, int col) {

        // Column check
        for (int i = 0; i < row; i++)
            if (board[i][col] == 1)
                return false;

        // Left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Right diagonal
        for (int i = row, j = col; i >= 0 && j < N; i--, j++)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    static boolean dls(int[][] board, int row) {

        if (row == LIMIT)
            return true;

        for (int col = 0; col < N; col++) {

            if (isSafe(board, row, col)) {

                board[row][col] = 1;

                if (dls(board, row + 1))
                    return true;

                board[row][col] = 0;
            }
        }

        return false;
    }

    static void printBoard(int[][] board) {
        for (int i = 0; i < LIMIT; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter depth limit (1 to 8): ");
        LIMIT = sc.nextInt();

        sc.close();

        if (LIMIT < 1 || LIMIT > N) {
            System.out.println("Invalid depth limit. Please enter a value between 1 and 8.");
            return;
        }

        int[][] board = new int[N][N];

        if (dls(board, 0)) {
            System.out.println("Partial solution up to depth " + LIMIT + ":\n");
            printBoard(board);
        } else {
            System.out.println("No solution within depth = " + LIMIT);
        }
    }
}
