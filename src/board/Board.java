package board;

import pieces.*;
import utils.Position;
public class Board {
    private Piece[][] grid;
    public Board() {
        grid = new Piece[8][8];
        initialize();
    }

    /**
     * @param grid the grid to restore from
     */
    public Board(Piece[][] grid) {
        this.grid = grid;
    }
    public void initialize() {
        grid[0][0] = new Rook("white",   new Position(0, 0));
        grid[0][1] = new Knight("white", new Position(0, 1));
        grid[0][2] = new Bishop("white", new Position(0, 2));
        grid[0][3] = new Queen("white",  new Position(0, 3));
        grid[0][4] = new King("white",   new Position(0, 4));
        grid[0][5] = new Bishop("white", new Position(0, 5));
        grid[0][6] = new Knight("white", new Position(0, 6));
        grid[0][7] = new Rook("white",   new Position(0, 7));
        for (int c = 0; c < 8; c++) grid[1][c] = new Pawn("white", new Position(1, c));
        for (int c = 0; c < 8; c++) grid[6][c] = new Pawn("black", new Position(6, c));
        grid[7][0] = new Rook("black",   new Position(7, 0));
        grid[7][1] = new Knight("black", new Position(7, 1));
        grid[7][2] = new Bishop("black", new Position(7, 2));
        grid[7][3] = new Queen("black",  new Position(7, 3));
        grid[7][4] = new King("black",   new Position(7, 4));
        grid[7][5] = new Bishop("black", new Position(7, 5));
        grid[7][6] = new Knight("black", new Position(7, 6));
        grid[7][7] = new Rook("black",   new Position(7, 7));
    }

    /**
     * @param position the board position to query
     * @return the Piece at that position, or null
     */
    public Piece getPiece(Position position) {
        return grid[position.getRow()][position.getCol()];
    }
    /**
     * @param from the source position
     * @param to   the destination position
     */
    public void movePiece(Position from, Position to) {
        Piece piece = grid[from.getRow()][from.getCol()];
        if (piece != null) {
            piece.setPosition(to);
            grid[to.getRow()][to.getCol()] = piece;
            grid[from.getRow()][from.getCol()] = null;
        }
    }
    /**
     * @return the internal board grid
     */
    public Piece[][] getGrid() {
        return grid;
    }
    /**
     * @param grid the grid to restore
     */
    public void setGrid(Piece[][] grid) {
        this.grid = grid;
    }
    /**
     * @return a deep copy of the grid
     */
    public Piece[][] copyGrid() {
        Piece[][] copy = new Piece[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null) {
                    Position pos = new Position(r, c);
                    String color = p.getColor();
                    switch (p.getSymbol().substring(1)) {
                        case "p": copy[r][c] = new Pawn(color, pos); break;
                        case "R": copy[r][c] = new Rook(color, pos); break;
                        case "N": copy[r][c] = new Knight(color, pos); break;
                        case "B": copy[r][c] = new Bishop(color, pos); break;
                        case "Q": copy[r][c] = new Queen(color, pos); break;
                        case "K": copy[r][c] = new King(color, pos); break;
                    }
                }
            }
        }
        return copy;
    }
    public void display() {
        System.out.println("\n    A   B   C   D   E   F   G   H");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " |");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                System.out.print(piece == null ? " ##|" : " " + piece.getSymbol() + "|");
            }
            System.out.println(" " + (row + 1));
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }
        System.out.println("    A   B   C   D   E   F   G   H\n");
    }
}
