package model;

public class BoxView {
    final private boolean valid; //casella in cui si può piazzare un ItemTile
    final private ItemTiles content;

    public BoxView(boolean valid, ItemTiles content){
        this.valid = valid;
        this.content = content;
    }

    public ItemTiles getItemContained() {
        return content;
    }

    public boolean getValid() {
        return valid;
    }
}
