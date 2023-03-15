package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckSquare extends CommonGoal{

    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckSquare(){
        super();
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) {

        for(int i=0; i< bookshelf.getHeight(); i++) {
            for (int j = 0; j < bookshelf.getLength(); j++) {
                //se sono nella sotto-matrice length-2 x height-2
                if(i<bookshelf.getHeight()-1 && j<bookshelf.getLength()-1){
                    if(bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i,j+1).getType()) &&
                            bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i+1,j).getType()) &&
                            bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i+1,j+1).getType())){
                        for(int r=i+2; r< bookshelf.getHeight()-1; r++){
                            for(int c=j+2; c<bookshelf.getLength()-1; c++){
                                if(bookshelf.getItem(r,c).getType().equals(bookshelf.getItem(r,c+1).getType()) &&
                                        bookshelf.getItem(r,c).getType().equals(bookshelf.getItem(r+1,c).getType()) &&
                                        bookshelf.getItem(r,c).getType().equals(bookshelf.getItem(r+1,c+1).getType())){
                                    return stack.pop();
                                }
                            }
                        }
                    }
                }else continue;
            }
        }
        return null; //check failed (false)
    }
}
