package org.academiadecodigo.invictus.bolinhas.game.piece;

import org.academiadecodigo.invictus.bolinhas.game.Game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PieceFactory {

    private Map<PieceType, Queue<Piece>> pieces;

    public void init() {
        pieces = new HashMap<>();

        for (PieceType type : PieceType.values()) {
            createPieces(type);
        }
    }

    private void createPieces(PieceType type) {
        Queue<Piece> queue = new LinkedList<>();

        for (int i = 0; i < Game.COLS * Game.ROWS; i++) {
            queue.offer(new Piece(type));
        }

        pieces.put(type, queue);
    }

    public Piece getRandom(int col, int row) {
        return getPiece(PieceType.getRandom(), col, row);
    }

    public Piece getExplosion(int col, int row) {
        return getPiece(PieceType.EXPLOSION, col, row);
    }

    private Piece getPiece(PieceType type, int col, int row) {
        Piece piece = pieces.get(type).poll();
        piece.moveTo(col, row);

        return piece;
    }

    public void recycle(Piece piece) {
        pieces.get(piece.getType()).offer(piece);
    }
}
