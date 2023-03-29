package model;

import model.board.Board;
import model.board.FourBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {


    @Test
    void fillEmptyTest() {
        try {

            Bag bag = new Bag();

            assertTrue(bag.getLeftItems() == 132);

            while (bag.getLeftItems() > 0) {
                bag.extract();
            }
            assertTrue(bag.getLeftItems() == 0);



        }catch (Exception e){
            System.out.println("<fillEmptyTest> Non Riuscito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void extract() {
            try {

                Bag bag = new Bag();

                assertTrue(bag.getLeftItems() == 132);




            } catch (Exception e) {
                System.out.println("<extract> Non Riuscito!");
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
}