public class KnightTourTest {
    private static int successfulMoves = 1;

    /**
     * Driver
     * @param args no args
     */
    public static void main(String[] args) {
      KnightBoard basicBoard = new KnightBoard(6);
      basicSearchTestBoard(basicBoard, 1, 1);
      assertBasicSearchBoard(basicBoard);
//      KnightBoard heuristicOneBoard = new KnightBoard(6);
//      heuristicOneTestBoard(heuristicOneBoard, 1, 1);
//      assertHeuristicOneBoard(heuristicOneBoard);
    }

    /**
     * performs ten iterations on the board using the first heuristic
     * @param board KnightBoard
     * @param px row
     * @param py col
     */
    private static void heuristicOneTestBoard(KnightBoard board, int px, int py) {
        if(successfulMoves == 11) {
            return;
        }
        Position currentPosition = new Position(px,py);
        board.getBoard()[px][py] = successfulMoves;
        int triedMoves = 0;
        while(triedMoves < 8) {
            if(currentPosition.moveHorseHeuristicOne(px, py, board, triedMoves)) {
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
