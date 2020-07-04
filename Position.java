import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Author: Benjamin Clark
 *
 * A class to represent a position object
 *
 */
public class Position {
    private final int POSSIBLE_MOVES = 8;
    public int x; //row coordinate
    public int y; //col coordinate
    public int[] xMoves = {1, 2, 2, 1, -1, -2, -2, -1};
    public int[] yMoves = {-2, -1, 1, 2, 2, 1, -1, -2};
    public int[] previous = new int[2]; //store previous move
    public int lastUsedIndex = 8;

    /**
     * Constructor
     *
     * @param x row coordinate
     * @param y col coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * tries to move horse in clock wise motion. Updates coordinates and returns true
     * if the move is valid, else returns false
     *
     * @param x row coordinate
     * @param y col coordinate
     * @param board the current board state
     * @param i move being tried out of 8 possible moves for a horse
     * @return boolean
     */
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

    /**
     * checks for valid move and chooses untried position closest to boarder
     * and updates position
     *
     * @param x row coordinate
     * @param y col coordinate
     * @param board current board state
     * @param used contains tried indexes
     * @return true if valid position found, else false
     */
    public boolean moveHorseHeuristicOne(int x, int y, KnightBoard board, HashMap<Integer, Integer> used) {
        savePreviousPosition(x, y);
        Queue<List<Integer>> q = new LinkedList<>();
        int smallestBoarder = 8;

        for(int i = 0; i < POSSIBLE_MOVES; i++) {
            if (moveHorseClockWise(x, y, board, i)) {
                LinkedList<Integer> coordinates = new LinkedList<>();
                int borderDistance = calculateDistanceFromBorder(this.x, this.y, board.n);

                if (borderDistance < smallestBoarder && !used.containsKey(i)) {
                    lastUsedIndex = i;
                    smallestBoarder = borderDistance;
                    coordinates.add(this.x);
                    coordinates.add(this.y);
                    q.clear();
                    q.add(coordinates);
                } else if (borderDistance == smallestBoarder) {
                    continue;
                }
            }
        }


        if(!q.isEmpty()) {
            List<Integer> nextMove = q.remove();
            this.x = nextMove.remove(0);
            this.y = nextMove.remove(0);
            return true;
        } else {
            this.x = x;
            this.y = y;
        }

        return false;
    }

    /**
     * calculates the positions distance from the border
     *
     * @param x row coordinate
     * @param y col coordinate
     * @param borderSize size of board
     * @return border distance
     */
    private int calculateDistanceFromBorder(int x,  int y, int borderSize) {
        int distanceFromBorder = 0;
        int smallerVerticalDistance = getSmallerVerticalDistance(x, borderSize);
        int smallerHorizontalDifference = getSmallerHorizontalDifference(y, borderSize);

        distanceFromBorder += smallerVerticalDistance;
        distanceFromBorder += smallerHorizontalDifference;

        return distanceFromBorder;
    }

    /**
     * gets the row distance from border that is closer
     *
     * @param x row coordinate
     * @param borderSize size of board
     * @return return row distance from border
     */
    private int getSmallerVerticalDistance(int x, int borderSize) {
        int smallerVerticalDistance = 0;
        if(this.x >= Math.ceil((double)borderSize/(double)2)) {
            smallerVerticalDistance = Math.abs(x-borderSize)-1; //-1 remove space piece is on
        } else {
            smallerVerticalDistance = x;
        }

        return smallerVerticalDistance;
    }

    /**
     * gets the smaller horizontal distance from border
     *
     * @param y col coordinate
     * @param borderSize size of board
     * @return smaller horizontal distance from border
     */
    private int getSmallerHorizontalDifference(int y, int borderSize) {
        int smallerHorizontalDifference = 0;

        if(this.y >= Math.ceil((double)borderSize/(double)2)) {
            smallerHorizontalDifference = Math.abs(y-borderSize)-1; //-1 remove space piece is on
        } else {
            smallerHorizontalDifference = y;
        }

        return smallerHorizontalDifference;
    }

    /**
     * checks if position being tried is out of bounds
     *
     * @param x row coordinate
     * @param y col coordinate
     * @param i horse move being tried out of 8
     * @param board current board state
     * @return true if not out of bounds, else false
     */
    private boolean checkIfNotOutOfBounds(int x, int y, int i, KnightBoard board) {
        boolean goodCoordinates = false;
        if(y + xMoves[i] >= 0 && y + xMoves[i] < board.n
                && x + yMoves[i] >= 0 && x + yMoves[i] < board.n) {
            goodCoordinates = true;
        }

        return goodCoordinates;
    }

    /**
     * checks for valid move and chooses position based on fewest onward moves.
     * if there is a tie then select by clock wise rotation
     *
     * @param x row coordinate
     * @param y col coordinate
     * @param board current board state
     * @param used tried indexes
     * @return true if valid untried position is found
     */
    public boolean moveHorseHeuristicTwo(int x, int y, KnightBoard board, HashMap<Integer, Integer> used) {
        savePreviousPosition(x, y);
        int fewestOnwardMoves = POSSIBLE_MOVES;
        List<Integer> validCoordinates = new LinkedList<>();

        for(int i = 0; i < POSSIBLE_MOVES; i++) {
            if(moveHorseClockWise(x, y, board, i) && !used.containsKey(i)) {
                int onwardMoves = 0;
                int potentialX = this.x;
                int potentialY = this.y;
                for(int j = 0; j < POSSIBLE_MOVES; j++) {
                    if(moveHorseClockWise(potentialX, potentialY, board, j)) {
                        onwardMoves++;
                    }
                }
                if(onwardMoves < fewestOnwardMoves) {
                    fewestOnwardMoves = onwardMoves;
                    validCoordinates.clear();
                    lastUsedIndex = i;
                    validCoordinates.add(potentialX);
                    validCoordinates.add(potentialY);
                }
            }
        }

        if(!validCoordinates.isEmpty()) {
            this.x = validCoordinates.remove(0);
            this.y = validCoordinates.remove(0);
            return true;
        } else {
            this.x = x;
            this.y = y;
        }
        return false;
    }

    /**
     * saves the previous position in-case we need to backtrack
     *
     * @param x row coordinate
     * @param y col coordinate
     */
    public void savePreviousPosition(int x, int y) {
        this.previous[0] = x;
        this.previous[1] = y;
    }
}
