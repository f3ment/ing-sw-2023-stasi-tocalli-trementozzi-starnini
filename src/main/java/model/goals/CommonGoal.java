package model.goals;

import model.Bookshelf;
import model.ScoringToken;

import java.util.Stack;

public abstract class CommonGoal{
    private int romanNumber;
    private Boolean completed;
    private Stack<ScoringToken> stack;

    public CommonGoal(int romanNumber, int numberPlayers){
        this.romanNumber = romanNumber;
        stack = new Stack<ScoringToken>();
        switch (numberPlayers){
            case 2:
                this.stack.add(new ScoringToken(4, romanNumber));
                this.stack.add(new ScoringToken(8, romanNumber));
                break;
            case 3:
                this.stack.add(new ScoringToken(4, romanNumber));
                this.stack.add(new ScoringToken(6, romanNumber));
                this.stack.add(new ScoringToken(8, romanNumber));
                break;
            case 4:
                this.stack.add(new ScoringToken(2, romanNumber));
                this.stack.add(new ScoringToken(4, romanNumber));
                this.stack.add(new ScoringToken(6, romanNumber));
                this.stack.add(new ScoringToken(8, romanNumber));
                break;
        }

        this.completed = false;
    }
    public abstract ScoringToken validate(Bookshelf bookshelf);
    public int getRomanNumber() {
        return romanNumber;
    }

    public void setRomanNumber(int romanNumber) {
        this.romanNumber = romanNumber;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    public Stack<ScoringToken> getStack() {
        return stack;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
