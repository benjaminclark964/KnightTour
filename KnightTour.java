import java.util.Arrays;

public class KnightTour {

    final static int POSSIBLE_HORSE_MOVES = 8;
    private static int successfulMoves = 1;
    private static long attemptedMoves = 0;
    private static int boardSize = 0;

    public static void main(String[] args) {

        if(args.length < 4 || args.length > 4) {
            System.err.println(errorMessage());
            return;
        }

        try {
            boardSize = Integer.parseInt(args[1]);
            KnightBoard board = new KnightBoard(boardSize);
            int px = Integer.parseInt(args[2]);
            int py = Integer.parseInt(args[3]);

            if(args[0].equals("0")) {
                basicSearch(board, px, py);
                if(successfulMoves == boardSize*boardSize) {
                    printSolution(board.getBoard(), boardSize);
                } else {
                    System.out.println(attemptedMoves);
                }
            } else if(args[0].equals("1")) {
                heurtisticOne();
            } else if(args[0].equals("2")) {
                heuristicTwo();
            } else {
                System.err.println("Invalid: Must be 0, 1, or 2");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static KnightBoard basicSearch(KnightBoard board, int px, int py) {

        board.getBoard()[px][py] = successfulMoves;

        if(successfulMoves == boardSize*boardSize) {
            return board;
        }

        if(successfulMoves == 35) {
            int l = 0;
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
                if(successfulMoves == boardSize*boardSize) {
                    break;
                }
            }
            triedMoves++;
        }


        if(successfulMoves != boardSize*boardSize) {
            board.getBoard()[currentPosition.x][currentPosition.y] = 0;
            successfulMoves--;
        }

        return board;
    }

    public static void heurtisticOne() {

    }

    public static void heuristicTwo() {

    }

    public static void printSolution(int[][] board, int size) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.printf("%-4s", board[i][j]);
            }
            //create new lines
            System.out.println();
        }
    }

    public static String errorMessage() {
        String error = "Invalid number of arguments, must be 4\n" +
                "<0/1/2> <n> <x> <y>";

        return error;
    }
}
