package model;

public class ItemTiles {
    Type itemType;
    //todo possono esistere carte diverse
    public ItemTiles(Type item_type){
        this.itemType=itemType;
    }

    public Type getType(){
        return itemType;
    }
}
