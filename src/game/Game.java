package game;

import board.Board;
import pieces.King;
import pieces.Piece;

import java.util.Scanner;

/**
 * Controls the overall flow of a chess game.
 * Manages the board, both players, and turn alternation.
 */
public class Game {

    /** The chessboard for this game. */
    private Board board;

    /** The player controlling white pieces. */
    private Player whitePlayer;

    /** The player controlling black pieces. */
    private Player blackPlayer;

    /** The color whose turn it currently is: "white" or "black". */
    private String currentTurn;

    /** Shared console input scanner. */
    private Scanner scanner;

    /**
     * Constructs a new Game, initializing the board and both players.
     */
    public Game() {
        scanner = new Scanner(System.in);
        board = new Board();
        whitePlayer = new Player("white", scanner);
        blackPlayer = new Player("black", scanner);
        currentTurn = "white";
    }

    /**
     * Starts the game by displaying a welcome message and entering the main play loop.
     */
    public void start() {
        System.out.println("========================================");
        System.out.println("       Welcome to Console Chess!        ");
        System.out.println("  Enter moves in format: E2 E4          ");
        System.out.println("========================================");
        play();
    }

    /**
     * The main game loop. Alternates turns between white and black players,
     * displays the board before each move, and checks for game-ending conditions.
     */
    public void play() {
        while (true) {
            board.display();

            // Check if a king is missing (captured) — simple end condition for Phase 1
            if (!kingExists("white")) {
                end("black");
                return;
            }
            if (!kingExists("black")) {
                end("white");
                return;
            }

            // Get the current player's move
            Player current = currentTurn.equals("white") ? whitePlayer : blackPlayer;
            current.makeMove(board);

            // Switch turns
            currentTurn = currentTurn.equals("white") ? "black" : "white";
        }
    }

    /**
     * Ends the game and announces the winner or a draw.
     *
     * @param winner the color that won, or null for a draw
     */
    public void end(String winner) {
        board.display();
        System.out.println("========================================");
        if (winner != null) {
            System.out.println("  Game over! "
                    + winner.substring(0, 1).toUpperCase() + winner.substring(1)
                    + " wins!");
        } else {
            System.out.println("  Game over! It's a draw.");
        }
        System.out.println("========================================");
        scanner.close();
    }

    /**
     * Checks whether the king of the specified color still exists on the board.
     * Used as a simple game-over condition for Phase 1.
     *
     * @param color "white" or "black"
     * @return true if that color's king is present on the board
     */
    private boolean kingExists(String color) {
        for (Piece[] row : board.getGrid()) {
            for (Piece piece : row) {
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }
}
