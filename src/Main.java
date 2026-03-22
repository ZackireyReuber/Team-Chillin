import game.Game;

/**
 * Entry point for the Console Chess application.
 * Creates and starts a new game.
 */
public class Main {

    /**
     * Main method — launches the chess game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
