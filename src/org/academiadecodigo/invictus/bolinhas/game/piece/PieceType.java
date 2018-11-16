package org.academiadecodigo.invictus.bolinhas.game.piece;

public enum PieceType {
    BLUE("blue.png"),
    GREEN("green.png"),
    YELLOW("yellow.png"),
    RED("red.png"),
    PINK("pink.png"),
    ORANGE("orange.png"),
    GREY("grey.png"),
    BROWN("brown.png"),
    PURPLE("purple.png"),
    EXPLOSION("explosion.png");

    private static final String IMAGE_DIR = "assets/";

    public static PieceType getRandom() {
        return values()[(int) (Math.random() * (values().length - 1))];
    }

    private String imagePath;

    PieceType(String imagePath) {
        this.imagePath = IMAGE_DIR + imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
