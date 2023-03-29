package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class CheckGroupsSameTypeTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;

    @org.junit.jupiter.api.Test
    void validate() {
        try {
            //CommonGoal cm = new CheckGroupsSameType(1,4, ,);

            bookshelf = new Bookshelf();


        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}