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

    /**
     * Constructor of the class CommonGoal that initialize the stack of scoring tokens
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for creating the stack of the tokens
     */
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

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    public abstract ScoringToken validate(Bookshelf bookshelf);

    /**
     * Method that returns the path of the image of the goal
     * @return path of the image of the goal
     */
    public abstract String getSource();

    /**
     * Method that returns the number of the goal
     * @return the number of the goal
     */
    public int getRomanNumber() {
        return romanNumber;
    }

    /**
     * Method that sets the number of the goal
     * @param romanNumber which is the number of the goal
     */
    public void setRomanNumber(int romanNumber) {
        this.romanNumber = romanNumber;
    }

    /**
     * Method that sets the completed attribute of the goal and will be called when the goal is completed and all tokens are taken
     * @param completed which is the value to set
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * Method that returns the stack of the tokens of the goal
     * @return the stack of the tokens of the goal
     */
    public Stack<ScoringToken> getStack() {
        return stack;
    }

    /**
     * Method that returns the value of the completed attribute of the goal
     * @return the value of the completed attribute of the goal
     */
    public Boolean getCompleted() {
        return completed;
    }

}
