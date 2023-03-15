package model;

public class Box {
    private boolean valid;
    private ItemTiles content;
    private Box up;
    private Box down;
    private Box left;
    private Box right;

    public Box(boolean valid,ItemTiles content){
        this.valid=valid;
        this.content=content;
    }

    public ItemTiles getItemContained(){
        return content;
    }
    public Box getAdjacentUp(){
        return up;
    }
    public Box getAdjacentDown(){
        return down;
    }
    public Box getAdjacentRight(){
        return right;
    }
    public Box getAdjacentLeft(){
        return left;
    }
    public boolean getValid(){
        return valid;
    }

    public void setContent(ItemTiles content) {
        this.content = content;
    }
    public boolean hasAdjacent(){
        if(getAdjacentUp().getItemContained()!=null){
            return true;
        } else if (getAdjacentDown().getItemContained()!=null) {
            return true;
        } else if (getAdjacentLeft().getItemContained()!=null) {
            return true;
        }else if (getAdjacentRight().getItemContained()!=null) {
            return true;
        }else{
            return false;
        }

    }
}
