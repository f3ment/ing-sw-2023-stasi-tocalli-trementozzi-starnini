package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class CheckEightEqualsTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;

    private CommonGoal cm;
    @Test
    void validate() {

        this.cm = new CheckEightEquals(1,4);

        try {


            /**
             *   0 1 2 3 4
             * 0
             * 1 C
             * 2 *     *
             * 3 *     * C
             * 4 C     * C
             * 5 C   C C C
             */

            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(3);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));

            bookshelf.setChoosenColumn(4);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(2);

            bookshelf.insert(new ItemTiles(Type.CATS,1));

            scoringToken = new ScoringToken(8 , 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckEightEqualsTest validate 1: OK");
        }catch (Exception e) {
            System.out.println("CheckEightEqualsTest validate 1: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        try {
            /**
             *   0 1 2 3 4
             * 0
             * 1 C
             * 2 *     *
             * 3 *     * C
             * 4 C     * C
             * 5 C     C C
             */
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(3);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));

            bookshelf.setChoosenColumn(4);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            scoringToken = new ScoringToken(8, 1);

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckEightEqualsTest validate 2: OK");
        }catch(Exception e){
            System.out.println("CheckEightEqualsTest validate 2: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void validateNull(){
        CommonGoal commonGoal = new CheckEightEquals(1,4);
        assertNull(commonGoal.validate(null));
        System.out.println("CheckEightEqualsTest validateNull 1: OK");
        try {
            assertNull(commonGoal.validate(new Bookshelf()));
            System.out.println("CheckEightEqualsTest validateNull 2: OK");
        } catch (IOException e) {
            System.out.println("CheckEightEqualsTest validateNull 2: FAIL");
            System.out.println(e.getMessage());
        }
    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckEightEquals(1,4);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckEightEquals(1,4);
        assertNotNull(commonGoal.getSource());
    }
}