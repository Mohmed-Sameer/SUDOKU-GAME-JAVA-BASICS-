import java.util.Scanner;

public class SUDOKU {
    public static void display(int[][] board) {
        // Display the Sudoku puzzle
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0) {
                System.out.println("---------------------");
            }
        }
    }

    public static boolean isSafe(int[][] board, int row, int col, int num) {
        // Check if the number is safe to be placed at position (row, col)
        for (int d = 0; d < 9; d++) {
            if (board[row][d] == num || board[d][col] == num) {
                return false;
            }
        }

        int boxRowStart = 3 * (row / 3);
        int boxColStart = 3 * (col / 3);

        for (int i = boxRowStart; i < boxRowStart + 3; i++) {
            for (int j = boxColStart; j < boxColStart + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board) {
        int row = -1;
        int col = -1;
        boolean isEmpty = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = true;
                    break;
                }
            }
            if (isEmpty) {
                break;
            }
        }

        if (!isEmpty) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) {
                    return true;
                } else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[][] sudokuBoard = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Sudoku Game!");
        System.out.println("To fill in a number, enter the row (1-9) and column (1-9) followed by the number.\n");
        System.out.println("For example, to fill in 5 at row 3 and column 7, enter: 3 7 5");
        System.out.println("To check the solution, enter 'solve'.");
        System.out.println("To quit the game, enter 'quit'.\n");

        while (true) {
            System.out.println("Current Sudoku Board:");
            display(sudokuBoard);

            System.out.print("\n your move: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("solve")) {
                int[][] copyBoard = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    System.arraycopy(sudokuBoard[i], 0, copyBoard[i], 0, 9);
                }

                if (solveSudoku(copyBoard)) {
                    System.out.println("\nSudoku Solved! Here's the solution:\n");
                    display(copyBoard);
                } else {
                    System.out.println("\nNo solution found for the current board!");
                }
                break;
            } else if (input.equalsIgnoreCase("quit")) {
                System.out.println("Quitting the game. Goodbye!");
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("Invalid input! Please enter row, column, and number separated by spaces.");
                continue;
            }

            try {
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;
                int number = Integer.parseInt(parts[2]);

                if (row < 0 || row >= 9 || col < 0 || col >= 9 || number < 1 || number > 9) {
                    System.out.println("Invalid input! Row and column should be between 1 and 9, and number should be between 1 and 9.");
                    continue;
                }

                if (sudokuBoard[row][col] != 0) {
                    System.out.println("Invalid move! The cell is already filled.");
                    continue;
                }

                if (!isSafe(sudokuBoard, row, col, number)) {
                    System.out.println("Invalid move! The number violates the Sudoku rules.");
                    continue;
                }

                sudokuBoard[row][col] = number;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter valid numbers.");
                continue;
            }
        }

        scanner.close();
}
}