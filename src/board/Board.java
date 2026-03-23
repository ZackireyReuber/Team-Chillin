package board;

import pieces.*;
import utils.Position;
//8x8 chess board
public class Board {
   //a simple array to make the chess board
    private Piece[][] grid;
   //creates chess baord with original position
    public Board() {
        grid = new Piece[8][8];
        initialize();
    }
   //sets it all up
    public void initialize() {
        //first row for white
        grid[0][0] = new Rook("white",   new Position(0, 0));
        grid[0][1] = new Knight("white", new Position(0, 1));
        grid[0][2] = new Bishop("white", new Position(0, 2));
        grid[0][3] = new Queen("white",  new Position(0, 3));
        grid[0][4] = new King("white",   new Position(0, 4));
        grid[0][5] = new Bishop("white", new Position(0, 5));
        grid[0][6] = new Knight("white", new Position(0, 6));
        grid[0][7] = new Rook("white",   new Position(0, 7));
        // White pawns 
        for (int c = 0; c < 8; c++) {
            grid[1][c] = new Pawn("white", new Position(1, c));
        }
        // Black pawns 
        for (int c = 0; c < 8; c++) {
            grid[6][c] = new Pawn("black", new Position(6, c));
        }

        // Black back rank (row 7 = rank 8)
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
     * Returns the piece at the given position, or null if the square is empty.
     *
     * @param position the board position to query
     * @return the Piece at that position, or null
     */
    public Piece getPiece(Position position) {
        return grid[position.getRow()][position.getCol()];
    }

    /**
     * Moves a piece from one square to another.
     * Does not validate the legality of the move.
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
     * Returns the raw 8x8 grid array, used by pieces to compute possible moves.
     * @return the internal board grid
     */
    public Piece[][] getGrid() {
        return grid;
    }
    /**
     * Prints the current state of the board to the console.
     * Ranks are displayed from 8 (top) to 1 (bottom).
     * Files are labeled A through H across the top.
     * Empty squares are shown as "##".
     */
    public void display() {
        System.out.println();
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " |");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece == null) {
                    System.out.print(" ##|");
                } else {
                    System.out.print(" " + piece.getSymbol() + "|");
                }
            }
            System.out.println(" " + (row + 1));
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println();
    }
}
