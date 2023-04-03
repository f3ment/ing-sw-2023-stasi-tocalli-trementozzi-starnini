package model.board;

import model.Bag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getBox() {
    }

    @Test
    void draw() {
    }

    @Test
    void setToken() {
    }

    @Test
    void setBox() {
        Board board = new TwoBoard();
        Bag bag = new Bag();
        assertTrue(board.setBox(bag));

        board = new FourBoard();
        assertTrue(board.setBox(bag));

        board = new FourBoard();
        assertTrue(board.setBox(bag));

        System.out.println("Test passato!");
    }



}