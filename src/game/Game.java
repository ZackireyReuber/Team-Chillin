package game;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import pieces.King;
import pieces.Piece;
import utils.Position;
    private Board board;
    private String currentTurn;
    private boolean gameOver;
    private String winner;
    public Game() {
        board = new Board();
        currentTurn = "white";
        gameOver = false;
        winner = null;
    }
    public Board getBoard() {
        return board;
    }
    public String getCurrentTurn() {
        return currentTurn;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public String getWinner() {
        return winner;
    }
    public void reset() {
        board = new Board();
        currentTurn = "white";
        gameOver = false;
        winner = null;
    }
    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }
    public boolean isCheck(String color) {
        return isKingInCheck(board, color);
    }
    public boolean hasLegalMoves(String color) {
        for (Piece[] row : board.getGrid()) {
            for (Piece piece : row) {
                if (piece != null && piece.getColor().equals(color)) {
                    List<Position> legal = getLegalDestinations(piece.getPosition(), color);
                    if (!legal.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public List<Position> getLegalDestinations(Position from) {
        return getLegalDestinations(from, currentTurn);
    }
    private List<Position> getLegalDestinations(Position from, String turn) {
        List<Position> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from);
        if (piece == null || !piece.getColor().equals(turn)) {
            return legalMoves;
        }
        List<Position> rawMoves = piece.possibleMoves(board.getGrid());
        for (Position to : rawMoves) {
            Board clone = new Board(board.copyGrid());
            clone.movePiece(from, to);
            if (!isKingInCheck(clone, turn)) {
                legalMoves.add(to);
            }
        }
        return legalMoves;
    }
    public boolean makeMove(Position from, Position to) {
        if (gameOver) {
            return false;
        }
        Piece piece = board.getPiece(from);
        if (piece == null || !piece.getColor().equals(currentTurn)) {
            return false;
        }
        List<Position> legal = getLegalDestinations(from);
        if (!legal.contains(to)) {
            return false;
        }
        Piece target = board.getPiece(to);
        boolean kingCaptured = target instanceof King;
        board.movePiece(from, to);
        if (kingCaptured) {
            gameOver = true;
            winner = currentTurn;
            return true;
        }
        String opponent = oppositeColor(currentTurn);
        if (isKingInCheck(board, opponent) && !hasLegalMoves(opponent)) {
            gameOver = true;
            winner = currentTurn;
            return true;
        }
        if (!isKingInCheck(board, opponent) && !hasLegalMoves(opponent)) {
            gameOver = true;
            winner = null; // stalemate
            return true;
        }

        currentTurn = opponent;
        return true;
    }
    private boolean isKingInCheck(Board boardState, String color) {
        Position kingPosition = findKingPosition(boardState, color);
        if (kingPosition == null) {
            return false;
        }
        for (Piece[] row : boardState.getGrid()) {
            for (Piece piece : row) {
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.possibleMoves(boardState.getGrid()).contains(kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private Position findKingPosition(Board boardState, String color) {
        for (Piece[] row : boardState.getGrid()) {
            for (Piece piece : row) {
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return piece.getPosition();
                }
            }
        }
        return null;
    }
    private String oppositeColor(String color) {
        return color.equals("white") ? "black" : "white";
    }
}
