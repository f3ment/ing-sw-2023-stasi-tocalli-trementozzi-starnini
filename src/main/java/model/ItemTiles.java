package model;

import java.io.Serializable;

/**
 * ItemTiles class
 * This class is responsible for managing the item tiles.
 * It contains the type of the item tile and its id.
 * The type is an enum that can be: cats, plants, books, trophies, games or frames.
 * The id is a number that identifies the item tile and is used to get the correct image of the item tile.
 */
public class ItemTiles implements Serializable {
    final private Type itemType;
    private static final long serialVersionUID = 1L;

    final private int id;

    /**
     * This enum is used to identify the type of the item tile.
     * The type can be: cats, plants, books, trophies, games or frames.
     * The id is used to get the correct image of the item tile.
     */
    public ItemTiles(Type item_type, int id){
        this.id = id;
        this.itemType=item_type;
    }

    /**
     * This method returns the type of the item tile.
     * @return The type of the item tile
     */
    public Type getType(){
        return itemType;
    }

    /**
     * This method returns the id of the item tile.
     * @return The id of the item tile
     */
    public int getId() {
        return id;
    }
}
