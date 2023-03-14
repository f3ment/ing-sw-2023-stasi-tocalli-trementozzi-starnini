package model.entities;

public class ItemTiles {
    Type itemType;

    public ItemTiles(Type item_type){
        this.itemType=itemType;
    }

    public Type getType(){
        return itemType;
    }
}
