package gui;

import pieces.Piece;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class HistoryPanel extends JPanel {
    private List<String> moves;
    private List<Piece[][]> boardStates;
    private JTextArea historyArea;
    private Runnable undoCallback;
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
     * @param callback a Runnable triggered on undo
     */
    public void setUndoCallback(Runnable callback) {
        this.undoCallback = callback;
    }

    /**
     * @param moveText    human-readable description of the move
     * @param boardBefore a deep copy of the board state before this move
     */
    public void addMove(String moveText, Piece[][] boardBefore) {
        moves.add(moveText);
        boardStates.add(boardBefore);
        updateDisplay();
    }

    /**
     * @return the previous board grid, or null if no moves have been made
     */
    public Piece[][] getLastBoardState() {
        if (boardStates.isEmpty()) return null;
        return boardStates.get(boardStates.size() - 1);
    }
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
    public void clear() {
        moves.clear();
        boardStates.clear();
        updateDisplay();
    }
    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < moves.size(); i++) {
            sb.append((i + 1)).append(". ").append(moves.get(i)).append("\n");
        }
        historyArea.setText(sb.toString());
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
}
