package model.goals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Bookshelf;
import model.ItemTiles;
import model.Type;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    @Test
    void validate() {
        Gson gson = new Gson();
        Map<String, Map<String, Map<String, String>>> windows;
        PersonalGoal pg;
        Bookshelf bookshelf;
        try {
            windows = gson.fromJson(new FileReader("./src/test/resources/personalGoals.json"),
                    new TypeToken<Map<String, Map<String, Map<String, String>>>>() {}.getType());

            pg  = new PersonalGoal(windows.get("1"), 1);
            bookshelf = new Bookshelf();
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1)); // x = 5
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1)); // x = 4
            bookshelf.insert(new ItemTiles(Type.PLANTS, 1)); // x = 3

            bookshelf.setChoosenColumn(1);
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));
            bookshelf.insert(new ItemTiles(Type.CATS, 1));

            bookshelf.setChoosenColumn(2);
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1)); // x == 5
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1)); // x == 4
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1)); // x == 3
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1)); // x == 2

            bookshelf.setChoosenColumn(3);
            bookshelf.insert(new ItemTiles(Type.GAMES, 1)); // x == 5
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1)); // x == 4
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1)); // x == 3
            bookshelf.insert(new ItemTiles(Type.TROPHIES, 1)); // x == 2
            bookshelf.insert(new ItemTiles(Type.BOOKS, 1)); // x ==  1
            bookshelf.insert(new ItemTiles(Type.GAMES, 1)); // x == 0

            bookshelf.setChoosenColumn(4);
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));
            bookshelf.insert(new ItemTiles(Type.FRAMES, 1));

            assertTrue(pg.validate(bookshelf) == 12);
            assertTrue(pg.getDone()==6);
            assertTrue(pg.validate(new Bookshelf()) == 0);


            System.out.println("Test passato!");

        } catch (FileNotFoundException e) {
            System.out.println("File non esistente lettura pgs!");
            assertTrue(false);
        } catch (IOException e) {
            System.out.println("File non esistente lettura properties!");
            assertTrue(false);
        } catch (Exception e) {
            System.out.println("Error");
            assertTrue(false);
        }

    }

    @Test
    void getWindowsTest(){
        Gson gson = new Gson();
        Map<String, Map<String, Map<String, String>>> windows;


        try {
            windows = gson.fromJson(new FileReader("./src/test/resources/personalGoals.json"),
                    new TypeToken<Map<String, Map<String, Map<String, String>>>>() {}.getType());
                    PersonalGoal pg  = new PersonalGoal(windows.get("1"),1);
                    assertNotNull(pg.getWindows());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}