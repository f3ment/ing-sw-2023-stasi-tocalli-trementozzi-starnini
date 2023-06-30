package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;



class CheckCrossTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;

    @Test
    void validate() {

        this.cm = new CheckCross(1,4);


        try {

            /**
             *   0 1 2 3 4
             * 0
             * 1
             * 2
             * 3 C ò C
             * 4 ò C ò
             * 5 C ò C
             */
            bookshelf = new Bookshelf();


            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));


            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));


            scoringToken = new ScoringToken(8, 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckCrossTest validate 1: OK");
        }catch (Exception e){
            System.out.println("CheckCrossTest validate 1: FAIL");
            System.out.println(e.getMessage());
        }


        try{

            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));


            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            scoringToken = new ScoringToken(8, 1);

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckCrossTest validate 2: OK");
        }catch(Exception e){
            System.out.println("CheckCrossTest validate 2: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        try {

            /**
             *   0 1 2 3 4
             * 0 *   *
             * 1 ò * ò
             * 2 * ò *
             * 3 ò ò ò
             * 4 ò ò ò
             * 5 ò ò ò
             */
            bookshelf = new Bookshelf();


            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));


            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));


            scoringToken = new ScoringToken(6, 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckCrossTest validate 3: OK");
        }catch (Exception e){
            System.out.println("CheckCrossTest validate 3: FAIL");
            System.out.println(e.getMessage());
        }

        /**
         *   0 1 2 3 4
         * 0
         * 1
         * 2     ò * ò
         * 3     * ò *
         * 4     ò * ò
         * 5     * ò *
         */
        try {
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            scoringToken = new ScoringToken(4, 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckCrossTest validate 4: OK");
        }catch (Exception e){
            System.out.println("CheckCrossTest validate 4: FAIL");
            System.out.println(e.getMessage());
        }
    }


    /**
     * Testing the case in which the bookshelf is null or empty
     */
    @Test
    void validateNull() {

        this.cm = new CheckCross(1,4);

        try {
            bookshelf = null;
            assertNull(cm.validate(bookshelf));
            System.out.println("CheckCrossTest validateNull 1: OK ");
        }catch(Exception e){
            System.out.println("CheckCrossTest validateNull 1: FAIL ");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        try{
            bookshelf = new Bookshelf();
            assertNull(cm.validate(bookshelf));
            System.out.println("CheckCrossTest validateNull 2: OK ");
        }catch (Exception e) {
            System.out.println("CheckCrossTest validateNull 2: FAIL ");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckCross(1,4);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckCross(1,4);
        assertNotNull(commonGoal.getSource());
    }
}

