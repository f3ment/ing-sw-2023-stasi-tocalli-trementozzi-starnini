package model.board;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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

    @Test
    void drawMethodTest(){
        Board board = new Board(4);
        for(int i=0; i<board.getMaxHeight(); i++){
            for(int j=0; j< board.getMaxLength(); j++){
                assertEquals(board.getBox(i,j).getItemContained(), board.draw(i,j));
            }
        }
        board.draw(4,5);
    }

    @Test
    void outOfBoundGetBoxTest(){
        Board board = new Board(4);
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            board.getBox(board.getMaxHeight(), board.getMaxLength());
        });

        String expectedMessage = "out of bounds for length";
        String actualMessage = exception.getMessage();

        //System.out.println("------------------Messaggi------------------\nExpected: " + expectedMessage + "\nActual: " + actualMessage + "\n");
        assertTrue(actualMessage.contains(expectedMessage));
    }

}