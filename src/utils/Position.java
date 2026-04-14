package utils;

//represents rows and columns.
public class Position {

    // The row index (0-7), where 0 corresponds to rank 1.
    private int row;

    // The column index (0-7), where 0 corresponds to file A.
    private int col;

    /**
     * Constructs a Position from zero-based row and columns.
     * @param row 0-7 (0 = rank 1)
     * @param col 0-7 (0 = file A)
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Parses a chess notation string like "E2" into a Position.
     * @param notation a two-character string (file letter + rank number), e.g. "E2"
     * @return the corresponding Position, or null if the format is invalid
     */
    public static Position fromNotation(String notation) {
        if (notation == null || notation.length() != 2) return null;
        notation = notation.toUpperCase();
        char file = notation.charAt(0);
        char rank = notation.charAt(1);
        if (file < 'A' || file > 'H') return null;
        if (rank < '1' || rank > '8') return null;
        int col = file - 'A';
        int row = rank - '1';
        return new Position(row, col);
    }

    /**
     * Converts this position back to standard chess notation (e.g., "E2").
     * @return the chess notation string
     */
    public String toNotation() {
        char file = (char) ('A' + col);
        char rank = (char) ('1' + row);
        return "" + file + rank;
    }

    // @return the zero-based row index
    public int getRow() { return row; }

    // @return the zero-based column index 
    public int getCol() { return col; }

    /**
     * Checks whether this position falls within the 8x8 board boundaries.
     * @return true if the position is on the board
     */
    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return toNotation();
    }
}
