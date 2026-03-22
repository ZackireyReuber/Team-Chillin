package pieces;

import utils.Position;
import java.util.List;

/**
 * Abstract base class representing a chess piece.
 * All specific piece types extend this class.
 */
public abstract class Piece {

    /** The color of this piece: "white" or "black". */
    protected String color;

    /** The current position of this piece on the board. */
    protected Position position;

    /**
     * Constructs a Piece with the given color and position.
     *
     * @param color    "white" or "black"
     * @param position the starting position of the piece
     */
    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the color of this piece.
     *
     * @return "white" or "black"
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the current position of this piece.
     *
     * @return the position of this piece
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the position of this piece.
     *
     * @param position the new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns the 2-character symbol used to display this piece on the board.
     * White pieces start with 'w', black pieces start with 'b'.
     *
     * @return the display symbol (e.g., "wp", "bK")
     */
    public abstract String getSymbol();

    /**
     * Returns all squares this piece can legally move to from its current position,
     * ignoring check constraints. The board is passed in so pieces can inspect
     * surrounding squares.
     *
     * @param board the current 8x8 board state (may contain nulls for empty squares)
     * @return list of valid destination positions
     */
    public abstract List<Position> possibleMoves(Piece[][] board);
}
