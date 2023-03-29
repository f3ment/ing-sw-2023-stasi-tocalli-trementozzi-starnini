package model;

import model.board.Board;
import model.board.FourBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void extract() {
        Bag bag = new Bag();
        assertTrue(bag.getLeftItems() == 132);
        while(bag.getLeftItems()>0){
            bag.extract();
        }

    }
}