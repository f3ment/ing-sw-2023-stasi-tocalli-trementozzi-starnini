package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTilesTest {

    @Test
    void validate(){
        ItemTiles item = new ItemTiles(Type.CATS, 1);
        ItemTiles item2 = new ItemTiles(Type.CATS, 2);
        ItemTiles item3 = new ItemTiles(Type.CATS, 3);

        assertTrue(item.getId() == 1);
        assertTrue(item.getType() == Type.CATS);

        assertTrue(item2.getId() == 2);
        assertTrue(item2.getType() == Type.CATS);

        assertTrue(item3.getId() == 3);
        assertTrue(item3.getType() == Type.CATS);
        System.out.println("Test passato!");
    }
}