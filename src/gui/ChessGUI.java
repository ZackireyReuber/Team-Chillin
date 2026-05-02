package gui;
import game.Game;
import java.awt.*;
import javax.swing.*;
import pieces.Piece;
public class ChessGUI extends JFrame {
    private Game game;
    private BoardPanel boardPanel;
    private HistoryPanel historyPanel;
    private JLabel statusLabel;
    private Color lightColor = new Color(240, 217, 181);
    private Color darkColor = new Color(181, 136, 99);
    private int squareSize = 80;
    public ChessGUI() {
        game = new Game();
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        historyPanel = new HistoryPanel();
        boardPanel = new BoardPanel(game, historyPanel, lightColor, darkColor, squareSize);
        historyPanel.setUndoCallback(this::restorePreviousState);
        statusLabel = new JLabel("White to move");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(240, 240, 240));
        add(boardPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.EAST);
        add(statusLabel, BorderLayout.SOUTH);
        setJMenuBar(createMenuBar());
        pack();
        setLocationRelativeTo(null);
        updateStatus();
    }
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
        game.reset();
        historyPanel.clear();
        boardPanel.setBoard(game.getBoard());
        boardPanel.repaint();
        updateStatus();
    }
    private void restorePreviousState() {
        Piece[][] lastState = historyPanel.getLastBoardState();
        if (lastState == null) {
            game.reset();
        } else {
            game.reset();
            game.getBoard().setGrid(lastState);
            String nextTurn = historyPanel.getMoveCount() % 2 == 0 ? "white" : "black";
            game.setCurrentTurn(nextTurn);
        }
        boardPanel.setBoard(game.getBoard());
        boardPanel.repaint();
        updateStatus();
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
    public void updateStatus() {
        if (game.isGameOver()) {
            if (game.getWinner() != null) {
                statusLabel.setText(game.getWinner().substring(0, 1).toUpperCase() + game.getWinner().substring(1) + " wins");
            } else {
                statusLabel.setText("Game over: Stalemate");
            }
        } else {
            String turn = game.getCurrentTurn();
            boolean inCheck = game.isCheck(turn);
            statusLabel.setText((turn.equals("white") ? "White" : "Black") + " to move" + (inCheck ? " — Check!" : ""));
        }
    }
}
