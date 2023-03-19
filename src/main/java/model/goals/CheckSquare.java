package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckSquare extends CommonGoal{

    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckSquare(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) throws Exception{
        boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
        int rep = 0;

        for(int i=0; i< bookshelf.getHeight(); i++) {
            for (int j = 0; j < bookshelf.getLength(); j++) {
                if(bookshelf.getItem(i,j) == null) throw new Exception();
                //check for square
                if( bookshelf.getItem(i,j).getType().equals(
                        bookshelf.getItem(i,j+1).getType()) &&
                    bookshelf.getItem(i,j).getType().equals(
                            bookshelf.getItem(i+1,j).getType()) &&
                    bookshelf.getItem(i,j).getType().equals(
                            bookshelf.getItem(i+1, j+1).getType())){
                    if(!batrix[i][j] && !batrix[i][j+1] && !batrix[i+1][j] && !batrix[i+1][j+1]){
                        batrix[i][j] = true;
                        batrix[i][j+1] = true;
                        batrix[i+1][j] = true;
                        batrix[i+1][j+1] = true;
                        rep++;
                        if(rep == 2){
                            return stack.pop();
                        }
                    }

                }
            }
        }
        return null;
    }
}
