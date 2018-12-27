package org.academiadecodigo.invictus.bolinhas;

import org.academiadecodigo.invictus.bolinhas.game.Game;
import org.academiadecodigo.invictus.bolinhas.user_interface.MouseListener;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        game.init();
        MouseListener listener = new MouseListener();

        listener.setHandler(game);
        listener.init();
    }
}
