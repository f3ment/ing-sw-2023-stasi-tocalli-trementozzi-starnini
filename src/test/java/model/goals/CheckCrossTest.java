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

import static org.junit.jupiter.api.Assertions.*;



class CheckCrossTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;

    @Test
    void validate() {

        this.cm = new CheckCross(1,4);

        try {

            //testing positive: CATS cross at the down left corner

            bookshelf = new Bookshelf();


            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));



            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));


            scoringToken = new ScoringToken(8 , 1);
            assertTrue(scoringToken.getScore() == cm.validate(bookshelf).getScore());


            //testing negative case

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

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void validateNull() {

        this.cm = new CheckCross(1,4);

        try {

            //testing positive: CATS cross at the down left corner

            bookshelf = null;

/*
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));


            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

*/
            scoringToken = new ScoringToken(8, 1);
            assertNull(cm.validate(bookshelf));

        }catch(Exception e){
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

