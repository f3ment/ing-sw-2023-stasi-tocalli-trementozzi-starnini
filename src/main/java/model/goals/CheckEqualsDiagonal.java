package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;

public class CheckEqualsDiagonal extends CommonGoal{
    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckEqualsDiagonal(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) throws Exception {
        // ho 4 possibili 'start' per la diagonale:
        // (0,0), (1,0) che proseguono in direzione sud-est
        // (0,4), (1,4) che proseguono in direzione sud-ovest
        // Esempio diagonale da 0,0
        /*
                0 1 2 3 4
           0   |X| | | | |
           1   | |X| | | |
           2   | | |X| | |
           3   | | | |X| |
           4   | | | | |X|
           5   | | | | | |
        */
        if(toSudEastDiag(bookshelf, 0, 0) ||
                toSudEastDiag(bookshelf, 1, 0) ||
                toSudWestDiag(bookshelf, 0, 4) ||
                toSudWestDiag(bookshelf, 1, 4))
        { return stack.pop(); } else return null;
    }

    private boolean toSudEastDiag (Bookshelf bookshelf, int i, int j) throws Exception{
        int flag = 0;
        for(int k=0; k<5; k++){
            if(bookshelf.getItem(i+k,j-k) == null) throw new Exception();
            if(!(bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(i+k, j+k).getType()))){
                flag++;
            }
        }

        if(flag == 0) return true;
        else return false;
    }

    private Boolean toSudWestDiag (Bookshelf bookshelf, int i, int j) throws Exception {
        int flag = 0;
        for (int k = 0; k < 5; k++) {
            if (bookshelf.getItem(i + k, j + k) == null) throw new Exception();
            if (!(bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + k, j - k).getType()))) {
                flag++;
            }
        }
        if(flag == 0) return true;
        else return false;
    }
}
