package model;

import model.board.Board;
import org.junit.jupiter.api.Test;

import model.goals.PersonalGoal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
//gestisci getbox,ripristina setbox in game
String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();

    FileInputStream ip;

    {
        try {
            ip = new FileInputStream(configFilePath);
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Player player;
    private Game game;
    private ScoringToken token1 , token2;
    private TablePosition position;
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;
    private Board board;
    private ArrayList<String> usernames = new ArrayList<String>();


    /*
    * tests the game attributes to be correctly set after game constructor
    * */
    @Test
    void GameConstructorTest(){
        try {
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            assertNotNull(game.getFirstCommonGoal());
            assertNotNull(game.getSecondCommonGoal());
            assertNotSame(game.getSecondCommonGoal(), game.getFirstCommonGoal());
            assertNotNull(game.getBoard());
            assertTrue(game.getCurrentPosition().getPlayer().getUsername() == "mario" ||
                                game.getCurrentPosition().getPlayer().getUsername() == "luca"||
                                game.getCurrentPosition().getPlayer().getUsername() == "dario"||
                                game.getCurrentPosition().getPlayer().getUsername() == "matteo");
            System.out.println("Test passato!");

        } catch (Exception e) {
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }

    /*
    * tests the setEndGame method
    * */
    @Test
    void setEndGameTest(){
        try{
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);

            game.setEndGame(true);
            assertTrue(game.getEndGame());
            game.setEndGame(false);
            assertFalse(game.getEndGame());

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }

    /*
     * tests the setCurrentPosition method
     * */
    @Test
    void setCurrentPositionTest(){
        try{
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            TablePosition pos = game.getCurrentPosition();

            assertEquals(pos.getBookshelf(), game.getCurrentBookshelf());
            game.setCurrentPosition();
            assertEquals(game.getCurrentPosition().getBookshelf(), game.getCurrentBookshelf());
            assertNotEquals(pos, game.getCurrentPosition());
            assertNotNull(game.getCurrentPosition());
            assertTrue(game.getCurrentPosition().getPlayer().getUsername() == "mario" ||
                    game.getCurrentPosition().getPlayer().getUsername() == "luca"||
                    game.getCurrentPosition().getPlayer().getUsername() == "dario"||
                    game.getCurrentPosition().getPlayer().getUsername() == "matteo");

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }

    /*
     * tests the getListBookshelfTest method
     * */
    @Test
    void getListBookshelfTest(){
        try{
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);

            assertEquals(4, game.getListBookshelf().size());
            for(int i=0;i<4;i++){
                assertNotNull(game.getListBookshelf().get(i));
            }

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }

/*
* tests if the validation of the two common goals at the
* beginning of the match returns false for each player
* and the score of every player is 0.
* */
    @Test
    void validateCommonGoalTest(){
        try{
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            for(int i=0;i<usernames.size();i++){
                game.validateCommonGoal(game.getCurrentPosition());
                assertNull(game.getCurrentPosition().getPlayer().getToken(0));
                assertNull(game.getCurrentPosition().getPlayer().getToken(1));
                assertEquals(0, game.getCurrentPosition().getPlayer().getScore());
                game.setCurrentPosition();
            }

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }

    /*
     * tests if the validation of the personal goals at the
     * beginning of the match returns false for each player
     * and the score of every player is 0.
     * */
    @Test
    void validatePersonalGoalTest(){
        try{
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            for(int i=0;i<usernames.size();i++){
                game.validatePersonalGoal(game.getCurrentPosition());
                assertEquals(0, game.getCurrentPosition().getPlayer().getScore());
                game.setCurrentPosition();
            }

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }


    /*
     * tests if the validation for adjacent tiles at the
     * beginning of the match returns false for each player
     * and the score of every player is 0.
     * */
    @Test
    void validateAdjacentTest(){
        try{
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            for(int i=0;i<usernames.size();i++){
                game.validateAdjacent(game.getCurrentPosition());
                assertEquals(0, game.getCurrentPosition().getPlayer().getScore());
                game.setCurrentPosition();
            }

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }


    @Test
    void fillBoardTest(){
        try{
            usernames.clear();
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            game.fillBoard();
            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPickedCardsTest(){
        try{
            usernames.clear();
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            game.getPickedCards();
            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }


    @Test
    void setWinnerTest(){
        try{
            usernames.clear();
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            game.getCurrentPosition().getPlayer().setScore(10);
            game.changeCurrentPosition();
            game.getCurrentPosition().getPlayer().setScore(5);
            game.setWinner();
            assertEquals(game.getFirstPlayer(), game.getWinner());
            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }


    @Test
    void checkInsertTest(){
        try{
            usernames.clear();
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("matteo");
            game = new Game(usernames);
            game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(),1,4);
            game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(),1,5);
            assertTrue(game.checkInsert(1));
            game.getCurrentBookshelf().setChoosenColumn(1);
            game.getCurrentBookshelf().insert(new ItemTiles(Type.CATS,1));
            assertTrue(game.checkInsert(1));
            game.getCurrentBookshelf().insert(new ItemTiles(Type.CATS,1));
            assertTrue(game.checkInsert(1));
            game.getCurrentBookshelf().insert(new ItemTiles(Type.CATS,1));
            assertTrue(game.checkInsert(1));
            game.getCurrentBookshelf().insert(new ItemTiles(Type.CATS,1));
            assertTrue(game.checkInsert(1));
            game.getCurrentBookshelf().insert(new ItemTiles(Type.CATS,1));
            assertFalse(game.checkInsert(1));
            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }


    @Test
    void checkBoardEmptyTest(){
        try{
            usernames.clear();
            usernames.add("mario");
            usernames.add("luca");
            game = new Game(usernames);
            game.getBoard().draw(1,3);
            game.getBoard().draw(2,4);
            game.getBoard().draw(3,3);
            game.getBoard().draw(3,5);
            game.getBoard().draw(4,2);
            game.getBoard().draw(4,4);
            game.getBoard().draw(4,6);
            game.getBoard().draw(5,1);
            game.getBoard().draw(5,3);
            game.getBoard().draw(5,5);
            game.getBoard().draw(6,4);
            game.getBoard().draw(7,5);
            assertFalse(game.checkBoardEmpty());
            game.getBoard().draw(3,7);
            assertTrue(game.checkBoardEmpty());
            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }


    @Test
    void checkDrawTest(){
        try{
            usernames.clear();
            usernames.add("mario");
            usernames.add("luca");
            usernames.add("dario");
            usernames.add("cassano");
            game = new Game(usernames);
            ArrayList coords = new ArrayList<ArrayList<Integer>>();
            ArrayList c1 = new ArrayList<Integer>(2);
            ArrayList c2 = new ArrayList<Integer>(2);
            ArrayList c3 = new ArrayList<Integer>(2);
            c1.add(0);
            c1.add(3);
            c2.add(0);
            c2.add(4);
            coords.add(c1);
            coords.add(c2);
            assertTrue(game.checkDraw(coords));
            game.getBoard().draw(0,3);
            game.getBoard().draw(0,4);
            coords.clear();
            c1.clear();
            c2.clear();
            c1.add(1);
            c1.add(3);
            c2.add(1);
            c2.add(4);
            c3.add(1);
            c3.add(5);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertTrue(game.checkDraw(coords));
            game.getBoard().draw(1,3);
            game.getBoard().draw(1,4);
            game.getBoard().draw(1,5);
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(2);
            c1.add(2);
            c2.add(3);
            c2.add(1);
            c3.add(3);
            c3.add(2);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertFalse(game.checkDraw(coords));
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(7);
            c1.add(3);
            c2.add(7);
            c2.add(4);
            c3.add(7);
            c3.add(5);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertFalse(game.checkDraw(coords));
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(2);
            c1.add(2);
            c2.add(2);
            c2.add(4);
            c3.add(2);
            c3.add(5);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertFalse(game.checkDraw(coords));
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(3);
            c1.add(7);
            c2.add(4);
            c2.add(7);
            c3.add(5);
            c3.add(7);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertFalse(game.checkDraw(coords));
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(3);
            c1.add(8);
            c2.add(4);
            c2.add(8);
            coords.add(c1);
            coords.add(c2);
            assertTrue(game.checkDraw(coords));
            game.getBoard().draw(3,8);
            game.getBoard().draw(4,8);
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(3);
            c1.add(7);
            c2.add(4);
            c2.add(7);
            c3.add(5);
            c3.add(7);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertTrue(game.checkDraw(coords));
            game.getBoard().draw(3,7);
            game.getBoard().draw(4,7);
            game.getBoard().draw(5,7);
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(3);
            c1.add(6);
            c2.add(4);
            c2.add(6);
            c3.add(6);
            c3.add(6);
            coords.add(c1);
            coords.add(c2);
            coords.add(c3);
            assertFalse(game.checkDraw(coords));
            coords.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c1.add(4);
            c1.add(4);
            coords.add(c1);
            assertFalse(game.checkDraw(coords));
            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }













}