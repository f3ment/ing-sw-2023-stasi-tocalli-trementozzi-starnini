package model;

import model.board.Board;
import model.goals.PersonalGoal;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
class PlayerTest {

    /*
     * Apertura file di configurazione
     * */
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
    private ScoringToken token1 , token2;
    private TablePosition position;
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;

    /*
    *
    * Testing the assignment of a scoring token
    * to the player and the relative score increment
    *
    * */
    @Test
    void ScoringTokenAssignment(){
        try{
            token1 = new ScoringToken(8,1);
            token2 = new ScoringToken(6,2);
            personalGoal = new PersonalGoal(new HashMap<>());
            bookshelf = new Bookshelf();
            position = new TablePosition("Mario",personalGoal,bookshelf);

            player = position.getPlayer();

            player.setToken(token2);
            player.setToken(token1);


            assertEquals(player.getScore(), token2.getScore() + token1.getScore());
            System.out.println("Test passato!");



        }catch (Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


    /*
    *
    * Testing the player insertion into the bookshelf
    *
    * */
    @Test
    void insertInBookshelfTest(){
        try {
            bookshelf = new Bookshelf();
            position = new TablePosition("Luca",personalGoal,bookshelf);
            player = position.getPlayer();
            Board board = new Board(4);
            board.setBox(new Bag());

            //partially fill the shelf
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            //draw 3 cards from the board
            for(int i=0;i<3;i++){
                player.drawFromBoard(board,2+i,2+i);
            }
            //insert the cards into the shelf in the first column
            for(int i=0;i<3;i++){
                player.insertInBookshelf(0,0);
            }

            //check if max of drawable cards for the next turn is 2 since there are
            //2 free spaces in the last column, every other column has less
            //than 2 free spaces
            assertEquals(2, bookshelf.getMaxDrowable());

            //check if the first column is completely filled with this insertion
            assertEquals(6, bookshelf.getColumnsSize().get(0));


            for(int i=0;i<3;i++){
                player.drawFromBoard(board,1+i,2+i);
            }


            for(int i=0;i<3;i++){
                try {
                    player.insertInBookshelf(1,i);
                } catch (Exception ignored){
                }
            }


            /*
            * the player cannot insert all three cards of his hand in the
            *second column since it has only two free spaces
            * so one card is still in his hand
            */
            assertFalse(player.getPickedCards().isEmpty());

            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

}