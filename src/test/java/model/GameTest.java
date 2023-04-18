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




}