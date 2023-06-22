package model;

import java.io.Serializable;

public class Box implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean valid; //casella in cui si può piazzare un ItemTile
    private ItemTiles content;

    public Box(boolean valid,ItemTiles content){
        this.valid=valid;
        this.content=content;
    }
    public ItemTiles getItemContained(){
        return content;
    }
    public boolean getValid(){
        return valid;
    }
    public void setContent(ItemTiles content) {
        this.content = content;
    }
}
