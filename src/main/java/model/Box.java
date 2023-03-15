package model;

public class Box {
    private boolean valid;
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
