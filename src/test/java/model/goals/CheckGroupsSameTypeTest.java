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
        System.out.println("<< Start test 'CheckGroupsSameTypeTest' >>");
    }

    @BeforeEach
    void testInit(){
        System.out.println("<< New Test ["+i+"] >>");
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
            System.out.println("CheckGroupsSameTypeTest bookshelfFullSameTypeAssertTrue6of2: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest bookshelfFullSameTypeAssertTrue6of2: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }



    @Test
    void fourGroupswithFourDiffTypes(){
        try {
            cm = new CheckGroupsSameType(1,4, 4,4);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());
            System.out.println("CheckGroupsSameTypeTest sixGroupswithSixDiffTypes: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest sixGroupswithSixDiffTypes: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void threeGroupsTricky(){
        try {
            /**
             *   0 1 2 3 4
             * 0
             * 1
             * 2         G
             * 3         G
             * 4         G
             * 5   G G G G
             */

            //tricky angle, it is just one couple, or vertical or horizontal, so it counts as one group
            cm = new CheckGroupsSameType(1,4, 4,4);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            assertNull(cm.validate(bookshelf));
            //assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());
            System.out.println("CheckGroupsSameTypeTest threeGroupsTricky: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest threeGroupsTricky: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void sixGroupswithSixDiffTypes(){
        try {

            /**
             *   0 1 2 3 4
             * 0
             * 1
             * 2 T
             * 3 T     P P
             * 4 P P B B G
             * 5 C C T G G
             */
            cm = new CheckGroupsSameType(1,4, 2,6);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());
            System.out.println("CheckGroupsSameTypeTest sixGroupswithSixDiffTypes: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest sixGroupswithSixDiffTypes: FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void fiveGroupsTricky(){
        try {
            /**
             *   0 1 2 3 4
             * 0
             * 1
             * 2 T
             * 3 T
             * 4 P P B B G
             * 5 C C T G G
             */

            //tricky angle, it is just one couple, or vertical or horizontal, so it counts as one group
            cm = new CheckGroupsSameType(1,4, 2,6);
            scoringToken = new ScoringToken(8,1);
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            assertNull(cm.validate(bookshelf));
            //assertEquals(scoringToken.getScore(), cm.validate(bookshelf).getScore());
            System.out.println("CheckGroupsSameTypeTest fiveGroupsTricky: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest fiveGroupsTricky: FAIL");
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
            System.out.println("CheckGroupsSameTypeTest bookshelfFullSameTypeAssertTrue4of4: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest bookshelfFullSameTypeAssertTrue4of4: FAIL");
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
            System.out.println("CheckGroupSameTypeTest bookshelfEmpty6of2 : OK");
        } catch (Exception e) {
            System.out.println("CheckGroupSameTypeTest bookshelfEmpty6of2 : FAIL");
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
            System.out.println("CheckGroupSameTypeTest bookshelfEmpty4of4 : OK");
        } catch (Exception e) {
            System.out.println("CheckGroupSameTypeTest bookshelfEmpty4of4 : FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void validateNull(){
        try {
            cm = new CheckGroupsSameType(1,4, 4,4);
            scoringToken = new ScoringToken(8,1);

            assertNull(cm.validate(null));
            System.out.println("CheckGroupSameTypeTest validateNull : OK");
        } catch (Exception e) {
            System.out.println("CheckGroupSameTypeTest validateNull : FAIL");
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
            System.out.println("CheckGroupsSameTypeTest noGroupsAssertNull: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest noGroupsAssertNull: FAIL");
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
            System.out.println("CheckGroupsSameTypeTest bothGroupsAssertTrue: OK");
        } catch (Exception e) {
            System.out.println("CheckGroupsSameTypeTest bothGroupsAssertTrue: FAIL");
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