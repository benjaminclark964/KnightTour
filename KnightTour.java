import java.util.HashMap;

/**
 * author: Benjamin Clark
 *
 * Searches for a solution for the knights tour problem using back tracking and three different search methods
 * for optimization.
 *
 * Basic Search: chooses horse move based on the clock wise rotation
 *
 * Heuristic One: chooses horse move based on move closest to the border of
 * the chess board. If there is a tie then pick based on clock wise rotation
 *
 * Heuristic Two: chooses horse move based on the fewest onward moves. If
 * there is a tie then choose based on clock wise rotation
 *
 */
public class KnightTour {

    final static int POSSIBLE_HORSE_MOVES = 8;
    private static int successfulMoves = 1;
    private static long attemptedMoves = 1;
    private static int boardSize = 0;
    private static int full = 0;

    /**
     * Driver
     * @param args <0/1/2> <n> <x> <y>
     */
    public static void main(String[] args) {

        if(args.length < 4 || args.length > 4) {
            System.err.println(errorMessage());
            return;
        }

        try {
            boardSize = Integer.parseInt(args[1]);
            full = boardSize*boardSize;
            KnightBoard board = new KnightBoard(boardSize);
            int px = Integer.parseInt(args[2]);
            int py = Integer.parseInt(args[3]);

            if(args[0].equals("0")) {
                basicSearch(board, px, py);
            } else if(args[0].equals("1")) {
                heuristicOne(board, px, py);
            } else if(args[0].equals("2")) {
                heuristicTwo(board, px, py);
            } else {
                System.err.println("Invalid: Must be 0, 1, or 2");
                return;
            }
            printResults(board);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * searches for a solution by choosing moves in a clockwise rotation.
     *
     * @param board current board
     * @param px row coordinate
     * @param py col coordinate
     * @return the current board state
     */
    private static KnightBoard basicSearch(KnightBoard board, int px, int py) {

        board.updateBoard(board.getBoard(), px, py, successfulMoves);

        if(successfulMoves == full) {
            return board;
        }

        Position currentPosition = new Position(px, py);
        int triedMoves = 0;

        while(triedMoves < POSSIBLE_HORSE_MOVES) {

            if(currentPosition.moveHorseClockWise(px, py, board, triedMoves)) {
                attemptedMoves++;
                successfulMoves++;
                basicSearch(board, currentPosition.x, currentPosition.y);
                currentPosition.x = currentPosition.previous[0];
                currentPosition.y = currentPosition.previous[1];
                if(successfulMoves == full) {
                    break;
                }
            }
            triedMoves++;
        }

        if(successfulMoves != full) {
            removeCurrentPosition(board, currentPosition);
        }

        return board;
    }

    /**
     * searches for a solution using heuristic one. Chooses the move
     * closest to the board border.
     *
     * @param board board
     * @param px row coordinate
     * @param py col coordinate
     * @return the current board state
     */
    private static KnightBoard heuristicOne(KnightBoard board, int px, int py) {
        board.updateBoard(board.getBoard(), px, py, successfulMoves);

        if(successfulMoves == full) {
            return board;
        }

        Position currentPosition = new Position(px, py);
        int triedMoves = 0;
        HashMap<Integer, Integer> usedIndexes = new HashMap<>();

        while(triedMoves < POSSIBLE_HORSE_MOVES) {
            if(currentPosition.moveHorseHeuristicOne(px, py, board, usedIndexes)) {
                successfulMoves++;
                attemptedMoves++;
                usedIndexes.put(currentPosition.lastUsedIndex, triedMoves);
                heuristicOne(board, currentPosition.x, currentPosition.y);
                currentPosition.x = currentPosition.previous[0];
                currentPosition.y = currentPosition.previous[1];
            }
            triedMoves++;
        }

        if(successfulMoves != full) {
            removeCurrentPosition(board, currentPosition);
        }

        return board;
    }

    /**
     * searches for a solution using heuristic two. Chooses the moves with
     * fewest onward moves
     *
     * @param board knight board
     * @param px row coordinate
     * @param py col coordinate
     * @return the current board
     */
    private static KnightBoard heuristicTwo(KnightBoard board, int px, int py) {
        board.updateBoard(board.getBoard(), px, py, successfulMoves);

        if(successfulMoves == full) {
            return board;
        }

        Position currentPosition = new Position(px, py);
        int triedMoves = 0;
        HashMap<Integer, Integer> usedIndexes = new HashMap<>();

        while(triedMoves < POSSIBLE_HORSE_MOVES) {
            if(currentPosition.moveHorseHeuristicTwo(px, py, board, usedIndexes)) {
                successfulMoves++;
                attemptedMoves++;
                usedIndexes.put(currentPosition.lastUsedIndex, triedMoves);
                heuristicTwo(board, currentPosition.x, currentPosition.y);
                currentPosition.x = currentPosition.previous[0];
                currentPosition.y = currentPosition.previous[1];
            }
            triedMoves++;
        }

        if(successfulMoves != full) {
            removeCurrentPosition(board, currentPosition);
        }

        return board;
    }

    /**
     * removes current position on the board
     * @param board The board
     * @param currentPosition the current position on the board
     */
    private static void removeCurrentPosition(KnightBoard board, Position currentPosition) {
        board.updateBoard(board.getBoard(), currentPosition.x, currentPosition.y, 0);
        successfulMoves--;
    }

    /**
     * prints board if a solution is found
     * @param board the board
     * @param size board size
     */
    private static void printSolution(int[][] board, int size) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.printf("%-4s", board[i][j]);
            }
            //create new lines
            System.out.println();
        }
    }

    /**
     * prints the attempted moves and the board if a solution is found
     * @param board the board
     */
    private static void printResults(KnightBoard board) {
        if(successfulMoves == boardSize*boardSize) {
            System.out.println("The total number of moves is " + attemptedMoves);
            printSolution(board.getBoard(), boardSize);
        } else {
            System.out.println("The total number of moves is " + attemptedMoves);
            System.err.print("No Solution Found!");
        }
    }

    /**
     * returns error message if arguments are not correct
     * @return error message
     */
    private static String errorMessage() {
        String error = "Invalid number of arguments, must be 4\n" +
                "<0/1/2> <n> <x> <y>";

        return error;
    }
}
