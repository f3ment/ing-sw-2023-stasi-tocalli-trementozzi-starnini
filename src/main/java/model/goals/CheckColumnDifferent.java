package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckColumnDifferent extends CommonGoal{
    private final int repetitions;
    private final boolean strategy; //false -> 3 max different for 3 columns , true -> vertical 2


    public CheckColumnDifferent(int romanNumber, int playerNumber,
                                int repetitions, boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        if(strategy){
            return "Two columns each formed by 6 different types. One column can show the same or a different combination of another column";
        }else{
            return "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column";
        }
    }

    public String getSource(){
        if(strategy){
            return "2.jpg";
        }else
            return "5.jpg";
    }



    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        int flag;
        int rep = 0;
        int counterDiffTypes;
        int i,j;

        for( j=0; j< bookshelf.getLength(); j++){
            counterDiffTypes = 1;
            for( i=1; i < bookshelf.getHeight(); i++){

                flag = 0;
                try {
                    for(int k = 0; k < i; k++){
                        if (bookshelf.getItem(i, j).getType().equals(
                                bookshelf.getItem(k, j).getType())) {
                            flag = 1;
                        }
                    }
                    if(flag == 0){
                        counterDiffTypes++;
                    }
                }catch (Exception e){
                    break;
                }
            }
            if(counterDiffTypes <= 3 && !strategy && i == bookshelf.getHeight()){
                rep++;
            }else if(counterDiffTypes == 6 && strategy){
                rep++;
            }

        }

        if(rep >= repetitions){
            return getStack().pop();
        }
        else return null;
    }
}
