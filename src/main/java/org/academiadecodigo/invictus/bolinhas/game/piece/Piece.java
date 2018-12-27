package org.academiadecodigo.invictus.bolinhas.game.piece;

import org.academiadecodigo.invictus.bolinhas.game.Game;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Piece {

    public static final int SIZE = 100;

    private Picture representation;
    private Rectangle border;
    private PieceType type;
    private boolean hidden;
    private int col;
    private int row;

    public Piece(PieceType type) {
        representation = new Picture(Game.PADDING, Game.PADDING, type.getImagePath());
        border = new Rectangle(Game.PADDING, Game.PADDING, SIZE, SIZE);
        this.type = type;
        hidden = true;
        col = 0;
        row = 0;
    }

    public void show() {
        representation.draw();
        hidden = false;
    }

    public void hide() {
        representation.delete();
        hidden = true;
    }

    public void select() {
        border.draw();
    }

    public void unselect() {
        border.delete();
    }

    public void moveTo(int col, int row) {
        int xPixels = (col - this.col) * SIZE;
        int yPixels = (row - this.row) * SIZE;

        representation.translate(xPixels, yPixels);
        border.translate(xPixels, yPixels);

        this.col = col;
        this.row = row;
    }

    public PieceType getType() {
        return type;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isHidden() {
        return hidden;
    }
}
