package model.goals;

import model.Bookshelf;
import model.ScoringToken;

public class CheckMultipleColumn extends CommonGoal{
    private int repetitons;

    public CheckMultipleColumn (int repetitons, int romanNumber, int playerNumber){
        super(romanNumber, playerNumber);
        this.repetitons = repetitons;
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        //todo algoritmo
    }
}
