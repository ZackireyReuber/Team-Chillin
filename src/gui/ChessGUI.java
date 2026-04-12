package gui;
import board.Board;
import javax.swing.*;
import java.awt.*;
public class ChessGUI extends JFrame {
    private Board board;
    private BoardPanel boardPanel;
    private HistoryPanel historyPanel;
    private Color lightColor = new Color(240, 217, 181);
    private Color darkColor = new Color(181, 136, 99);
    private int squareSize = 80;
    public ChessGUI() {
        board = new Board();
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        historyPanel = new HistoryPanel();
        boardPanel = new BoardPanel(board, historyPanel, lightColor, darkColor, squareSize);
        add(boardPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);
        setJMenuBar(createMenuBar());
        pack();
        setLocationRelativeTo(null);
    }
    /**
     * @return the constructed JMenuBar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> resetGame());
        gameMenu.add(newGame);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem openSettings = new JMenuItem("Customize Board");
        openSettings.addActionListener(e -> openSettings());
        settingsMenu.add(openSettings);
        menuBar.add(gameMenu);
        menuBar.add(settingsMenu);
        return menuBar;
    }
    public void resetGame() {
        board = new Board();
        historyPanel.clear();
        boardPanel.setBoard(board);
        boardPanel.repaint();
    }
    private void openSettings() {
        SettingsWindow settings = new SettingsWindow(this, lightColor, darkColor, squareSize);
        settings.setVisible(true);
        if (settings.isApplied()) {
            lightColor = settings.getLightColor();
            darkColor = settings.getDarkColor();
            squareSize = settings.getSquareSize();
            boardPanel.applySettings(lightColor, darkColor, squareSize);
            pack();
        }
    }
}
