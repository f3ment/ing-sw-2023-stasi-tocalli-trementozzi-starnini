package model;

import model.goals.CommonGoal;

public class Stack {
    private ScoringToken[] scoringTokens;
    private ScoringToken top;
    private int dim;
    CommonGoal commongoal;
    public Stack(int dim, CommonGoal goal){
        this.dim=dim;
        scoringTokens= new ScoringToken[dim];
        scoringTokens=pila;
        top=scoringTokens[dim-1];
        this.commongoal=goal;
    }

    public ScoringToken pop(){
         ScoringToken actualtop = new ScoringToken(top.getScore(), top.getNumber()); //actualtop è il vecchio top
         ScoringToken[] reducedstack = new ScoringToken[scoringTokens.length];
         for(int i=0;i<scoringTokens.length-1;i++){
             reducedstack[i]=scoringTokens[i];
         }
         scoringTokens=reducedstack;
         top=scoringTokens[scoringTokens.length-1];
         return actualtop;
    }
    public ScoringToken getTop(){
        return this.top;
    }

}
