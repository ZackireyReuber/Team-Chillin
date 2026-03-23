import game.Game;

//starts the game, it is infact main.
public class Main {

    /**
     * Main method — launches the chess game.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
