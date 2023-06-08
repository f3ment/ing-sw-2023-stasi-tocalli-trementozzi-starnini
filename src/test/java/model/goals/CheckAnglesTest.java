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
        this.cm = new CheckAngles(1,3);
        /**
         *      0 1 2 3 4
         *  0   C       C
         *  1   C       C
         *  2   C       C
         *  3   C       C
         *  4   C       C
         *  5   C       C
         */
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
            System.out.println("CheckAnglesTest validate: OK");
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println("CheckAnglesTest validate: FAIL");
        }
    }

    /**
     * Test validate with an angle with Type different from CATS
     */
    @Test
    void validateNull() {
        this.cm = new CheckAngles(1,3);

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
            this.bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));
            this.bookshelf.insert(new ItemTiles(Type.CATS, 1));

            this.scoringToken = new ScoringToken(6, 1);
            assertNull(cm.validate(bookshelf));
            System.out.println("CheckAnglesTest validateNull: OK");
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println("CheckAnglesTest validateNull: FAIL");
        }
    }


    /**
     * Test validate with an empty bookshelf
     */
    @Test
    void validateEmptyBookshelf(){
        this.cm = new CheckAngles(1,3);
        try{
            this.bookshelf = new Bookshelf();
            assertNull(cm.validate(bookshelf));
            System.out.println("CheckAnglesTest validateEmptyBookshelf: OK");
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println("CheckAnglesTest validateEmptyBookshelf: FAIL");
        }
    }



    @Test
    void setComplete(){
        CommonGoal commonGoal = new CheckAngles(2,3);
        commonGoal.setCompleted(true);
        assertTrue(commonGoal.getCompleted());
    }

    @Test
    void setComplete2(){
        CommonGoal commonGoal = new CheckAngles(2,3);
        commonGoal.setCompleted(true);
        assertTrue(commonGoal.getCompleted());
    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckAngles(2,3);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckAngles(2,3);
        assertNotNull(commonGoal.getSource());
    }
}
