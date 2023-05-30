package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckRowDifferent extends CommonGoal{
    private int repetitions;
    private Boolean strategy; // false->orizzontale 5 e repetitions=2 , true -> orizzontale 3 diff

    public CheckRowDifferent(int romanNumber, int playerNumber, int repetitions, Boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    public String getSource(){
        if(strategy)
            return "7.jpg";
        else
            return "2.jpg";
    }

    @Override
    public String toString() {
        String descr = null;
        if(strategy){
            descr = new String("Four lines each formed by 5 tiles of maximum three different types. One lime can show the same or a different combination of another line.");
        }else{
            descr =new String("Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.");
        }
        return descr;
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
