package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckColumnDifferent extends CommonGoal{
    private int repetitions;
    private boolean strategy; //false -> 3 max different for 3 columns , true -> verticale 2


    public CheckColumnDifferent(int romanNumber, int playerNumber,
                                int repetitions, boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        String str = null;
        if(strategy){
            str= new String("Two columns each formed by 6 different types. One column can show the same or a different combination of another column");
        }else if(!strategy){
            str=new String("Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column");
        }
        return str;
    }

    public String getSource(){
        if(strategy){
            return "2.jpg";
        }else
            return "5.jpg";
    }



    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        int flag = 0;
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
