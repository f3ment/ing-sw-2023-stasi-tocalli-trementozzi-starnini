package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public abstract class CommonGoal{
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CommonGoal(int romanNumber, int numberPlayers){
        this.romanNumber = romanNumber;
        stack = new Stack(numberPlayers,this);
        this.completed = false;
    }
    public abstract ScoringToken validate(Bookshelf bookshelf) throws Exception;
    public int getRomanNumber() {
        return romanNumber;
    }
}
