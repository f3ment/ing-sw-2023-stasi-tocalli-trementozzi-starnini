package model;

import view.Color;

public enum Type {
    BOOKS(Color.YELLOW_BRIGHT), CATS(Color.GREEN_BRIGHT), FRAMES(Color.BLUE_BRIGHT), GAMES(Color.WHITE_BRIGHT), PLANTS(Color.MAGENTA_BRIGHT), TROPHIES(Color.CYAN_BRIGHT);
    private Color color;

    Type(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
