package model;
import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {

    @Test
    void insert() {
        try {
            Bookshelf boo = new Bookshelf();
            boo.setChoosenColumn(0);
            boo.insert(new ItemTiles(Type.CATS,1));
            assertTrue(boo.getColumnsSize().get(0).equals(1));
            boo.insert(new ItemTiles(Type.CATS,1));
            assertTrue(boo.getColumnsSize().get(0).equals(2));
            boo.insert(new ItemTiles(Type.FRAMES,1));
            assertTrue(boo.getColumnsSize().get(0).equals(3));
            boo.insert(new ItemTiles(Type.BOOKS,1));
            assertTrue(boo.getColumnsSize().get(0).equals(4));
            boo.insert(new ItemTiles(Type.CATS,1));
            assertTrue(boo.getColumnsSize().get(0).equals(5));
            boo.insert(new ItemTiles(Type.CATS,1));
            assertTrue(boo.getColumnsSize().get(0).equals(6));



        }catch(Exception e){
                System.out.println("Test fallito!");
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
    }

    @Test
    void setChoosenColumn() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setChoosenColumn(0);
            assertTrue(bookshelf.getChoosenColumn()==0);
            bookshelf.setChoosenColumn(4);
            assertTrue(bookshelf.getChoosenColumn()==4);
            //genera errore
            try{
                bookshelf.setChoosenColumn(5);
                assertTrue(bookshelf.getChoosenColumn()==5);
            }catch(Exception e){
                    System.out.println("test corretto");
            }


        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void getMaxDrowable() {
        try {
            Bookshelf bookshelf= new Bookshelf();
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(1);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(2);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(3);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(4);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            assertTrue(bookshelf.getMaxDrowable()==0);

            Bookshelf bookshel = new Bookshelf();
            bookshel.setChoosenColumn(0);
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.FRAMES,1));
            bookshel.insert(new ItemTiles(Type.BOOKS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));

            bookshel.setChoosenColumn(1);

            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.FRAMES,1));
            bookshel.insert(new ItemTiles(Type.BOOKS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));

            bookshel.setChoosenColumn(2);

            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.FRAMES,1));
            bookshel.insert(new ItemTiles(Type.BOOKS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));

            bookshel.setChoosenColumn(3);

            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.FRAMES,1));
            bookshel.insert(new ItemTiles(Type.BOOKS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));

            bookshel.setChoosenColumn(4);

            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));
            bookshel.insert(new ItemTiles(Type.FRAMES,1));
            bookshel.insert(new ItemTiles(Type.BOOKS,1));
            bookshel.insert(new ItemTiles(Type.CATS,1));


            assertTrue(bookshel.getMaxDrowable()==1);

            Bookshelf bookshe = new Bookshelf();
            bookshe.setChoosenColumn(0);
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.FRAMES,1));
            bookshe.insert(new ItemTiles(Type.BOOKS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));

            bookshe.setChoosenColumn(1);

            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.FRAMES,1));
            bookshe.insert(new ItemTiles(Type.BOOKS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));

            bookshe.setChoosenColumn(2);

            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.FRAMES,1));
            bookshe.insert(new ItemTiles(Type.BOOKS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));

            bookshe.setChoosenColumn(3);

            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.FRAMES,1));
            bookshe.insert(new ItemTiles(Type.BOOKS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));

            bookshe.setChoosenColumn(4);

            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.CATS,1));
            bookshe.insert(new ItemTiles(Type.FRAMES,1));
            bookshe.insert(new ItemTiles(Type.BOOKS,1));



            assertTrue(bookshe.getMaxDrowable()==2);

            Bookshelf boo = new Bookshelf();
            boo.setChoosenColumn(0);
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.FRAMES,1));
            boo.insert(new ItemTiles(Type.BOOKS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));

            boo.setChoosenColumn(1);

            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.FRAMES,1));
            boo.insert(new ItemTiles(Type.BOOKS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));

            boo.setChoosenColumn(2);

            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.FRAMES,1));
            boo.insert(new ItemTiles(Type.BOOKS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));

            boo.setChoosenColumn(3);

            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.FRAMES,1));
            boo.insert(new ItemTiles(Type.BOOKS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));

            boo.setChoosenColumn(4);

            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.CATS,1));
            boo.insert(new ItemTiles(Type.FRAMES,1));


            assertTrue(boo.getMaxDrowable()==3);




        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

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


        int res=match.validateAdjacent(match.getCurrentPosition());
        Assertions.assertTrue(res==18);
        match.setCurrentPosition();

        bookshelf=match.getCurrentPosition().getBookshelf();
        bookshelf.setChoosenColumn(0);
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.PLANTS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));

        bookshelf.setChoosenColumn(1);

        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));

        bookshelf.setChoosenColumn(2);
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.BOOKS,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));

        bookshelf.setChoosenColumn(3);
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.FRAMES,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));

        bookshelf.setChoosenColumn(4);
        bookshelf.insert(new ItemTiles(Type.FRAMES,1));
        bookshelf.insert(new ItemTiles(Type.FRAMES,1));
        bookshelf.insert(new ItemTiles(Type.FRAMES,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));
        bookshelf.insert(new ItemTiles(Type.CATS,1));

        int pi=match.validateAdjacent(match.getCurrentPosition());

        Assertions.assertTrue(pi==23);



    }


    @Test
    void getItem() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(1);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));

            bookshelf.setChoosenColumn(2);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            try {
                bookshelf.getItem(7,8);
                assertTrue(bookshelf.getItem(7,8).equals(Type.CATS));
            }catch (Exception e){
                System.out.println("Test Passato!");
            }
            try{
                assertTrue(bookshelf.getItem(0,0).getType().equals(Type.CATS));
            }catch (Exception e){
            System.out.println("Test fallito!");
            }
            try{
                assertTrue(bookshelf.getItem(2,1).getType().equals(Type.PLANTS));
            }catch (Exception e){
                System.out.println("Test fallito!");
            }



        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void getHeight() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            assertTrue(bookshelf.getLength()==5);


        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void getLength() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            assertTrue(bookshelf.getLength()==5);


        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void getColumnsSize() {
        try {
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setChoosenColumn(0);
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.FRAMES,1));
            bookshelf.insert(new ItemTiles(Type.BOOKS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.CATS,1));

            bookshelf.setChoosenColumn(1);

            bookshelf.insert(new ItemTiles(Type.CATS,1));
            bookshelf.insert(new ItemTiles(Type.TROPHIES,1));
            bookshelf.insert(new ItemTiles(Type.GAMES,1));
            bookshelf.insert(new ItemTiles(Type.PLANTS,1));

            bookshelf.setChoosenColumn(2);

            bookshelf.insert(new ItemTiles(Type.CATS,1));

            ArrayList<Integer> actualColumnLength = bookshelf.getColumnsSize();
            assertTrue(actualColumnLength.get(0)==6);
            assertTrue(actualColumnLength.get(1)==4);
            assertTrue(actualColumnLength.get(2)==1);
            assertTrue(actualColumnLength.get(3)==0);
            assertTrue(actualColumnLength.get(4)==0);
            assertTrue(bookshelf.isFull()==false);
            assertTrue(actualColumnLength.size()==5);





        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


}