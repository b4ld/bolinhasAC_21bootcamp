package org.academiadecodigo.invictus.bolinhas.user_interface;


import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;


public class MouseListener implements MouseHandler {

    private ClickHandler handler;

    public void init() {
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        handler.click(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    public void setHandler(ClickHandler handler) {
        this.handler = handler;
    }
}
