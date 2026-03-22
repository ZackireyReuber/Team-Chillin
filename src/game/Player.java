package game;

import board.Board;
import pieces.Piece;
import utils.Position;

import java.util.List;
import java.util.Scanner;

/**
 * Represents a chess player (white or black).
 * Responsible for reading input and attempting moves on the board.
 */
public class Player {

    /** The color this player controls: "white" or "black". */
    private String color;

    /** Shared scanner for reading console input. */
    private Scanner scanner;

    /**
     * Constructs a Player with the given color.
     *
     * @param color   "white" or "black"
     * @param scanner the shared console input scanner
     */
    public Player(String color, Scanner scanner) {
        this.color = color;
        this.scanner = scanner;
    }

    /**
     * Returns the color of this player.
     *
     * @return "white" or "black"
     */
    public String getColor() {
        return color;
    }

    /**
     * Prompts the player to enter a move, validates the format and basic rules,
     * and executes it on the board if valid.
     * Keeps prompting until a valid move is entered.
     *
     * @param board the current board state
     */
    public void makeMove(Board board) {
        while (true) {
            System.out.print(color.substring(0, 1).toUpperCase() + color.substring(1)
                    + "'s move (e.g. E2 E4): ");
            String input = scanner.nextLine().trim().toUpperCase();

            // Basic format validation
            if (!Utils.isValidMoveFormat(input)) {
                System.out.println("  Invalid format. Please use [FROM] [TO], e.g. \"E2 E4\".");
                continue;
            }

            String[] parts = input.split("\\s+");
            Position from = Position.fromNotation(parts[0]);
            Position to   = Position.fromNotation(parts[1]);

            if (from == null || to == null) {
                System.out.println("  Invalid squares. Use letters A-H and numbers 1-8.");
                continue;
            }

            Piece piece = board.getPiece(from);

            // Check a piece exists at the source
            if (piece == null) {
                System.out.println("  No piece at " + from + ". Try again.");
                continue;
            }

            // Check it belongs to this player
            if (!piece.getColor().equals(color)) {
                System.out.println("  That piece belongs to your opponent. Try again.");
                continue;
            }

            // Check the destination is a legal move for that piece
            List<Position> legal = piece.possibleMoves(board.getGrid());
            if (!legal.contains(to)) {
                System.out.println("  Illegal move for " + piece.getSymbol() + ". Try again.");
                continue;
            }

            // Execute the move
            board.movePiece(from, to);
            System.out.println("  Moved " + piece.getSymbol() + " from " + from + " to " + to);
            break;
        }
    }
}
