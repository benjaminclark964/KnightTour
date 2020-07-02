public class KnightBoard {
    public int[][] board;
    public final int n;
    public int moveNum = 0;

    public KnightBoard(int n) {
        this.n = n;
        this.board = new int[n][n];
    }

    public boolean isValidPosition(int x, int y) {
        boolean isValid = true;

        if(this.board[y][x] != 0) {
            isValid = false;
        }

        return isValid;
    }

//    public void updateBoard(int[][] board, int px, int py, int move) {
//        board[px][py] = move;
//    }

    public boolean checkIfDone(long[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return this.board;
    }
}
