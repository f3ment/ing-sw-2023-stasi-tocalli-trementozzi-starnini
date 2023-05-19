package model;

import view.Color;

public enum Type {
    CATS(Color.GREEN_BRIGHT),BOOKS(Color.YELLOW_BRIGHT),GAMES(Color.WHITE_BRIGHT),FRAMES( Color.BLUE_BRIGHT),TROPHIES(Color.CYAN_BRIGHT),PLANTS(Color.MAGENTA_BRIGHT);
    private Color color;

    Type(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
