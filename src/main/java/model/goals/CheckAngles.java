package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckAngles extends CommonGoal{
    private int romanNumber;
    private Boolean completed;
    private Stack stack;
    public  CheckAngles(int romanNumber, int numberPlayers){
        super(romanNumber,numberPlayers);
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        if(bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(0, bookshelf.getLength()-1).getType())  &&
           bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(bookshelf.getHeight()-1, bookshelf.getLength()-1).getType())  &&
           bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(bookshelf.getHeight()-1, 0).getType())){
            return stack.pop();
        }else{
            return null;
        }
    }
}
