package model;

import model.goals.CommonGoal;

import java.util.ArrayList;

public class Stack {
    private ArrayList<ScoringToken> scoringTokens;
    private int dim;
    CommonGoal commongoal;
    public Stack(int dim, CommonGoal goal){
        this.dim=dim;
        scoringTokens = new ArrayList<ScoringToken>();
        //todo scalabilità
        switch (dim){
            case 2:
                scoringTokens.add(new ScoringToken(4, goal.getRomanNumber()));
                scoringTokens.add(new ScoringToken(8, goal.getRomanNumber()));
            case 3:
                scoringTokens.add(new ScoringToken(4, goal.getRomanNumber()));
                scoringTokens.add(new ScoringToken(6, goal.getRomanNumber()));
                scoringTokens.add(new ScoringToken(8, goal.getRomanNumber()));

            case 4:
                scoringTokens.add(new ScoringToken(2, goal.getRomanNumber()));
                scoringTokens.add(new ScoringToken(4, goal.getRomanNumber()));
                scoringTokens.add(new ScoringToken(6, goal.getRomanNumber()));
                scoringTokens.add(new ScoringToken(8, goal.getRomanNumber()));

        }
        this.commongoal=goal;
    }

    public ScoringToken pop(){
        if(scoringTokens.size() == 1){
            commongoal.setCompleted(true);
        }else{
            if(scoringTokens.size() == 0){
                return null;
            }
        }
        return this.scoringTokens.remove(scoringTokens.size()-1);
    }

    public boolean IsEmpty(){
        return scoringTokens.isEmpty();
    }

}
