
public class EightQueensDFS {

    static final int N = 8;

    // Check if placing queen is safe
    static boolean isSafe(int[][] board, int row, int col) {

        // Check column
        for (int i = 0; i < row; i++)
            if (board[i][col] == 1)
                return false;

        // Check upper left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check upper right diagonal
        for (int i = row, j = col; i >= 0 && j < N; i--, j++)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    static boolean dfs(int[][] board, int row) {

        // All queens placed
        if (row == N)
            return true;

        // Try each column
        for (int col = 0; col < N; col++) {

            if (isSafe(board, row, col)) {

                board[row][col] = 1; // place queen

                // Recur for next row
                if (dfs(board, row + 1))
                    return true;

                // Backtrack
                board[row][col] = 0;
            }
        }

        return false;
    }

    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int val : row)
                System.out.print(val + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int[][] board = new int[N][N];

        if (dfs(board, 0)) {
            System.out.println("Solution found:\n");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }
    }
}
