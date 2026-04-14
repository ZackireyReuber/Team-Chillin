package gui;

import board.Board;
import pieces.Piece;
import utils.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A Swing panel that renders the chessboard and handles piece movement via mouse clicks.
 * Supports click-to-move: click a piece to select it, then click a destination to move it.
 */
public class BoardPanel extends JPanel {

    /** The game board model. */
    private Board board;

    /** The history panel to log moves and support undo. */
    private HistoryPanel historyPanel;

    /** Light square color. */
    private Color lightColor;

    /** Dark square color. */
    private Color darkColor;

    /** Size of each square in pixels. */
    private int squareSize;

    /** The currently selected piece's position, or null if none selected. */
    private Position selectedPos;

    /** Unicode symbols for white pieces indexed by symbol string. */
    private static final java.util.Map<String, String> SYMBOLS = new java.util.HashMap<>();

    static {
        SYMBOLS.put("wK", "\u2654"); SYMBOLS.put("wQ", "\u2655");
        SYMBOLS.put("wR", "\u2656"); SYMBOLS.put("wB", "\u2657");
        SYMBOLS.put("wN", "\u2658"); SYMBOLS.put("wp", "\u2659");
        SYMBOLS.put("bK", "\u265A"); SYMBOLS.put("bQ", "\u265B");
        SYMBOLS.put("bR", "\u265C"); SYMBOLS.put("bB", "\u265D");
        SYMBOLS.put("bN", "\u265E"); SYMBOLS.put("bp", "\u265F");
    }

    /**
     * Constructs a BoardPanel with the given board and display settings.
     *
     * @param board        the chess board model
     * @param historyPanel the history panel for logging moves
     * @param lightColor   color for light squares
     * @param darkColor    color for dark squares
     * @param squareSize   size of each square in pixels
     */
    public BoardPanel(Board board, HistoryPanel historyPanel,
                      Color lightColor, Color darkColor, int squareSize) {
        this.board = board;
        this.historyPanel = historyPanel;
        this.lightColor = lightColor;
        this.darkColor = darkColor;
        this.squareSize = squareSize;
        setPreferredSize(new Dimension(squareSize * 8, squareSize * 8));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * Updates the board model and refreshes the display.
     *
     * @param board the new board to display
     */
    public void setBoard(Board board) {
        this.board = board;
        selectedPos = null;
        repaint();
    }

    /**
     * Applies new visual settings and repaints.
     *
     * @param lightColor new light square color
     * @param darkColor  new dark square color
     * @param squareSize new square size in pixels
     */
    public void applySettings(Color lightColor, Color darkColor, int squareSize) {
        this.lightColor = lightColor;
        this.darkColor = darkColor;
        this.squareSize = squareSize;
        setPreferredSize(new Dimension(squareSize * 8, squareSize * 8));
        repaint();
    }

    /**
     * Handles a mouse click at the given pixel coordinates.
     * First click selects a piece; second click moves it to the destination.
     *
     * @param x pixel x coordinate
     * @param y pixel y coordinate
     */
    private void handleClick(int x, int y) {
        int col = x / squareSize;
        int row = 7 - (y / squareSize);
        Position clicked = new Position(row, col);

        if (selectedPos == null) {
            // Select a piece
            Piece piece = board.getPiece(clicked);
            if (piece != null) {
                selectedPos = clicked;
                repaint();
            }
        } else {
            // Move selected piece to destination
            if (!clicked.equals(selectedPos)) {
                Piece moving = board.getPiece(selectedPos);
                Piece target = board.getPiece(clicked);

                // Check if capturing the king
                boolean kingCaptured = target != null &&
                        target.getSymbol().equals("wK") || target != null &&
                        target.getSymbol().equals("bK");

                // Log the move
                String moveText = moving.getSymbol() + ": " +
                        selectedPos.toNotation() + " → " + clicked.toNotation();
                if (target != null) moveText += " (captured " + target.getSymbol() + ")";
                historyPanel.addMove(moveText, board.copyGrid());

                board.movePiece(selectedPos, clicked);

                if (kingCaptured) {
                    repaint();
                    String winner = moving.getColor().substring(0, 1).toUpperCase()
                            + moving.getColor().substring(1);
                    JOptionPane.showMessageDialog(this,
                            winner + " wins by capturing the King!",
                            "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.getWindowAncestor(this).dispose();
                    System.exit(0);
                }
            }
            selectedPos = null;
            repaint();
        }
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

                // Draw square
                boolean isLight = (row + col) % 2 == 0;
                g2.setColor(isLight ? lightColor : darkColor);
                g2.fillRect(x, y, squareSize, squareSize);

                // Highlight selected square
                if (selectedPos != null &&
                        selectedPos.getRow() == row && selectedPos.getCol() == col) {
                    g2.setColor(new Color(100, 200, 100, 150));
                    g2.fillRect(x, y, squareSize, squareSize);
                }

                // Draw coordinate labels on edges
                g2.setColor(isLight ? darkColor : lightColor);
                g2.setFont(new Font("Arial", Font.PLAIN, 11));
                if (col == 0) g2.drawString(String.valueOf(row + 1), x + 3, y + 14);
                if (row == 0) g2.drawString(String.valueOf((char)('A' + col)), x + squareSize - 13, y + squareSize - 3);

                // Draw piece
                Piece piece = board.getPiece(new Position(row, col));
                if (piece != null) {
                    String symbol = SYMBOLS.getOrDefault(piece.getSymbol(), "?");
                    g2.setFont(new Font("Serif", Font.PLAIN, squareSize - 10));
                    FontMetrics fm = g2.getFontMetrics();
                    int sx = x + (squareSize - fm.stringWidth(symbol)) / 2;
                    int sy = y + (squareSize + fm.getAscent() - fm.getDescent()) / 2 - 4;
                    // Shadow
                    g2.setColor(Color.BLACK);
                    g2.drawString(symbol, sx + 1, sy + 1);
                    // Piece color
                    g2.setColor(piece.getColor().equals("white") ? Color.WHITE : Color.BLACK);
                    g2.drawString(symbol, sx, sy);
                }
            }
        }

        // Draw board border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(0, 0, squareSize * 8, squareSize * 8);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(squareSize * 8, squareSize * 8);
    }
}
