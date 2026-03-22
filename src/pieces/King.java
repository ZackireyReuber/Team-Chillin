package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King chess piece.
 * The king moves exactly one square in any direction.
 */
public class King extends Piece {

    /**
     * Constructs a King with the given color and position.
     *
     * @param color    "white" or "black"
     * @param position the starting position
     */
    public King(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wK" : "bK";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int[][] offsets = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int[] o : offsets) {
            Position p = new Position(position.getRow() + o[0], position.getCol() + o[1]);
            if (p.isValid()) {
                Piece target = board[p.getRow()][p.getCol()];
                if (target == null || !target.getColor().equals(color)) {
                    moves.add(p);
                }
            }
        }
        return moves;
    }
}
