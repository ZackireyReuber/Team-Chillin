package gui;

import pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A side panel that displays the move history and provides an Undo button.
 * Each move is logged with piece symbol, from/to positions, and any captures.
 */
public class HistoryPanel extends JPanel {

    /** List of move description strings. */
    private List<String> moves;

    /** Saved board states for undo functionality (deep copies). */
    private List<Piece[][]> boardStates;

    /** The text area displaying move history. */
    private JTextArea historyArea;

    /** Callback triggered when undo is pressed. */
    private Runnable undoCallback;

    /**
     * Constructs the HistoryPanel with a scrollable move list and an Undo button.
     */
    public HistoryPanel() {
        moves = new ArrayList<>();
        boardStates = new ArrayList<>();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 640));
        setBorder(BorderFactory.createTitledBorder("Move History"));
        setBackground(new Color(40, 40, 40));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(30, 30, 30));
        historyArea.setForeground(Color.WHITE);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scroll = new JScrollPane(historyArea);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        JButton undoBtn = new JButton("⟵ Undo");
        undoBtn.setBackground(new Color(180, 60, 60));
        undoBtn.setForeground(Color.WHITE);
        undoBtn.setFont(new Font("Arial", Font.BOLD, 13));
        undoBtn.setFocusPainted(false);
        undoBtn.addActionListener(e -> undo());
        add(undoBtn, BorderLayout.SOUTH);
    }

    /**
     * Registers a callback to be called when the undo button is pressed.
     * The callback receives the previous board grid to restore.
     *
     * @param callback a Runnable triggered on undo
     */
    public void setUndoCallback(Runnable callback) {
        this.undoCallback = callback;
    }

    /**
     * Logs a move to the history panel and saves the board state before the move.
     *
     * @param moveText    human-readable description of the move
     * @param boardBefore a deep copy of the board state before this move
     */
    public void addMove(String moveText, Piece[][] boardBefore) {
        moves.add(moveText);
        boardStates.add(boardBefore);
        updateDisplay();
    }

    /**
     * Returns the saved board state from before the last move, for undo purposes.
     *
     * @return the previous board grid, or null if no moves have been made
     */
    public Piece[][] getLastBoardState() {
        if (boardStates.isEmpty()) return null;
        return boardStates.get(boardStates.size() - 1);
    }

    /**
     * Removes the last move from history and triggers the undo callback.
     */
    public void undo() {
        if (moves.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No moves to undo!", "Undo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        moves.remove(moves.size() - 1);
        boardStates.remove(boardStates.size() - 1);
        updateDisplay();
        if (undoCallback != null) undoCallback.run();
    }

    /**
     * Clears all move history and saved states.
     */
    public void clear() {
        moves.clear();
        boardStates.clear();
        updateDisplay();
    }

    /**
     * Refreshes the text area to show the current list of moves.
     */
    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < moves.size(); i++) {
            sb.append((i + 1)).append(". ").append(moves.get(i)).append("\n");
        }
        historyArea.setText(sb.toString());
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
}
