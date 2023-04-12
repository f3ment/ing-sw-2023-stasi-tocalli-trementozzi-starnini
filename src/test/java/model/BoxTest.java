package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void getItemContained() {

        ItemTiles tile= new ItemTiles(Type.CATS,3);
        Box box= new Box(true,tile);
        assertTrue(box.getItemContained().equals(tile));
        Box boxi=new Box(false,null);
    }

    @Test
    void getValid() {
        ItemTiles tile= new ItemTiles(Type.CATS,3);
        Box box= new Box(true,tile);
        assertTrue(box.getValid()==true);
        Box boxi=new Box(false,null);
        assertTrue(boxi.getValid()==false);

    }

    @Test
    void setContent() {

        ItemTiles tile= new ItemTiles(Type.CATS,3);
        Box box= new Box(true,tile);
        ItemTiles tilo= new ItemTiles(Type.FRAMES,2);
        box.setContent(tilo);
        assertTrue(box.getItemContained().equals(tilo));


    }
}