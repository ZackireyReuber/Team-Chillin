package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

//pawns can move two foward and can capture diag
public class Pawn extends Piece {

    /**
     * Constructs a Pawn with the given color and starting position.
     * @param color    "white" or "black"
     * @param position the starting position of the pawn
     */
    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wp" : "bp";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();
        int direction = color.equals("white") ? 1 : -1;
        int startRow   = color.equals("white") ? 1 : 6;

        // one
        Position oneStep = new Position(row + direction, col);
        if (oneStep.isValid() && board[oneStep.getRow()][oneStep.getCol()] == null) {
            moves.add(oneStep);

            // two
            if (row == startRow) {
                Position twoStep = new Position(row + 2 * direction, col);
                if (board[twoStep.getRow()][twoStep.getCol()] == null) {
                    moves.add(twoStep);
                }
            }
        }

        // Diagonal captures
        int[] captureCols = {col - 1, col + 1};
        for (int capCol : captureCols) {
            Position capture = new Position(row + direction, capCol);
            if (capture.isValid()) {
                Piece target = board[capture.getRow()][capture.getCol()];
                if (target != null && !target.getColor().equals(color)) {
                    moves.add(capture);
                }
            }
        }

        return moves;
    }
}
