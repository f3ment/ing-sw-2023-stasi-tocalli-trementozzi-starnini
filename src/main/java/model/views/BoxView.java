package model.views;

import model.ItemTiles;

/**
 * This class is used to represent an immutable Box.
 */
public class BoxView {
    final private boolean valid; //casella in cui si può piazzare un ItemTile
    final private ItemTiles content;

    /**
     * Creates a new BoxView with the given parameters.
     * @param valid true if the box is valid, false otherwise
     * @param content the ItemTile contained in the box
     */
    public BoxView(boolean valid, ItemTiles content){
        this.valid = valid;
        this.content = content;
    }

    /**
     * @return the ItemTile contained in the box
     */
    public ItemTiles getItemContained() {
        return content;
    }

    /**
     * @return true if the box is valid, false otherwise
     */
    public boolean getValid() {
        return valid;
    }
}
