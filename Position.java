import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Position {
    private final int POSSIBLE_MOVES = 8;
    public int x;
    public int y;
    public int[] xMoves = {1, 2, 2, 1, -1, -2, -2, -1};
    public int[] yMoves = {-2, -1, 1, 2, 2, 1, -1, -2};
    public int[] previous = new int[2];
    public int lastUsedIndex = 8;

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

    public boolean moveHorseHeuristicOne(int x, int y, KnightBoard board, HashMap<Integer, Integer> used) {
        savePreviousPosition(x, y);
        Queue<List<Integer>> q = new LinkedList<>();
        int smallestBoarder = 8;

        for(int i = 0; i < 8; i++) {
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

    private int calculateDistanceFromBorder(int x,  int y, int borderSize) {
        int distanceFromBorder = 0;
        int smallerVerticalDistance = getSmallerVerticalDistance(x, borderSize);
        int smallerHorizontalDifference = getSmallerHorizontalDifference(y, borderSize);

        distanceFromBorder += smallerVerticalDistance;
        distanceFromBorder += smallerHorizontalDifference;

        return distanceFromBorder;
    }

    private int getSmallerVerticalDistance(int x, int borderSize) {
        int smallerVerticalDistance = 0;
        if(this.x >= Math.ceil((double)borderSize/(double)2)) {
            smallerVerticalDistance = Math.abs(x-borderSize)-1;
        } else {
            smallerVerticalDistance = x;
        }

        return smallerVerticalDistance;
    }

    private int getSmallerHorizontalDifference(int y, int borderSize) {
        int smallerHorizontalDifference = 0;

        if(this.y >= Math.ceil((double)borderSize/(double)2)) {
            smallerHorizontalDifference = Math.abs(y-borderSize)-1;
        } else {
            smallerHorizontalDifference = y;
        }

        return smallerHorizontalDifference;
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
