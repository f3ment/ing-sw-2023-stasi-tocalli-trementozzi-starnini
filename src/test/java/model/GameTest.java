package model;

import model.board.FourBoard;
import org.junit.jupiter.api.Test;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.board.Board;
import model.goals.CommonGoal;
import model.goals.PersonalGoal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
    private FourBoard board;
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
            assertTrue(game.getFirstCommonGoal()!= null);
            assertTrue(game.getSecondCommonGoal()!= null);
            assertTrue(game.getSecondCommonGoal()!= game.getFirstCommonGoal());
            assertTrue(game.getBoard()!= null);
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

            assertTrue(pos.getBookshelf().equals(game.getCurrentBookshelf()));
            game.setCurrentPosition();
            assertTrue(game.getCurrentPosition().getBookshelf().equals(game.getCurrentBookshelf()));
            assertTrue(!pos.equals(game.getCurrentPosition()));
            assertTrue(game.getCurrentPosition()!= null);
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

            assertTrue(game.getListBookshelf().size() == 4);
            for(int i=0;i<4;i++){
                assertTrue(game.getListBookshelf().get(i) != null);
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
                assertTrue(game.getCurrentPosition().getPlayer().getToken(0)==null);
                assertTrue(game.getCurrentPosition().getPlayer().getToken(1)==null);
                assertTrue(game.getCurrentPosition().getPlayer().getScore()==0);
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
                assertTrue(game.getCurrentPosition().getPlayer().getScore()==0);
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
                assertTrue(game.getCurrentPosition().getPlayer().getScore()==0);
                game.setCurrentPosition();
            }

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }
    }




}