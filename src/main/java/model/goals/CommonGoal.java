package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public abstract class CommonGoal {
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    CommonGoal(int romanNumber, int numberPlayers){
        this.romanNumber = romanNumber;
        //todo implement stack creator
        // stack = new Stack(numberPlayers,);
        this.completed = false;
    }
    public abstract ScoringToken validate(Bookshelf bookshelf);
}
