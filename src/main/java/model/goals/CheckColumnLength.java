package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

import java.util.ArrayList;

public class CheckColumnLength extends CommonGoal{

    public CheckColumnLength(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers/*, "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, ach next column must be made of exactly one more tile. Tile can be of any type."*/);

    }

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
