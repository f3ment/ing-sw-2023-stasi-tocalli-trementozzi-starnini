package model;

import model.TablePosition;
import model.goals.PersonalGoal;
import org.junit.jupiter.api.Test;

import javax.swing.text.TabExpander;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TablePositionTest {
    TablePosition tablePosition;
    @Test
    void getPlayer() {
        try{
            TablePosition table = new TablePosition(new String("Michi"), new PersonalGoal(null), new Bookshelf());
            assertTrue(table.getPlayer().getUsername().equals("Michi"));
            assertTrue(table.getPlayer().getScore() == 0);

            System.out.println("Test passato!");
        }catch(Exception e){
            System.out.println("Test non passato!");
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getCurrentPGoal() throws IOException {

        TablePosition table = new TablePosition(new String("Michi"), new PersonalGoal(null), new Bookshelf());
        assertTrue(table.getCurrentPGoal().getWindows()==null);
        assertTrue(table.getCurrentPGoal().getDone()==0);
    }

    @Test
    void getBookshelf() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(1);

            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1));

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.GAMES, 1));
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            TablePosition table = new TablePosition(new String("Michi"), new PersonalGoal(null), bookshelf);

            Bookshelf book2 = new Bookshelf();
            book2.setChoosenColumn(0);
            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.FRAMES, 1));
            book2.insert(new ItemTiles(Type.BOOKS, 1));
            book2.insert(new ItemTiles(Type.PLANTS, 1));

            book2.setChoosenColumn(1);

            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.GAMES, 1));
            book2.insert(new ItemTiles(Type.BOOKS, 1));
            book2.insert(new ItemTiles(Type.PLANTS, 1));
            book2.insert(new ItemTiles(Type.PLANTS, 1));

            book2.setChoosenColumn(2);
            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.CATS, 1));
            book2.insert(new ItemTiles(Type.TROPHIES, 1));
            book2.insert(new ItemTiles(Type.FRAMES, 1));
            book2.insert(new ItemTiles(Type.PLANTS, 1));
            book2.insert(new ItemTiles(Type.PLANTS, 1));

            book2.setChoosenColumn(3);
            book2.insert(new ItemTiles(Type.CATS, 1));
            book2.insert(new ItemTiles(Type.CATS, 1));
            book2.insert(new ItemTiles(Type.GAMES, 1));
            book2.insert(new ItemTiles(Type.BOOKS, 1));
            book2.insert(new ItemTiles(Type.CATS, 1));

            book2.setChoosenColumn(4);
            book2.insert(new ItemTiles(Type.CATS, 1));
            book2.insert(new ItemTiles(Type.CATS, 1));


            assertTrue(table.getBookshelf().getItem(1,1).getType().equals(book2.getItem(1,1).getType()));
            assertTrue(table.getBookshelf().getItem(3,1).getType().equals(book2.getItem(3,1).getType()));
            assertTrue(table.getBookshelf().getItem(5,2).getType().equals(book2.getItem(5,2).getType()));

        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void setFirstPosition() throws IOException { //and isfirstposition
        TablePosition table = new TablePosition(new String("Michi"), new PersonalGoal(null), new Bookshelf());
        table.setFirstPosition(false);
        assertTrue(table.isFirstPosition()==false);
        table.setFirstPosition(true);
        assertTrue(table.isFirstPosition()==true);
    }


}