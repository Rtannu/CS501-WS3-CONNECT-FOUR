import java.util.Arrays;
import java.util.Scanner;


public class FinalProjectConnectFourGame {

    // Characters for players [ R => RED, Y => YELLOW ]
    private static final char[] PLAYERS = {'R', 'Y'};

    // Width and Height of board
    private final int WIDTH;
    private final int HEIGHT;

    // Grid of board
    private final char[][] gridOfBoard;

    // Storing last move of a player
    private int lastMoveColumn = -1;
    private int lastMoveTop = -1;

    // Gridboard filler
    private static final char GRID_FILLER_SYMBOL = '.';

    public FinalProjectConnectFourGame(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        gridOfBoard = new char[height][];

        // init the grid will blank cell
        for (int i = 0; i < height; i++) {
            Arrays.fill(gridOfBoard[i] = new char[width], GRID_FILLER_SYMBOL);
        }
    }


    //  Representation of the game board
    public String toString() {
        return getColumnRage() +
                "\n" + getGrid();
    }

    /**
     * Getting the column range
     *
     * @return
     */
    private String getColumnRage() {
        String range = "";
        for (Integer i = 0; i < WIDTH; i++) {
            range += i.toString();
        }
        return range;
    }

    /**
     * Getting Grid
     *
     * @return
     */
    private String getGrid() {
        String grid = "";
        for (int i = 0; i < gridOfBoard.length; i++) {
            grid = grid + new String(gridOfBoard[i]);
            grid += "\n";
        }
        return grid;
    }


    /**
     * String representation of row of last play of user
     *
     * @return
     */
    public String horizontalLastPlayUser() {
        return new String(gridOfBoard[lastMoveTop]);
    }

    /**
     * String representation of column of last play of user
     *
     * @return
     */
    public String verticalLastPlayUser() {
        StringBuilder sb = new StringBuilder(HEIGHT);

        for (int height = 0; height < HEIGHT; height++) {
            sb.append(gridOfBoard[height][lastMoveColumn]);
        }

        return sb.toString();
    }


    /**
     * String representation of forwardslash of last play of the user
     *
     * @return
     */
    public String forwardSlashDiagonalLastPlayUser() {
        StringBuilder sb = new StringBuilder(HEIGHT);

        for (int h = 0; h < HEIGHT; h++) {
            int w = lastMoveColumn + lastMoveTop - h;

            if (0 <= w && w < WIDTH) {
                sb.append(gridOfBoard[h][w]);
            }
        }

        return sb.toString();
    }


    /**
     * String representation of backwardslash of last play of the user
     *
     * @return
     */
    public String backslashDiagonalLastPlayUser() {
        StringBuilder sb = new StringBuilder(HEIGHT);

        for (int h = 0; h < HEIGHT; h++) {
            int w = lastMoveColumn - lastMoveTop + h;

            if (0 <= w && w < WIDTH) {
                sb.append(gridOfBoard[h][w]);
            }
        }
        return sb.toString();
    }

    /**
     * Checking whether substring is part of string or not
     *
     * @param str
     * @param substring
     * @return
     */
    public static boolean isStrContainsSubString(String str, String substring) {
        return str.indexOf(substring) >= 0;
    }

    /**
     * Checking player is winner or not
     *
     * @param streak
     * @return
     */
    public boolean isPlayerWinner(String streak) {
        return isStrContainsSubString(horizontalLastPlayUser(), streak) ||
                isStrContainsSubString(verticalLastPlayUser(), streak) ||
                isStrContainsSubString(forwardSlashDiagonalLastPlayUser(), streak) ||
                isStrContainsSubString(backslashDiagonalLastPlayUser(), streak);
    }

    /**
     * Checking whether player wins the game or not
     *
     * @return
     */
    public boolean isPlayerWinningGame() {
        if (lastMoveColumn == -1) {
            System.err.println("Player didn't take any move yet");
            return false;
        }

        char lastMoveSym = gridOfBoard[lastMoveTop][lastMoveColumn];

        // Streak of the last play symbol
        String streak = String.format("%c%c%c%c", lastMoveSym, lastMoveSym, lastMoveSym, lastMoveSym);

        // Checking whether streak of the last play symbol is in row, col, forwardslash or backwardslah diagonal
        return isPlayerWinner(streak);
    }

    /**
     * User will select the column and Fill the Board
     *
     * @param symbol
     * @param input
     */
    public void chooseColumnAndFillBoard(char symbol, Scanner input) {
        do {
            int choosenColumn = 0;
            boolean continueFlag = true;
            do {
                try {
                    // Message for users of turn
                    System.out.println("\nPlayer " + getPlayerByPlayerSymbol(symbol) + " turn: ");
                    System.out.println("Use 0-" + (WIDTH - 1) + " to select a column");

                    choosenColumn = input.nextInt();
                    continueFlag = false;
                } catch (Exception exception) {
                    input.nextLine();
                    System.out.println("Try again. (Incorrect input, Integer is required)\n");
                }
            } while (continueFlag);


            // Checking column whether it's valid or not
            if (!isValidColumn(choosenColumn, WIDTH)) {
                System.out.println("Column must be between 0 and " + (WIDTH - 1));
                continue;
            }


            // Place the symbol in selected column in first available row
            for (int height = HEIGHT - 1; height >= 0; height--) {
                if (gridOfBoard[height][choosenColumn] == GRID_FILLER_SYMBOL) {
                    gridOfBoard[lastMoveTop = height][lastMoveColumn = choosenColumn] = symbol;
                    return;
                }
            }

            // If the column is already full then user will be asked for next input.
            System.out.println("Column " + choosenColumn + " is full, Please select another column");
        } while (true);
    }

    /**
     * Entry point of connect-four game
     *
     * @param args
     */
    public static void main(String[] args) {

        int repeatFlag = 1;
        // Repeat loop
        while (repeatFlag == 1) {
            Scanner scanner = new Scanner(System.in);

            // Dimension height | width of board
            final int HEIGHT_OF_BOARD = 6;
            final int WIDTH_OF_BOARD = 8;

            // Total moves
            int totalMoves = HEIGHT_OF_BOARD * WIDTH_OF_BOARD;

            // Instance of connectFourGameBoard
            FinalProjectConnectFourGame connectFourGameBoard = new FinalProjectConnectFourGame(WIDTH_OF_BOARD, HEIGHT_OF_BOARD);

            // Users how to enter their choices
            System.out.println("Use 0-" + (WIDTH_OF_BOARD - 1) + " to select a column");

            // Display initial board
            System.out.println(connectFourGameBoard);


            // Play game
            for (int player = 0; totalMoves-- > 0; player = 1 - player) {

                // Symbol of current player
                char currentPlayerSymbol = PLAYERS[player];

                // we ask user to choose a column
                connectFourGameBoard.chooseColumnAndFillBoard(currentPlayerSymbol, scanner);

                // Displaying connect four game board
                System.out.println(connectFourGameBoard);

                // continuously checking whether a player wins or not
                if (connectFourGameBoard.isPlayerWinningGame()) {
                    System.out.println("\nPlayer " + getPlayerByPlayerSymbol(currentPlayerSymbol) + " is winner!");
                    break;
                }
            }
            if (totalMoves <= 0) {
                System.out.println("Game over. No winner. Try again!");
            }

            boolean exitContinueFlag = true;
            do {
                try {
                    System.out.println("\nEnter 1 to continue the program\n" +
                            "Enter any number except 1 to exit from program");
                    repeatFlag = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println();
                    exitContinueFlag = false;
                } catch (Exception exception) {
                    System.out.println("Try again. (Incorrect input: Integer is required)");
                    scanner.nextLine();
                }
            } while (exitContinueFlag);
        }
    }

    /**
     * Getting Player by player-symbol
     *
     * @param symbol
     * @return
     */
    private static String getPlayerByPlayerSymbol(char symbol) {
        String player = null;
        if (symbol == 'R') {
            player = "RED";
        } else {
            player = "YELLOW";
        }
        return player;
    }

    /**
     * Checking whether the column is valid or not
     *
     * @param choosenColumn
     * @param WIDTH
     * @return
     */
    private static boolean isValidColumn(int choosenColumn, int WIDTH) {
        if ((0 <= choosenColumn && choosenColumn < WIDTH)) {
            return true;
        } else {
            return false;
        }
    }

}
