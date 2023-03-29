package model;

import static org.junit.jupiter.api.Assertions.*;

import model.goals.PersonalGoal;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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

        token1 = new ScoringToken(8,1);
        token2 = new ScoringToken(6,2);
        personalGoal = new PersonalGoal(new HashMap<>());
        try {
            bookshelf = new Bookshelf();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        position = new TablePosition("Mario",personalGoal,bookshelf);

        player = new Player(position,position.getPlayer().getUsername());

        player.setToken(token1);
        player.setToken(token2);

        assertTrue(player.getScore() == token1.getScore() + token2.getScore() && );

        //continuo...

    }


}