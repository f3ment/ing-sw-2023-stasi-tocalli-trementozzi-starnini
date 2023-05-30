package model.goals;

import org.junit.jupiter.api.Test;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheckRowDifferentTest {

    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;
    private int repetitions;

    @Test
    void validate() {
        repetitions=2;
        this.cm = new CheckRowDifferent(1,4,repetitions,false);  //test 5 diff elements, 2 lines

        try{
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
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));


            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));

            this.scoringToken = new ScoringToken(8, 1);

            assertTrue(this.scoringToken.getScore() == cm.validate(bookshelf).getScore() &&
                    this.scoringToken.getNumber() == cm.validate(bookshelf).getNumber());

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
            this.cm = new CheckRowDifferent(1,4,repetitions,true);  //test 3

            this.scoringToken = new ScoringToken(8, 1);
            assertTrue(this.scoringToken.getScore() == cm.validate(bookshelf).getScore() &&
                    this.scoringToken.getNumber() == cm.validate(bookshelf).getNumber());

            assertNull(cm.validate(new Bookshelf())); //empty bookshelf
            System.out.println("Test passato!");
        }catch (Exception e){
            System.out.println("Test non passato!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));


        }
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