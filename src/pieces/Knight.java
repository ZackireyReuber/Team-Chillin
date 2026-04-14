package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

//Knight can jump in a L shape, will land where it is placed.
public class Knight extends Piece {

    /**
     * Constructs a Knight with the given color and position.
     * @param color    "white" or "black"
     * @param position the starting position
     */
    public Knight(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wN" : "bN";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int[][] offsets = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
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
