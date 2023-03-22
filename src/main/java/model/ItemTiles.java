package model;

public class ItemTiles {
    private Type itemType;
    private int id;
    //todo possono esistere carte diverse
    public ItemTiles(Type item_type, int id){
        this.id = id;
        this.itemType=itemType;
    }

    public Type getType(){
        return itemType;
    }
}
