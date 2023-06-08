package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckEqualsDiagonal extends CommonGoal{


    public CheckEqualsDiagonal(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    @Override
    public String toString() {
        String str = new String("Five tiles of the same type forming a diagonal");
        return str;
    }
    public String getSource(){
        return "11.jpg";
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        if (bookshelf == null) return null;
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
        if(     toSudEastDiag(bookshelf, 0, 0) ||
                toSudEastDiag(bookshelf, 1, 0) ||
                toSudWestDiag(bookshelf, 0, 4) ||
                toSudWestDiag(bookshelf, 1, 4))
            return getStack().pop();
    else return null;
    }

    private boolean toSudEastDiag (Bookshelf bookshelf, int i, int j){
        for(int k=0; k<bookshelf.getLength(); k++){
            try{
                if (!(bookshelf.getItem(i, j).getType().equals(bookshelf.getItem( i + k, j+ k).getType()))) {
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;

       }

    private Boolean toSudWestDiag (Bookshelf bookshelf, int i, int j)  {
        for (int k = 0; k < bookshelf.getLength(); k++) {
            try{
                if (!(bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + k, j - k).getType()))) {
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
