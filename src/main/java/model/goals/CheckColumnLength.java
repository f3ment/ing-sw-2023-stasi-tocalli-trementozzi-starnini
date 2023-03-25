package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

import java.util.ArrayList;

public class CheckColumnLength extends CommonGoal{
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckColumnLength(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
        this.romanNumber = romanNumber;
        this.stack = new Stack(numberPlayers,this);
        this.completed = false;
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
            return this.stack.pop();
        }else{
            return null;
        }
    }
}
