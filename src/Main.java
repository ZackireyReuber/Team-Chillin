<<<<<<< HEAD
import gui.ChessGUI;
import javax.swing.SwingUtilities;
=======
import game.Game;

//starts the game, it is infact main.
>>>>>>> 9e2709120496682d28220d8be23631c03e6c57d0
public class Main {
    /**
<<<<<<< HEAD
     * @param args
=======
     * Main method — launches the chess game.
     * @param args command-line arguments (not used)
>>>>>>> 9e2709120496682d28220d8be23631c03e6c57d0
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGUI gui = new ChessGUI();
            gui.setVisible(true);
        });
    }
}
