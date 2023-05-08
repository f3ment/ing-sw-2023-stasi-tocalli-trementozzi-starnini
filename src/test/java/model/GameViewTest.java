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


    @Test
    void getListBookshelfTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        for(int i=0;i<4;i++){
            game.getListBookshelf().get(i).setChoosenColumn(i);
            try {
                game.getListBookshelf().get(i).insert(new ItemTiles(Type.CATS,1));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ArrayListView list = gameView.getListBookshelf();
        for(int i=0;i<4;i++){
            ItemTiles[][] shelf = (ItemTiles[][])list.get(i);
            assertSame(shelf[5][i].getType(), game.getListBookshelf().get(i).getItem(5, i).getType());
        }

    }


    @Test
    void getParticularBookshelfTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        game.getListBookshelf().get(3).setChoosenColumn(4);
        try {
            game.getListBookshelf().get(3).insert(new ItemTiles(Type.CATS,1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ArrayListView list = gameView.getListBookshelf();
        ItemTiles[][] shelf = gameView.getParticularBookshelf(3);
        assertSame(game.getListBookshelf().get(3).getItem(5,4).getType(), shelf[5][4].getType());

    }

    @Test
    void getCurrentBookshelfTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        game.getCurrentBookshelf().setChoosenColumn(4);
        try {
            game.getCurrentBookshelf().insert(new ItemTiles(Type.CATS,1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ArrayListView list = gameView.getListBookshelf();
        ItemTiles[][] shelf = gameView.getCurrentBookshelf();
        assertSame(game.getCurrentBookshelf().getItem(5,4).getType(), shelf[5][4].getType());

    }


    @Test
    void getPickedCardsTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(),4,4);
        game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(),4,5);
        game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(),4,6);
        for(int i=0;i<3;i++){
            assertEquals(gameView.getHand(i),game.getPickedCards().get(i));
        }
    }

    @Test
    void getFirstCommonGoalTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        ArrayListView goal = gameView.getFirstCommonGoal();
        for(int i=0;i<goal.size();i++){
            assertEquals(gameView.getScoringToken1(i).getScore(),game.getFirstCommonGoal().getStack().get(i).getScore());
            assertEquals(gameView.getScoringToken1(i).getNumber(),game.getFirstCommonGoal().getStack().get(i).getNumber());
        }
    }

    @Test
    void getSecondCommonGoalTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        ArrayListView goal = gameView.getSecondCommonGoal();
        for(int i=0;i<goal.size();i++){
            assertEquals(gameView.getScoringToken2(i).getScore(),game.getSecondCommonGoal().getStack().get(i).getScore());
            assertEquals(gameView.getScoringToken2(i).getNumber(),game.getSecondCommonGoal().getStack().get(i).getNumber());
        }
    }


    @Test
    void getHeightBookshelfTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getHeightBookshelf(),game.getCurrentBookshelf().getHeight());
    }

    @Test
    void getLengthBookshelfTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getLenghtBookshelf(),game.getCurrentBookshelf().getLength());
    }

    @Test
    void getHeightBoardTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getHeightBoard(),game.getBoard().getMaxHeight());
    }



    @Test
    void getLengthBoardTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getLenghtBoard(),game.getBoard().getMaxLength());
    }



    @Test
    void getCurrentPlayerTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getCurrentPlayer().getUsername(),game.getCurrentPosition().getPlayer().getUsername());
    }


    @Test
    void getEndGameTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getEndGame(),game.getEndGame());
    }

    @Test
    void getFirstPLayerTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getFirstPlayer(),game.getFirstPlayer());
    }

    @Test
    void getWinnerTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        game.getCurrentPosition().getPlayer().setScore(100);
        game.setWinner();
        GameView gameView = new GameView(game);
        assertEquals(gameView.getWinner(),game.getWinner());
    }

    @Test
    void getMaxDrawableTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getMaxDrawable(),game.getCurrentBookshelf().getMaxDrowable());
    }

    @Test
    void getScoreTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getScore(),game.getCurrentPosition().getPlayer().getScore());
    }

    @Test
    void getNumPLayerTest() throws Exception {
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("marco");
        nomi.add("mario");
        nomi.add("dario");
        nomi.add("matteo");
        Game game = new Game(nomi);
        GameView gameView = new GameView(game);
        assertEquals(gameView.getNumPlayer(),game.getListBookshelf().size());
    }









}