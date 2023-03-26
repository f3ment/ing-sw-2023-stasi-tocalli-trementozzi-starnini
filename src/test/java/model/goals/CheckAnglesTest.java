package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheckAnglesTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;
    @Test
    void validate() {
        this.cm = new CheckAngles(1,4);

        try{
            this.bookshelf = new Bookshelf();
            this.bookshelf.setChoosenColumn(0);
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));

            this.bookshelf.setChoosenColumn(4);
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));

            this.scoringToken = new ScoringToken(8, 1);
            assertTrue(this.scoringToken.getScore() == cm.validate(bookshelf).getScore() &&
                    this.scoringToken.getNumber() == cm.validate(bookshelf).getNumber());

            this.bookshelf = new Bookshelf();
            assertNull(cm.validate(bookshelf));

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));


        }
    }
}