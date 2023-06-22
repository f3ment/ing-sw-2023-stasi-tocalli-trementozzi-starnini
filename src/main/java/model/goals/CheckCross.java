package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckCross extends CommonGoal{

    /**
     * Constructor of the class CheckCross that extends CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for creating the stack of the tokens
     */
    public CheckCross(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    /**
     * Method that returns the description of the goal
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return  "Five tiles of the same type forming an X";
    }

    /**
     * Method that returns the source of the image of the goal
     * @return path of the image of the goal
     */
    public String getSource(){
        return "10.jpg";
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks if the bookshelf has a cross of five tiles of the same type.
     * For each cell in the bookshelf, the algorithm checks if the cell is in the middle of the cross made of five tiles of the same type.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        try {
            for (int i = 0; i < bookshelf.getHeight(); i++) {
                for (int j = 0; j < bookshelf.getLength(); j++) {
                    if (i < bookshelf.getHeight() - 2 && j < bookshelf.getLength() - 2) {
                        try {
                            if (bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i, j + 2).getType()) &&
                                    bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 1, j + 1).getType()) &&
                                    bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 2, j).getType()) &&
                                    bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 2, j + 2).getType())) {
                                return getStack().pop();  // Esiste una <X> dello stesso tipo
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
            }
            return null; // Non esiste una <X> dello stesso tipo}
        } catch (Exception e) {
            return null;
        }
    }
}