import java.util.HashMap;

public class KnightTourTest {
    private static int successfulMoves = 1;

    /**
     * Driver
     * @param args no args
     */
    public static void main(String[] args) {    //comment out the versions you are not wanting to test
        //basic search tests
//      KnightBoard basicBoard = new KnightBoard(6);
//      basicSearchTestBoard(basicBoard, 1, 1);
//      assertBasicSearchBoard(basicBoard);
      //heuristic one tests
//      KnightBoard heuristicOneBoard = new KnightBoard(6);
//      heuristicOneTestBoard(heuristicOneBoard, 1, 1);
//      assertHeuristicOneBoard(heuristicOneBoard);
      //heuristic two tests
        KnightBoard heuristicTwoBoard = new KnightBoard(6);
        heuristicTwoTestBoard(heuristicTwoBoard, 1, 1);
        assertHeuristicTwoBoard(heuristicTwoBoard);
    }

    private static void heuristicTwoTestBoard(KnightBoard board, int px, int py) {
        board.getBoard()[px][py] = successfulMoves;

        if(successfulMoves == 11) {
            return;
        }

        if(successfulMoves == 26) {
            int l = 0;
        }

        Position currentPosition = new Position(px, py);
        int triedMoves = 0;
        HashMap<Integer, Integer> usedIndexes = new HashMap<>();
        while(triedMoves < 8) {
            if(currentPosition.moveHorseHeuristicTwo(px, py, board, usedIndexes)) {
                successfulMoves++;
                if(successfulMoves == 11) {
                    return;
                }
                if(successfulMoves == 2) {
                    int t = 0;
                }
                usedIndexes.put(currentPosition.lastUsedIndex, triedMoves);
                heuristicTwoTestBoard(board, currentPosition.x, currentPosition.y);
                currentPosition.x = currentPosition.previous[0];
                currentPosition.y = currentPosition.previous[1];
                if(successfulMoves == 11) {
                    return;
                }
            }
            triedMoves++;
        }

        if(successfulMoves != 36) {
            board.getBoard()[currentPosition.x][currentPosition.y] = 0;
            successfulMoves--;
        }

        return;
    }

    private static void assertHeuristicTwoBoard(KnightBoard testBoard) {
        assert testBoard.getBoard()[1][1] == 1;
        assert testBoard.getBoard()[0][3] == 2;
        assert testBoard.getBoard()[1][5] == 3;
        assert testBoard.getBoard()[3][4] == 4;
        assert testBoard.getBoard()[5][5] == 5;
        assert testBoard.getBoard()[4][3] == 6;
        assert testBoard.getBoard()[5][1] == 7;
        assert testBoard.getBoard()[3][0] == 8;
        assert testBoard.getBoard()[2][2] == 9;
        assert testBoard.getBoard()[1][0] == 10;
    }

    /**
     * performs ten iterations on the board using the first heuristic
     * @param board KnightBoard
     * @param px row
     * @param py col
     */
    private static void heuristicOneTestBoard(KnightBoard board, int px, int py) {
        board.getBoard()[px][py] = successfulMoves;

        if(successfulMoves == 11) {
            return;
        }

        if(successfulMoves == 26) {
            int l = 0;
        }

        Position currentPosition = new Position(px, py);
        int triedMoves = 0;
        HashMap<Integer, Integer> usedIndexes = new HashMap<>();
        while(triedMoves < 8) {
            if(currentPosition.moveHorseHeuristicOne(px, py, board, usedIndexes)) {
                successfulMoves++;
                if(successfulMoves == 11) {
                    return;
                }
                if(successfulMoves == 8) {
                    int t = 0;
                }
                usedIndexes.put(currentPosition.lastUsedIndex, triedMoves);
                heuristicOneTestBoard(board, currentPosition.x, currentPosition.y);
                currentPosition.x = currentPosition.previous[0];
                currentPosition.y = currentPosition.previous[1];
            }
            triedMoves++;
        }

        if(successfulMoves != 36) {
            board.getBoard()[currentPosition.x][currentPosition.y] = 0;
            successfulMoves--;
        }

        return;
    }

    /**
     * asserts the first ten positions on the board are correct
     * @param testBoard KnightBoard
     */
    private static void assertHeuristicOneBoard(KnightBoard testBoard) {
        assert testBoard.getBoard()[1][1] == 1;
        assert testBoard.getBoard()[0][3] == 2;
        assert testBoard.getBoard()[1][5] == 3;
        assert testBoard.getBoard()[3][4] == 4;
        assert testBoard.getBoard()[5][5] == 5;
        assert testBoard.getBoard()[4][3] == 6;
        assert testBoard.getBoard()[5][1] == 7;
        assert testBoard.getBoard()[3][0] == 8;
        assert testBoard.getBoard()[4][2] == 9;
        assert testBoard.getBoard()[5][0] == 10;
    }

    /**
     * Performs ten iterations on the board using basic search
     * @param board KnightBoard
     * @param px row
     * @param py col
     */
    private static void basicSearchTestBoard(KnightBoard board, int px, int py) {
        if(successfulMoves == 11) {
            return;
        }
        Position currentPosition = new Position(px,py);
        board.getBoard()[px][py] = successfulMoves;
        int triedMoves = 0;
        while(triedMoves < 8) {
            if(currentPosition.moveHorseClockWise(px, py, board, triedMoves)) {
                if(successfulMoves == 11) {
                    return;
                }
                successfulMoves++;
                basicSearchTestBoard(board, currentPosition.x, currentPosition.y);
                currentPosition.x = currentPosition.previous[0];
                currentPosition.y = currentPosition.previous[1];
            }
            triedMoves++;
        }
    }

    /**
     * asserts the first ten values are what they should be according
     * to basic search
     * @param testBoard KnightBoard with ten iterations
     */
    private static void assertBasicSearchBoard(KnightBoard testBoard) {
        assert testBoard.getBoard()[1][1] == 1;
        assert testBoard.getBoard()[0][3] == 2;
        assert testBoard.getBoard()[1][5] == 3;
        assert testBoard.getBoard()[3][4] == 4;
        assert testBoard.getBoard()[5][5] == 5;
        assert testBoard.getBoard()[4][3] == 6;
        assert testBoard.getBoard()[2][4] == 7;
        assert testBoard.getBoard()[0][5] == 8;
        assert testBoard.getBoard()[1][3] == 9;
        assert testBoard.getBoard()[2][5] == 10;
    }
}
