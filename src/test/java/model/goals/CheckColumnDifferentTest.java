package model.goals;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckColumnDifferentTest {
    private Bookshelf bookshelf;
    private ScoringToken scoringToken;
    private CommonGoal cm;
    @Test
    //colonne da 6 elementi diversi, 2 ripetizioni
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

            //libreria vuota
            assertNull(cm.validate(new Bookshelf()));

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

            assertNull(cm.validate(new Bookshelf()));

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
            bookshelf.insert(new ItemTiles(Type.GAMES,1));

            assertNull(cm.validate(new Bookshelf()));

            System.out.println("Test passato!");
        }catch(Exception e){
            System.out.println("Test non passato!");
            System.out.println(e.getMessage());
        }

    }

    @Test
        //colonne da max 3 elementi diversi, 3 ripetizioni
    void validateThreeDiff(){
        cm = new CheckColumnDifferent(1, 4,3 , false);
        try{

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

            //libreria vuota
            assertNull(cm.validate(new Bookshelf()));

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

            System.out.println("Test passato!");
        }catch (Exception e){
            System.out.println("Test non passato!");
            System.out.println(e.getMessage());
        }
    }
}