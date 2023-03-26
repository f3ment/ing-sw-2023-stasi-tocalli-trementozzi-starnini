package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckCross extends CommonGoal{
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckCross(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }


    // null = false
    @Override
    public ScoringToken validate(Bookshelf bookshelf)  {

        for(int i=0; i< bookshelf.getHeight(); i++){
            for(int j=0; j< bookshelf.getLength(); j++){
                if(i<bookshelf.getHeight()-2 && j<bookshelf.getLength()-2){
                    if(bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i,j+2).getType()) &&
                            bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i+1,j+1).getType())&&
                            bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i+2,j).getType())&&
                            bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i+2,j+2).getType())){
                        return stack.pop();  // Esiste una <X> dello stesso tipo
                    }
                }else continue;
            }
        }
        return null; // Non esiste una <X> dello stesso tipo
    }
}
