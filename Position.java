public class Position {
    public int x;
    public int y;
    public int[] xMoves = {1, 2, 2, 1, -1, -2, -2, -1};
    public int[] yMoves = {-2, -1, 1, 2, 2, 1, -1, -2};
    public int[] previous = new int[2];

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean moveHorseClockWise(int x, int y, KnightBoard board, int i) {
        savePreviousPosition(x, y);

        if(checkIfNotOutOfBounds(x, y, i, board)) {
            if (board.isValidPosition(xMoves[i] + y, yMoves[i] + x)) {
                this.x = yMoves[i] + x;
                this.y = xMoves[i] + y;
                return true;
            }
        }

        return false;
    }

    public boolean moveHorseHeuristicOne(int x, int y, KnightBoard board, int i) {
        savePreviousPosition(x, y);

        return false;
    }

    private boolean checkIfNotOutOfBounds(int x, int y, int i, KnightBoard board) {
        boolean goodCoordinates = false;
        if(y + xMoves[i] >= 0 && y + xMoves[i] < board.n
                && x + yMoves[i] >= 0 && x + yMoves[i] < board.n) {
            goodCoordinates = true;
        }

        return goodCoordinates;
    }

    public void savePreviousPosition(int x, int y) {
        this.previous[0] = x;
        this.previous[1] = y;
    }

}
