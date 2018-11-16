package org.academiadecodigo.invictus.bolinhas.game;

import org.academiadecodigo.invictus.bolinhas.game.piece.Piece;
import org.academiadecodigo.invictus.bolinhas.game.piece.PieceFactory;
import org.academiadecodigo.invictus.bolinhas.game.piece.PieceType;
import org.academiadecodigo.invictus.bolinhas.user_interface.ClickHandler;

public class Game implements ClickHandler {

    public static final int PADDING = 10;
    public static final int ROWS = 6;
    public static final int COLS = 10;

    // number of consecutive balls needed for explosion
    private static final int CONSECUTIVE = 3;

    private PieceFactory factory;
    private ExplosionManager manager;
    private Piece[][] pieces;
    private Piece selected;

    public void init() {
        factory = new PieceFactory();
        factory.init();

        manager = new ExplosionManager(factory, this);
        pieces = new Piece[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                pieces[row][col] = factory.getRandom(col, row);
                pieces[row][col].show();
            }
        }

        checkExplosions();
    }

    @Override
    public void click(double x, double y) {
        int col = xToCol(x);
        int row = yToRow(y);

        if (manager.isExploding() || isOutOfBounds(col, row)) {
            return;
        }

        Piece clickedPiece = pieces[row][col];

        if (selected == null) {
            clickedPiece.select();
            selected = clickedPiece;
            return;
        }

        swap(clickedPiece);
        selected.unselect();
        selected = null;
    }

    private int xToCol(double x) {
        return (int) ((x - PADDING) / Piece.SIZE);
    }

    private int yToRow(double y) {
        return (int) ((y - PADDING) / Piece.SIZE);
    }

    private boolean isOutOfBounds(int col, int row) {
        return col < 0 || row < 0 || col >= COLS || row >= ROWS;
    }

    private void swap(Piece piece) {
        if (!onePlaceApart(selected, piece)) {
            return;
        }

        changePositions(selected, piece);

        if (!checkExplosions()) {
            changePositions(selected, piece);
        }
    }

    private boolean onePlaceApart(Piece a, Piece b) {
        int colDiff = Math.abs(a.getCol() - b.getCol());
        int rowDiff = Math.abs(a.getRow() - b.getRow());

        return (colDiff == 0 && rowDiff == 1) || (colDiff == 1 && rowDiff == 0);
    }

    private void changePositions(Piece a, Piece b) {
        // swap pieces' position in array
        pieces[a.getRow()][a.getCol()] = b;
        pieces[b.getRow()][b.getCol()] = a;

        // swap pieces' visual representation
        int pieceCol = b.getCol();
        int pieceRow = b.getRow();
        b.moveTo(a.getCol(), a.getRow());
        a.moveTo(pieceCol, pieceRow);
    }

    private boolean checkExplosions() {
        boolean explosion = false;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                PieceType type = pieces[row][col].getType();

                for (Direction direction : Direction.values()) {
                    explosion = check(direction, type, col, row, 0) || explosion;
                }
            }
        }

        if (explosion) {
            manager.explode();
        }

        return explosion;
    }

    private boolean check(Direction direction, PieceType type, int col, int row, int times) {
        if (isOutOfBounds(col, row) || type != pieces[row][col].getType()) {
            return false;
        }

        times++;

        if (check(direction, type, col + direction.colIncrement, row + direction.rowIncrement, times) ||
                times >= CONSECUTIVE) {
            explode(col, row);
            return true;
        }

        return false;
    }

    private void explode(int col, int row) {
        Piece piece = pieces[row][col];

        if (piece.isHidden()) {
            return;
        }

        pieces[row][col].hide();
        manager.addExplosion(col, row);
    }

    public void dropColumns() {
        for (int col = 0; col < COLS; col++) {
            dropCol(col);
        }

        checkExplosions();
    }

    private void dropCol(int col) {
        boolean swap = false;

        for (int row = 0; row < ROWS; row++) {
            Piece piece = pieces[row][col];

            if (!piece.isHidden()) {
                continue;
            }

            if (row == 0) {
                factory.recycle(piece);
                pieces[row][col] = factory.getRandom(col, row);
                pieces[row][col].show();

            } else {
                changePositions(pieces[row][col], pieces[row - 1][col]);
                swap = true;
            }
        }

        if (swap) {
            dropCol(col);
        }
    }

    private enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private int colIncrement;
        private int rowIncrement;

        Direction(int colIncrement, int rowIncrement) {
            this.colIncrement = colIncrement;
            this.rowIncrement = rowIncrement;
        }
    }
}