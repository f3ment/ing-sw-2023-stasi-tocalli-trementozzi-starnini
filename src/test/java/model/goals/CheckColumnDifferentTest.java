package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CheckColumnDifferentTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;
    @Test
    void validateSixDiff() {
        cm = new CheckColumnDifferent(1, 4,2 , true);
        try{
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            scoringToken = new ScoringToken(8, 1);

            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckColumnDiff validateSixDiff 1: OK");
        }catch(Exception e){
            System.out.println("CheckColumnDiff validateSixDiff 1: FAIL");
            System.out.println(e.getMessage());
        }

        try{
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckColumnDiff validateSixDiff 2: OK");
        }catch (Exception e){
            System.out.println("CheckColumnDiff validateSixDiff 2: FAIL");
            System.out.println(e.getMessage());
        }

        try{
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckColumnDiff validateSixDiff 3: OK");
        }catch (Exception e){
            System.out.println("CheckColumnDiff validateSixDiff 3: FAIL");
            System.out.println(e.getMessage());
        }


    }

    /**
     * Test which check if the method validate returns null when the bookshelf is empty
     */
    @Test
    void validateEmptyBookshelf(){
        cm = new CheckColumnDifferent(1, 4,2 , true);
        try {
            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckColumnDiff validateEmptyBookshelf 1: OK");
        } catch (IOException e) {
            System.out.println("CheckColumnDiff validateEmptyBookshelf 1: FAIL");
            System.out.println(e.getMessage());
        }

        cm = new CheckColumnDifferent(1, 4,3 , false);
        try {
            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckColumnDiff validateEmptyBookshelf 2: OK");
        } catch (IOException e) {
            System.out.println("CheckColumnDiff validateEmptyBookshelf 2: FAIL");
            System.out.println(e.getMessage());
        }


    }

    @Test
    void validateThreeDiff(){
        cm = new CheckColumnDifferent(1, 4,3 , false);
        try{
            //one column with 3 different types and two columns with items of the same type
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            scoringToken = new ScoringToken(8, 1);

            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());
            System.out.println("CheckColumnDiff validateThreeDiff 1: OK");
        }catch (Exception e){
            System.out.println("CheckColumnDiff validateThreeDiff 1: FAIL");
            System.out.println(e.getMessage());
        }

        try {
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckColumnDiff validateThreeDiff 2: OK");
        }catch (Exception e){
            System.out.println("CheckColumnDiff validateThreeDiff 2: FAIL");
            System.out.println(e.getMessage());
        }

        try {
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));

            assertNull(cm.validate(new Bookshelf()));
            System.out.println("CheckColumnDiff validateThreeDiff 3: OK");
        }catch (Exception e){
            System.out.println("CheckColumnDiff validateThreeDiff 3: FAIL");
            System.out.println(e.getMessage());
        }
    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckColumnDifferent(1,4,3,false);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckColumnDifferent(1,4, 3,false);
        assertNotNull(commonGoal.getSource());
    }
}