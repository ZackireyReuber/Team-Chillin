package gui;
import board.Board;
import game.Game;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import pieces.Piece;
import utils.Position;
public class BoardPanel extends JPanel {
    private Game game;
    private Board board;
    private HistoryPanel historyPanel;
    private Color lightColor;
    private Color darkColor;
    private int squareSize;
    private Position selectedPos;
    private Set<Position> legalMoves;
    private static final java.util.Map<String, String> SYMBOLS = new java.util.HashMap<>();
    static {
        SYMBOLS.put("wK", "\u2654"); SYMBOLS.put("wQ", "\u2655");
        SYMBOLS.put("wR", "\u2656"); SYMBOLS.put("wB", "\u2657");
        SYMBOLS.put("wN", "\u2658"); SYMBOLS.put("wp", "\u2659");
        SYMBOLS.put("bK", "\u265A"); SYMBOLS.put("bQ", "\u265B");
        SYMBOLS.put("bR", "\u265C"); SYMBOLS.put("bB", "\u265D");
        SYMBOLS.put("bN", "\u265E"); SYMBOLS.put("bp", "\u265F");
    }
    public BoardPanel(Game game, HistoryPanel historyPanel,
                      Color lightColor, Color darkColor, int squareSize) {
        this.game = game;
        this.board = game.getBoard();
        this.historyPanel = historyPanel;
        this.lightColor = lightColor;
        this.darkColor = darkColor;
        this.squareSize = squareSize;
        this.legalMoves = new HashSet<>();
        setPreferredSize(new Dimension(squareSize * 8, squareSize * 8));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }
    public void setBoard(Board board) {
        this.board = board;
        this.selectedPos = null;
        this.legalMoves.clear();
        repaint();
    }
    public void applySettings(Color lightColor, Color darkColor, int squareSize) {
        this.lightColor = lightColor;
        this.darkColor = darkColor;
        this.squareSize = squareSize;
        setPreferredSize(new Dimension(squareSize * 8, squareSize * 8));
        repaint();
    }
    private void handleClick(int x, int y) {
        int col = x / squareSize;
        int row = 7 - (y / squareSize);
        Position clicked = new Position(row, col);
        if (!clicked.isValid() || game.isGameOver()) {
            return;
        }
        if (selectedPos == null) {
            Piece piece = board.getPiece(clicked);
            if (piece == null) {
                return;
            }
            if (!piece.getColor().equals(game.getCurrentTurn())) {
                JOptionPane.showMessageDialog(this,
                        "It's " + game.getCurrentTurn() + "'s turn.",
                        "Invalid Piece", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Position> moves = game.getLegalDestinations(clicked);
            if (moves.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "This piece has no legal moves.",
                        "No Legal Moves", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            selectedPos = clicked;
            legalMoves.clear();
            legalMoves.addAll(moves);
            repaint();
            return;
        }
        if (clicked.equals(selectedPos)) {
            selectedPos = null;
            legalMoves.clear();
            repaint();
            return;
        }
        if (!legalMoves.contains(clicked)) {
            Piece piece = board.getPiece(clicked);
            if (piece != null && piece.getColor().equals(game.getCurrentTurn())) {
                selectedPos = clicked;
                legalMoves.clear();
                legalMoves.addAll(game.getLegalDestinations(clicked));
                repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "That move is not legal.",
                        "Illegal Move", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }
        Piece moving = board.getPiece(selectedPos);
        Piece target = board.getPiece(clicked);
        String moveText = moving.getSymbol() + ": " + selectedPos.toNotation() + " → " + clicked.toNotation();
        if (target != null) {
            moveText += " (captured " + target.getSymbol() + ")";
        }
        historyPanel.addMove(moveText, board.copyGrid());
        game.makeMove(selectedPos, clicked);
        selectedPos = null;
        legalMoves.clear();
        board = game.getBoard();
        repaint();
        SwingUtilities.getWindowAncestor(this).repaint();
        if (game.isGameOver()) {
            if (game.getWinner() != null) {
                JOptionPane.showMessageDialog(this,
                        game.getWinner().substring(0, 1).toUpperCase() + game.getWinner().substring(1) + " wins!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Stalemate: no legal moves remain.",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (game.isCheck(game.getCurrentTurn())) {
            JOptionPane.showMessageDialog(this,
                    game.getCurrentTurn().substring(0, 1).toUpperCase() + game.getCurrentTurn().substring(1) + " is in check!",
                    "Check", JOptionPane.INFORMATION_MESSAGE);
        }
        ((ChessGUI) SwingUtilities.getWindowAncestor(this)).updateStatus();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int x = col * squareSize;
                int y = (7 - row) * squareSize;
                boolean isLight = (row + col) % 2 == 0;
                g2.setColor(isLight ? lightColor : darkColor);
                g2.fillRect(x, y, squareSize, squareSize);
                if (selectedPos != null && selectedPos.getRow() == row && selectedPos.getCol() == col) {
                    g2.setColor(new Color(100, 200, 100, 150));
                    g2.fillRect(x, y, squareSize, squareSize);
                }
                if (legalMoves.contains(new Position(row, col))) {
                    g2.setColor(new Color(50, 150, 255, 120));
                    g2.fillRect(x, y, squareSize, squareSize);
                }
                g2.setColor(isLight ? darkColor : lightColor);
                g2.setFont(new Font("Arial", Font.PLAIN, 11));
                if (col == 0) g2.drawString(String.valueOf(row + 1), x + 3, y + 14);
                if (row == 0) g2.drawString(String.valueOf((char)('A' + col)), x + squareSize - 13, y + squareSize - 3);
                Piece piece = board.getPiece(new Position(row, col));
                if (piece != null) {
                    String symbol = SYMBOLS.getOrDefault(piece.getSymbol(), "?");
                    g2.setFont(new Font("Serif", Font.PLAIN, squareSize - 10));
                    FontMetrics fm = g2.getFontMetrics();
                    int sx = x + (squareSize - fm.stringWidth(symbol)) / 2;
                    int sy = y + (squareSize + fm.getAscent() - fm.getDescent()) / 2 - 4;
                    g2.setColor(Color.BLACK);
                    g2.drawString(symbol, sx + 1, sy + 1);
                    g2.setColor(piece.getColor().equals("white") ? Color.WHITE : Color.BLACK);
                    g2.drawString(symbol, sx, sy);
                }
            }
        }
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(0, 0, squareSize * 8, squareSize * 8);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(squareSize * 8, squareSize * 8);
    }
}
