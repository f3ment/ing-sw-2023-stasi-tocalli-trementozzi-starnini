package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckRowDifferent extends CommonGoal{
    private int repetitions;
    private Boolean strategy; // false->orizzontale 5 e repetitions=2 , true -> orizzontale 3 = 4
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckRowDifferent(int romanNumber, int playerNumber, int repetitions, Boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) throws Exception{
        int flag = 0;
        int rep = 0;
        int counterDiffTypes;

        for (int i=0; i < bookshelf.getHeight(); i++){
            counterDiffTypes = 0;
            for (int j=1; j < bookshelf.getLength(); j++){
                flag = 0;
                for(int k =0; k<j; k++){
                    if(bookshelf.getItem(i,j).getType().equals(
                            bookshelf.getItem(i, k).getType())) {
                        flag = 1;
                    }
                }
                if(flag == 0){
                    counterDiffTypes++;
                }
            }

            if(counterDiffTypes <= 3&& counterDiffTypes > 0 && strategy ){
                rep ++;
            }else if(counterDiffTypes == 5 && !strategy){
                rep ++;
            }
        }

        if(rep == repetitions){
            return stack.pop();
        }else{
            return null;
        }
    }
}
