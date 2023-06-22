package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckAngles extends CommonGoal{
    /**
     * Constructor of the class CheckAngles that extends CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for crating the stack of the tokens
     */
    public  CheckAngles(int romanNumber, int numberPlayers){
        super(romanNumber,numberPlayers);
    }

    /**
     * Method that returns the description of the goal
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return "Four tiles of the same type in the four corners of the bookshelf.";
    }

    /**
     * Method that returns the source of the image of the goal
     * @return path of the image of the goal
     */
    public String getSource(){
        return "8.jpg";
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks if the four corners of the bookshelf have the same type of tile.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        try{
            if(bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(0, bookshelf.getLength()-1).getType())  &&
                    bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(bookshelf.getHeight()-1, bookshelf.getLength()-1).getType())  &&
                    bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(bookshelf.getHeight()-1, 0).getType())){
                return getStack().pop();
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }

    }
}
