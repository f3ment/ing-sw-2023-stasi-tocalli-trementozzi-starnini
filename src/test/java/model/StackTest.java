package model;

import static org.junit.jupiter.api.Assertions.*;

import model.goals.CheckEightEquals;
import model.goals.CommonGoal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
class StackTest {
    private Stack stack;
    private CommonGoal commonGoal;


    /*
    * testing the pop() function
    *
    * after pop() is called the top of the has to contain
    * the scoring token with the previous score in the hierarchy
    *
    *
    * after pop() is called "NumberOfPlayers" times, the stack has
    * to be empty and the relative CommonGoal has to be set as "Completed"
    *
    * */
    @Test
    void differentNumberOfPops(){
        try{
            commonGoal = new CheckEightEquals(1,4);
            stack = new Stack(4,commonGoal);


            assertTrue(stack.pop().getScore() == 8);
            assertTrue(stack.pop().getScore() == 6);
            assertTrue(stack.pop().getScore() == 4);
            assertTrue(stack.pop().getScore() == 2 && commonGoal.getCompleted() == true && stack.IsEmpty());


            System.out.println("Test passato!");

        }catch (Exception e){
            System.out.println("Test fallito!");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }
}