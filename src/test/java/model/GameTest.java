package model;

import org.junit.jupiter.api.Test;

import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.board.Board;
import model.goals.CommonGoal;
import model.goals.PersonalGoal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
//gestisci getbox,ripristina setbox in game
    @Test
    void validateAdjacent() throws Exception {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("Marcello");
        usernames.add("Fabio");
        Game match = null;
        match = new Game(usernames);

        Bookshelf bookshelf=match.getCurrentPosition().getBookshelf();
        bookshelf.setChoosenColumn(0);
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.FRAMES,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));

        bookshelf.setChoosenColumn(1);

        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.GAMES,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));

        bookshelf.setChoosenColumn(2);
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.FRAMES,1));
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));

        bookshelf.setChoosenColumn(3);
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.GAMES,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));

        bookshelf.setChoosenColumn(4);
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));

        System.out.println(match.validateAdjacent(match.getCurrentPosition()));
        //assertTrue(res==36);




    }


}