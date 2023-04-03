package model.goals;

import model.Bookshelf;
import model.ScoringToken;

import java.util.ArrayList;

public class CheckColumnLength extends CommonGoal{

    public CheckColumnLength(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);

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
