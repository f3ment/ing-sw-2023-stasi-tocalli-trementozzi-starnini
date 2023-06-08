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
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CheckColumnLengthTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    @org.junit.jupiter.api.Test
    void validateDecresc() {
        CommonGoal cm = new CheckColumnLength(1 , 4);
        try {
            bookshelf = new Bookshelf();
            /**
             *   0 1 2 3 4
             * 0
             * 1 *
             * 2 * *
             * 3 * * *
             * 4 * * * *
             * 5 * * * * *
             */
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(8, 1);

            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckcolumnLength valdateDecresc 1: OK");
        }catch (Exception e) {
            System.out.println("CheckcolumnLength valdateDecresc 1: FAIL");
            System.out.println(e.getMessage());
        }

        /**
         *   0 1 2 3 4
         * 0
         * 1
         * 2 * *
         * 3 * * *
         * 4 * * * *
         * 5 * * * * *
         */
        try {
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(8, 1);

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckcolumnLength validateDecresc 2: OK");
        }catch(Exception e){
            System.out.println("CheckcolumnLength validateDecresc 2: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));

        }

        try {
            bookshelf = new Bookshelf();
            /**
             *   0 1 2 3 4
             * 0 *
             * 1 * *
             * 2 * * *
             * 3 * * * *
             * 4 * * * * *
             * 5 * * * * *
             */
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(6, 1);

            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckcolumnLength valdateDecresc 3: OK");
        }catch (Exception e) {
            System.out.println("CheckcolumnLength valdateDecresc 3: FAIL");
            System.out.println(e.getMessage());
        }

        try {
            bookshelf = new Bookshelf();
            /**
             *   0 1 2 3 4
             * 0 *
             * 1 * *
             * 2 * * *
             * 3 * * *
             * 4 * * * * *
             * 5 * * * * *
             */
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));


            assertNull(cm.validate(bookshelf));
            System.out.println("CheckcolumnLength valdateDecresc 4: OK");
        }catch (Exception e) {
            System.out.println("CheckcolumnLength valdateDecresc 4: FAIL");
            System.out.println(e.getMessage());
        }

    }

    @Test
    void emptyBookshelf(){
        CommonGoal cm = new CheckColumnLength(1 , 4);
        try {
            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckcolumnLength emptyBookshelf: OK");
        } catch (IOException e) {
            System.out.println("CheckcolumnLength emptyBookshelf: FAIL");
            System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test

    void validateCresc(){
        CommonGoal cm = new CheckColumnLength(1 , 4);
        try {

            /**
             *   0 1 2 3 4
             * 0
             * 1
             * 2       * *
             * 3     * * *
             * 4   * * * *
             * 5 * * * * *
             */
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(4);
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

            scoringToken = new ScoringToken(6, 1);

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckcolumnLength validateCresc 1: OK");
        }catch (Exception e) {
            System.out.println("CheckcolumnLength validateCresc 1: FAIL");
            System.out.println(e.getMessage());
        }

        try {
            /**
             *   0 1 2 3 4
             * 0
             * 1         *
             * 2       * *
             * 3     * * *
             * 4   * * * *
             * 5 * * * * *
             */
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

            scoringToken = new ScoringToken(8, 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckcolumnLength validateCresc 2: OK");
        }catch(Exception e){
            System.out.println("CheckcolumnLength validateCresc 2: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        try {

            /**
             *   0 1 2 3 4
             * 0         *
             * 1       * *
             * 2     * * *
             * 3   * * * *
             * 4   * * * *
             * 5 * * * * *
             */
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
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
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(6, 1);

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckcolumnLength validateCresc 3: OK");
        }catch (Exception e) {
            System.out.println("CheckcolumnLength validateCresc 3: FAIL");
            System.out.println(e.getMessage());
        }

        try {
            /**
             *   0 1 2 3 4
             * 0         *
             * 1       * *
             * 2     * * *
             * 3   * * * *
             * 4 * * * * *
             * 5 * * * * *
             */
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
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
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            scoringToken = new ScoringToken(6, 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckcolumnLength validateCresc 4: OK");
        }catch(Exception e){
            System.out.println("CheckcolumnLength validateCresc 4: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckColumnLength(1,4);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckColumnLength(1,4);
        assertNotNull(commonGoal.getSource());
    }
}