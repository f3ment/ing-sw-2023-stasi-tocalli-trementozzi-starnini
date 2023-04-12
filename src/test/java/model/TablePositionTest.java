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
        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void setFirstPosition() {
    }

    @Test
    void isFirstPosition() {
    }
}