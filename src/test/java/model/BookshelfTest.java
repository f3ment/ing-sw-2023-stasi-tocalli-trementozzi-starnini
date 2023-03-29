package model;
import model.Bookshelf;
import model.ItemTiles;
import model.ScoringToken;
import model.Type;

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
            Bookshelf bookshelf = new Bookshelf();



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



        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void getMaxDrowable() {
        try {
            Bookshelf bookshelf = new Bookshelf();



        }catch(Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
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
                System.out.println("Ma ndo vai, dove stai scrivendo?");
            }
            try{
                assertTrue(bookshelf.getItem(0,0).getType().equals(Type.CATS));
            }catch (Exception e){
            System.out.println("Ma ndo vai?");
            }
            try{
                assertTrue(bookshelf.getItem(2,1).getType().equals(Type.PLANTS));
            }catch (Exception e){
                System.out.println("Ma ndo vai?");
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