package model;

public class ItemTiles {
    final private Type itemType;
    final private int id;
    //todo possono esistere carte diverse
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
