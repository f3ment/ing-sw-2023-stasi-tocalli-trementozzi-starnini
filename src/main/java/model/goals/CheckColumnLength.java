package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

import java.util.ArrayList;

public class CheckColumnLength extends CommonGoal{
    /**
     * Constructor of the class CheckColumnLength that extends CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for creating the stack of the tokens
     */
    public CheckColumnLength(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    /**
     * Method that returns the description of the goal
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, ach next column must be made of exactly one more tile. Tile can be of any type.";
    }

    /**
     * Method that returns the source of the image of the goal
     * @return path of the image of the goal
     */
    public String getSource(){
        return "12.jpg";
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks if the columns of the bookshelf have increasing or decreasing height with a difference of 1.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        boolean flagCresc = true;
        boolean flagDecresc = true;
        ArrayList<Integer> arr = bookshelf.getColumnsSize();
        for(int i=0;i< arr.size()-1 && (flagCresc || flagDecresc) ;i++){
            if(arr.get(i)+1 != arr.get(i+1)){
                flagCresc = false;
            }
            if(arr.get(i)-1 != arr.get(i+1)){
                flagDecresc = false;
            }
        }
        if(flagDecresc || flagCresc ){
            return getStack().pop();
        }else{
            return null;
        }
    }
}
