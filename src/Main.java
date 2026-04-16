import gui.ChessGUI;
import javax.swing.SwingUtilities;

/**
 * Entry point for the Chess Game GUI application.
 * Launches the Swing GUI on the Event Dispatch Thread.
 */
public class Main {
    /**
     * Main method — starts the chess GUI.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGUI gui = new ChessGUI();
            gui.setVisible(true);
        });
    }
}