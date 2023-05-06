package model.board;

import model.Bag;
import model.Box;
import model.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getbox() {
        Board board = new Board(4);
        int checkException=0;
        try {
            Box box = board.getBox(board.getMaxHeight()+2, board.getMaxLength()+2);
        } catch (IndexOutOfBoundsException e) {
            checkException = 1;
        }

        assertTrue(checkException == 1);
        System.out.println("Test passato!");

    }



    @Test
    void setToken() {
        Token token = new Token(6);
        Board board = new Board(4);
        board.setToken(token);
        assertTrue(board.getToken().equals(token));
        System.out.println("Test passato!");
    }

    @Test
    void setBox() {
        Board board = new Board(2);
        Bag bag = new Bag();
        assertTrue(board.setBox(bag));

        board = new Board(4);
        assertTrue(board.setBox(bag));

        board = new Board(4);
        assertTrue(board.setBox(bag));

        System.out.println("Test passato!");
    }

    @Test
    void getMaxHeight(){
        Board board = new Board(4);
        int height = board.getMaxHeight();
        assertTrue(height == 9);
        System.out.println("Test passato!");
    }

    @Test
    void getMaxLength(){
        Board board = new Board(4);
        int length = board.getMaxLength();
        assertTrue(length == 9);
        System.out.println("Test passato!");
    }

}