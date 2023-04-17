package model;

import model.goals.PersonalGoal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
class ScoringTokenTest {
    private ScoringToken scoringtoken;
    private TablePosition tablePosition;
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;

    @Test
    void TokenMainFunctions(){
        scoringtoken = new ScoringToken(6,2);
        try {
            personalGoal = new PersonalGoal(new HashMap<>());
            bookshelf = new Bookshelf();
            tablePosition = new TablePosition("Mario",personalGoal,bookshelf);


            assertTrue(scoringtoken.getScore() == 6 && scoringtoken.getNumber()==2);



            System.out.println("Test Passato!");

        } catch (Exception e) {
            System.out.println("Test fallito!");
            throw new RuntimeException(e);
        }



    }
}