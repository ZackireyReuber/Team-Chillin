package game;

import board.Board;
import pieces.King;
import pieces.Piece;

import java.util.Scanner;

//How the game works, player turns and such.
public class Game {

    // The chessboard for this game.
    private Board board;

    // The player controlling white pieces. 
    private Player whitePlayer;

    // The player controlling black pieces. 
    private Player blackPlayer;

    // The color whose turn it currently is: "white" or "black".
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

   //starts the game,
    public void start() {
        System.out.println("       Welcome to Console Chess!        ");
        System.out.println("  Enter moves in format: E2 E4          ");
        play();
    }

//how each turn runs, shows the board and then goes from there.
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
     * checks the king to see if the game has ended.
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
