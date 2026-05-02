public class CSPEightQueens {

    static final int N = 8;

    // Function to print solution
    static void printSolution(int board[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Check if safe to place queen
    static boolean isSafe(int board[][], int row, int col) {

        int i, j;

        // Check column
        for (i = 0; i < row; i++) {
            if (board[i][col] == 1)
                return false;
        }

        // Check upper left diagonal
        for (i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }

        // Check upper right diagonal
        for (i = row - 1, j = col + 1; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    // Backtracking function
    static boolean solveQueens(int board[][], int row) {

        // Base case: all queens placed
        if (row == N) {
            return true;
        }

        // Try placing queen in all columns
        for (int col = 0; col < N; col++) {

            if (isSafe(board, row, col)) {

                board[row][col] = 1; // place queen

                // Recur for next row
                if (solveQueens(board, row + 1)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        int board[][] = new int[N][N];

        if (solveQueens(board, 0)) {
            printSolution(board);
        } else {
            System.out.println("No solution exists");
        }
    }
}
