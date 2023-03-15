package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public abstract class CommonGoal {
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public abstract ScoringToken validate(Bookshelf bookshelf);
}
