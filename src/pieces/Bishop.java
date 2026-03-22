package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Bishop chess piece.
 * The bishop moves any number of squares diagonally.
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop with the given color and position.
     *
     * @param color    "white" or "black"
     * @param position the starting position
     */
    public Bishop(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wB" : "bB";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int[] d : directions) {
            int r = position.getRow() + d[0];
            int c = position.getCol() + d[1];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Piece target = board[r][c];
                if (target == null) {
                    moves.add(new Position(r, c));
                } else {
                    if (!target.getColor().equals(color)) moves.add(new Position(r, c));
                    break;
                }
                r += d[0]; c += d[1];
            }
        }
        return moves;
    }
}
