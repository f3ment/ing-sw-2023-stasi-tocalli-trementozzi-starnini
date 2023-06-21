package model.goals;

import org.junit.jupiter.api.Test;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheckRowDifferentTest {

    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;
    private int repetitions;

    @Test
    void validateFourRowsMaxThreeDiff() {

        try{
            this.cm = new CheckRowDifferent(1,4,4,true);  //4 rows with max 3 different types for each row

            this.bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            bookshelf.setChoosenColumn(1);

            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            repetitions=4;

            this.scoringToken = new ScoringToken(8, 1);
            assertTrue(this.scoringToken.getScore() == cm.validate(bookshelf).getScore() &&
                    this.scoringToken.getNumber() == cm.validate(bookshelf).getNumber());

            assertNull(cm.validate(new Bookshelf())); //empty bookshelf
            System.out.println("CheckRowDifferentTest validateFourRowsMaxThreeDiff : OK");
        }catch (Exception e){
            System.out.println("CheckRowDifferentTest validateFourRowsMaxThreeDiff : FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        try{
            bookshelf = new Bookshelf();
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));

            bookshelf.setChoosenColumn(1);

            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckRowDifferentTest validateFourRowsMaxThreeDiff 2 : OK");
        }catch (Exception e){
            System.out.println("CheckRowDifferentTest validateFourRowsMaxThreeDiff 2 : FAIL");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void validateTwoAlldifferent(){
        this.cm = new CheckRowDifferent(1,4,2 ,false);  //test 5 diff elements, 2 lines
        try{
            this.bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));


            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            this.scoringToken = new ScoringToken(8, 1);

            assertTrue(this.scoringToken.getScore() == cm.validate(bookshelf).getScore() &&
                    this.scoringToken.getNumber() == cm.validate(bookshelf).getNumber());
            System.out.println("CheckRowDifferentTest validateTwoAlldifferent : OK");
        } catch (Exception e) {
            System.out.println("CheckRowDifferentTest validateTwoAlldifferent : FAIL");
            System.out.println(e.getMessage());
        }

        try{
            bookshelf = new Bookshelf();

            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));

            assertNull(cm.validate(bookshelf));
            System.out.println("CheckRowDifferentTest validateTwoAlldifferent 2: OK");
        }catch (Exception e){
            System.out.println("CheckRowDifferentTest validateTwoAlldifferent 2: FAIL");
            System.out.println(e.getMessage());
        }
    }

    @Test
    void validateNull(){
        this.cm = new CheckRowDifferent(1,4,2,false);  //test 5 diff elements, 2 linesas
        try {
            assertNull(cm.validate(new Bookshelf())); //empty bookshelf
            System.out.println("CheckRowDifferentTest validateNull 1: OK");
        } catch (IOException e) {
            System.out.println("CheckRowDifferentTest validateNull 1: FAIL");
            System.out.println(e.getMessage());
        }
        assertNull(cm.validate(null)); //null bookshelf
        System.out.println("CheckRowDifferentTest validateNull 2: OK");

        this.cm = new CheckRowDifferent(1,4,4,true);  //test 5 diff elements, 2 linesas
        try {
            assertNull(cm.validate(new Bookshelf())); //empty bookshelf
            System.out.println("CheckRowDifferentTest validateNull 3: OK");
        } catch (IOException e) {
            System.out.println("CheckRowDifferentTest validateNull 3: FAIL");
            System.out.println(e.getMessage());
        }
        assertNull(cm.validate(null)); //null bookshelf
        System.out.println("CheckRowDifferentTest validateNull 4: OK");
    }

    @Test
    void toStringTest(){
        CommonGoal commonGoal = new CheckRowDifferent(1,4,4,true);
        assertNotNull(commonGoal.toString());
    }

    @Test
    void getSourceTest(){
        CommonGoal commonGoal = new CheckRowDifferent(1,4,4,true);
        assertNotNull(commonGoal.getSource());
    }
}