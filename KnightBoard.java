/**
 * Author: Benjamin Clark
 *
 * A class to represent a board object
 *
 */
public class KnightBoard {
    public int[][] board;
    public final int n;

    /**
     * Constructor
     *
     * @param n dimensions of board
     */
    public KnightBoard(int n) {
        this.n = n;
        this.board = new int[n][n];
    }

    /**
     * checks if position is valid
     *
     * @param x row coordinate
     * @param y col coordinate
     * @return true or false
     */
    public boolean isValidPosition(int x, int y) {
        boolean isValid = true;

        if(this.board[y][x] != 0) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * adds new position to board
     *
     * @param board Knight board
     * @param px row coordinate
     * @param py col coordinate
     * @param move value for coordinate
     */
    public void updateBoard(int[][] board, int px, int py, int move) {
        board[px][py] = move;
    }

    /**
     * gets 2D array representation of the board
     *
     * @return 2D array of board
     */
    public int[][] getBoard() {
        return this.board;
    }
}
