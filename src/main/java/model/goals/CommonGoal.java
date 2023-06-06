package model.goals;

import model.Bookshelf;
import model.ScoringToken;

import java.io.Serial;
import java.io.Serializable;
import java.util.Stack;

public abstract class CommonGoal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int romanNumber;
    private Boolean completed;
    private Stack<ScoringToken> stack;
    private String description;

    public CommonGoal(int romanNumber, int numberPlayers){
        this.romanNumber = romanNumber;
        stack = new Stack<>();
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

    public abstract String getSource();
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
