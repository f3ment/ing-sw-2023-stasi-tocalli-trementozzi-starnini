package model.goals;
import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


class CheckColumnLengthTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    @org.junit.jupiter.api.Test
    void validate() {
        CommonGoal cm = new CheckColumnLength(1 , 4);
        try{
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(8, 1);

            assertEquals(scoringToken, cm.validate(bookshelf));

            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(5);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(6, 1);

            assertEquals(scoringToken, cm.validate(bookshelf));

            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(5);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(4, 1);

            assertEquals(scoringToken, cm.validate(bookshelf));

            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(2, 1);

            assertEquals(scoringToken, cm.validate(bookshelf));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}