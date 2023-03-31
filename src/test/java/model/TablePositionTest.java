package model;

import model.goals.PersonalGoal;
import org.junit.jupiter.api.Test;

import javax.swing.text.TabExpander;

import static org.junit.jupiter.api.Assertions.*;

class TablePositionTest {
    TablePosition tablePosition;
    @Test
    void getPlayer() {
        try{
            tablePosition = new TablePosition(new String("Michi"), new PersonalGoal(null), new Bookshelf());
            assertTrue(tablePosition.getPlayer().getUsername().equals("Michi"));
            assertTrue(tablePosition.getPlayer().getScore() == 0);
            assertEquals(tablePosition.getPlayer().getCurrentPosition(), tablePosition);

            System.out.println("Test passato!");
        }catch(Exception e){
            System.out.println("Test non passato!");
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getCurrentPGoal() {
    }

    @Test
    void getBookshelf() {
    }

    @Test
    void setFirstPosition() {
    }

    @Test
    void isFirstPosition() {
    }
}