package model;

import java.io.Serializable;

public class ItemTiles implements Serializable {
    final private Type itemType;
    private static final long serialVersionUID = 1L;

    final private int id;
    public ItemTiles(Type item_type, int id){
        this.id = id;
        this.itemType=item_type;
    }

    public Type getType(){
        return itemType;
    }

    public int getId() {
        return id;
    }
}
