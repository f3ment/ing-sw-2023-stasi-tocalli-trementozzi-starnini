package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckRowDifferent extends CommonGoal{
    private int repetitions;
    private Boolean strategy; // false->orizzontale 5 e repetitions=2 , true -> orizzontale 3 diff

    public CheckRowDifferent(int romanNumber, int playerNumber, int repetitions, Boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        int flag = 0;
        int rep = 0;
        int counterDiffTypes;
        int i, j;

        for ( i=0; i < bookshelf.getHeight(); i++){
            counterDiffTypes = 1;
            for (j=1; j < bookshelf.getLength(); j++){
                flag = 0;
                try{
                    for (int k = 0; k < j; k++) {
                        if (bookshelf.getItem(i, j).getType().equals(
                                bookshelf.getItem(i, k).getType())) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        counterDiffTypes++;
                    }
                }catch (Exception e){
                    break;
                }
            }
            if(counterDiffTypes <= 3 && strategy && j == bookshelf.getLength() ){
                rep ++;
            }else if(counterDiffTypes == 5 && !strategy){
                rep ++;
            }

        }

        if(rep >= repetitions){
            return getStack().pop();
        }else{
            return null;
        }
    }
}
