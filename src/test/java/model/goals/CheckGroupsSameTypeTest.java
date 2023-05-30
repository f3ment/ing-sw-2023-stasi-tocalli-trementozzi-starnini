package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CheckGroupsSameTypeTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;
    private static int i = 1; //contatore test

    @BeforeAll
    static void initAll(){
        System.out.println("<< Inizio dei test 'CheckGroupsSameTypeTest' >>");
    }

    @BeforeEach
    void testInit(){
        System.out.println("<< Nuovo Test ["+i+"] >>");
        i++;
    }

    @Test
    void bookshelfFullSameTypeAssertTrue6of2() {
        try {
            cm = new CheckGroupsSameType(1,4, 2,6);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
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
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
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
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());

        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void bookshelfFullSameTypeAssertTrue4of4() {
        try {
            cm = new CheckGroupsSameType(1,4, 4,4);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
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
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
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
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());

        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void bookshelfEmpty6of2() {
        try {
            cm = new CheckGroupsSameType(1,4, 2,6);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            assertNull(cm.validate(bookshelf));
        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void bookshelfEmpty4of4() {
        try {
            cm = new CheckGroupsSameType(1,4, 4,4);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            assertNull(cm.validate(bookshelf));

        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void noGroupsAssertNull() {
        try {
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));

            CommonGoal cm4 = new CheckGroupsSameType(1,4,4,4);
            CommonGoal cm2 = new CheckGroupsSameType(1,4,2,6);
            assertNull(cm4.validate(bookshelf));
            assertNull(cm2.validate(bookshelf));

        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void bothGroupsAssertTrue() {
        try {
            scoringToken = new ScoringToken(8,1);

            bookshelf = new Bookshelf();

            CommonGoal cm4 = new CheckGroupsSameType(1,4,4,4);
            CommonGoal cm2 = new CheckGroupsSameType(1,4,2,6);

            assertNull(cm4.validate(bookshelf));
            assertNull(cm2.validate(bookshelf));

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));


            cm4 = new CheckGroupsSameType(1,4,4,4);
            cm2 = new CheckGroupsSameType(1,4,2,6);
            //assertNull(cm4.validate(bookshelf));
            assertEquals(scoringToken.getScore(), cm4.validate(bookshelf).getScore());
            assertEquals(scoringToken.getScore(), cm2.validate(bookshelf).getScore());

        } catch (Exception e) {
            System.out.println("Non riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckGroupsSameType(1,4,4,4);
        assertNotNull(commonGoal.toString());
        commonGoal = new CheckGroupsSameType(1,4,2,6);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckGroupsSameType(1,4,4,4);
        assertNotNull(commonGoal.getSource());
        commonGoal = new CheckGroupsSameType(1,4,2,6);
        assertNotNull(commonGoal.getSource());
    }
}