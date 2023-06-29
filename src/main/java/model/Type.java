package model;

import view.Color;

/**
 * Enumerates the different types of itemsTiles.
 */
public enum Type {
    BOOKS(Color.WHITE_BRIGHT), CATS(Color.GREEN_BRIGHT), FRAMES(Color.BLUE), GAMES(Color.YELLOW_BRIGHT), PLANTS(Color.MAGENTA_BRIGHT), TROPHIES(Color.CYAN_BRIGHT);
    private final Color color;

    /**
     * Sets the color of the item.
     * @param color the color of the item
     */
    Type(Color color) {
        this.color = color;
    }

    /**
     * @return the color of the item
     */
    public Color getColor() {
        return color;
    }
}
