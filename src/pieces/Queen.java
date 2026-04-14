package pieces;

import utils.Position;
import java.util.ArrayList;
import java.util.List;

//The queen does what she wants, can move any direction horizontal or diagonal
public class Queen extends Piece {

    /**
     * Constructs a Queen with the given color and position.
     * @param color    "white" or "black"
     * @param position the starting position
     */
    public Queen(String color, Position position) {
        super(color, position);
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "wQ" : "bQ";
    }

    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        // all directions besides horsie is allowed
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
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
