package model;

import view.Color;

public enum Type {
    BOOKS(Color.WHITE_BRIGHT), CATS(Color.GREEN_BRIGHT), FRAMES(Color.BLUE), GAMES(Color.YELLOW_BRIGHT), PLANTS(Color.MAGENTA_BRIGHT), TROPHIES(Color.CYAN_BRIGHT);
    private final Color color;

    Type(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
