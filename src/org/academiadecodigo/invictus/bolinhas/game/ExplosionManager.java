package org.academiadecodigo.invictus.bolinhas.game;

import org.academiadecodigo.invictus.bolinhas.game.piece.Piece;
import org.academiadecodigo.invictus.bolinhas.game.piece.PieceFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExplosionManager {

    private static final int DELAY = 1000;

    private PieceFactory factory;
    private Game game;
    private List<Piece> explosions;
    private boolean exploding;

    public ExplosionManager(PieceFactory factory, Game game) {
        this.factory = factory;
        this.game = game;
        explosions = new LinkedList<>();
    }

    public void addExplosion(int col, int row) {
        explosions.add(factory.getExplosion(col, row));
    }

    public void explode() {
        exploding = true;
        showExplosions();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                removeExplosions();
                game.dropColumns();
            }
        }, DELAY);
    }

    private void showExplosions() {
        for (Piece explosion : explosions) {
            explosion.show();
        }
    }

    private void removeExplosions() {
        for (Piece explosion : explosions) {
            explosion.hide();
            factory.recycle(explosion);
        }

        explosions.clear();
        exploding = false;
    }

    public boolean isExploding() {
        return exploding;
    }
}