package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class CheckSquareTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;

    @org.junit.jupiter.api.Test
    void validate() {
        try{
            CommonGoal cm = new CheckSquare(1,4);

            bookshelf = new Bookshelf();

            // Column 0
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            // Column 1
            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            // Column 2
            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            // Column 3
            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            // Column 4
            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(8,1);
            assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());

            bookshelf = new Bookshelf();

            // Column 0
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            // Column 1
            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            // Column 2
            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            // Column 3
            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            // Column 4
            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            scoringToken = new ScoringToken(6,1);
            assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());

            bookshelf = new Bookshelf();
            assertNull(cm.validate(bookshelf));

        }catch(Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));

        }
    }
}
