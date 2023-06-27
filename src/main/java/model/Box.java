package model;

import java.io.Serializable;


/**
 * Box class
 * This class is responsible for managing the boxes.
 * It contains the content of the box and if it is valid.
 * It is used by Board class to create the board as a matrix of boxes.
 */
public class Box implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean valid; //casella in cui si può piazzare un ItemTile
    private ItemTiles content;


    /**
     * Constructor for the Box class that initializes the content of the box and if it is valid.
     * @param valid A cell can be valid or not. If it is valid, it means that it is possible to place an ItemTile on it.
     * @param content The content of the box (ItemTile)
     */
    public Box(boolean valid,ItemTiles content){
        this.valid=valid;
        this.content=content;
    }

    /**
     * This method returns the content of the box.
     * @return The content of the box
     */
    public ItemTiles getItemContained(){
        return content;
    }

    /**
     * This method returns if the box is valid.
     * @return If the box is valid
     */
    public boolean getValid(){
        return valid;
    }

    /**
     * This method sets the content of the box.
     * It is used by Board class to set the content of the box when the board is created or needs to be refilled.
     * @param content The content of the box
     */
    public void setContent(ItemTiles content) {
        this.content = content;
    }
}
