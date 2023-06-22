package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckEqualsDiagonal extends CommonGoal{

    /**
     * Constructor of the class CheckEqualsDiagonal that extends CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for crating the stack of the tokens
     */
    public CheckEqualsDiagonal(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    /**
     * Method that returns the description of the goal
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return "Five tiles of the same type forming a diagonal";
    }

    /**
     * Method that returns the source of the image of the goal
     * @return path of the image of the goal
     */
    public String getSource(){
        return "11.jpg";
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks if the bookshelf has a diagonal of five tiles of the same type.
     * There are four possible 'start' for the diagonal:
     * (0,0), (1,0) that continue in south-east direction
     * (0,4), (1,4) that continue in south-west direction
     * Example of diagonal from 0,0 :
     *                 0 1 2 3 4
     *            0   |X| | | | |
     *            1   | |X| | | |
     *            2   | | |X| | |
     *            3   | | | |X| |
     *            4   | | | | |X|
     *            5   | | | | | |
     * Example of diagonal from 1,0 :
     *                  0 1 2 3 4
     *            0   | | | | | |
     *            1   |X| | | | |
     *            2   | |X| | | |
     *            3   | | |X| | |
     *            4   | | | |X| |
     *            5   | | | | |X|
     *
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        if (bookshelf == null) return null;
        if(     toSudEastDiag(bookshelf, 0, 0) ||
                toSudEastDiag(bookshelf, 1, 0) ||
                toSudWestDiag(bookshelf, 0, 4) ||
                toSudWestDiag(bookshelf, 1, 4))
            return getStack().pop();
    else return null;
    }

    /**
     * Method that checks if there is a diagonal of five tiles of the same type starting from the position i,j.
     * The diagonal is in south-east direction.
     * @param bookshelf which is the bookshelf to check
     * @param i which is the row of the starting position of the diagonal
     * @param j which is the column of the starting position of the diagonal
     * @return true if there is a diagonal of five tiles of the same type starting from the position i,j, false otherwise
     */
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

    /**
     * Method that checks if there is a diagonal of five tiles of the same type starting from the position i,j.
     * The diagonal is in south-west direction.
      * @param bookshelf which is the bookshelf to check
     * @param i which is the row of the starting position of the diagonal
     * @param j which is the column of the starting position of the diagonal
     * @return true if there is a diagonal of five tiles of the same type starting from the position i,j, false otherwise
     */
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
