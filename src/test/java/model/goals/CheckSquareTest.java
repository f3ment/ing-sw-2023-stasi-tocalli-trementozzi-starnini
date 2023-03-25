package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;

import static junit.framework.Assert.assertEquals;

public class CheckSquareTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;

    @org.junit.jupiter.api.Test
    void validate() {
        CommonGoal cm = new CheckSquare(1,4);
        try{
            bookshelf = new Bookshelf();

            // Column 0
            bookshelf.setChoosenColumn(0);
            System.out.println("> Colonna 0 scelta");
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            System.out.println("> CATS (1) inserito");
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            System.out.println("> CATS (1) inserito");

            // Column 1
            bookshelf.setChoosenColumn(1);
            System.out.println("> Colonna 1 scelta");
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            System.out.println("> CATS (1) inserito");
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            System.out.println("> CATS (1) inserito");

            scoringToken = new ScoringToken(8,1);
            assertEquals(scoringToken, cm.validate(bookshelf));


        }catch(Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
        }
    }
}
