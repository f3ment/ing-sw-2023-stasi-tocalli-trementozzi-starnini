package model;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {

    @Test
    void getBoardTest() throws IOException {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        BoxView[][] board = gameView.getBoard();
        assertFalse(board[0][0].getValid());
        assertTrue(board[4][4].getValid());
        assertFalse(board[2][2].getValid());
        assertEquals(board[4][3].getItemContained(),game.getBoard().getBox(4,3).getItemContained());
        assertEquals(board[7][4].getItemContained(),game.getBoard().getBox(7,4).getItemContained());
        assertEquals(board[5][5].getItemContained(),game.getBoard().getBox(5,5).getItemContained());
    }

//todo
    @Test
    void getListBookshelfTest() throws IOException {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        BoxView[][] board = gameView.getBoard();
        assertFalse(board[0][0].getValid());
        assertTrue(board[4][4].getValid());
        assertFalse(board[2][2].getValid());
        assertEquals(board[4][3].getItemContained(),game.getBoard().getBox(4,3).getItemContained());
        assertEquals(board[7][4].getItemContained(),game.getBoard().getBox(7,4).getItemContained());
        assertEquals(board[5][5].getItemContained(),game.getBoard().getBox(5,5).getItemContained());
    }
}