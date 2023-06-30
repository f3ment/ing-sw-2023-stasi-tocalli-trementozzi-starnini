package model.goals;
import model.Bookshelf;
import model.ScoringToken;

public class CheckSquare extends CommonGoal{

    /**
     * Constructor of the class CheckSquare that extends CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for creating the stack of the tokens
     */
    public CheckSquare(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    /**
     * Method that returns the description of the goal
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square.";
    }

    /**
     * Method that returns the source of the image of the goal
     * @return path of the image of the goal
     */
    public String getSource(){
        return "1.jpg";
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks if there are two groups of four tiles of the same type in a 2x2 square.
     * The batrix is used to avoid to check the same tile more than once and set a cell to true if it is part of a square.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf){
        if (bookshelf == null) return null;
        boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
        int found=0;

        for(int i=0; i< bookshelf.getHeight()-1; i++) {
            for (int j = 0; j < bookshelf.getLength()-1; j++) {
                if(batrix[i][j]) {continue;}
                try{
                    if (bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i, j + 1).getType()) &&
                            bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 1, j).getType()) &&
                            bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 1, j + 1).getType())) {
                        if (!(batrix[i][j] || batrix[i][j + 1] || batrix[i + 1][j] || batrix[i + 1][j + 1])) {
                            batrix[i][j] = true;
                            batrix[i][j + 1] = true;
                            batrix[i + 1][j] = true;
                            batrix[i + 1][j + 1] = true;
                            found++;
                            if (found == 2){
                                return getStack().pop();
                            }

                        }
                    }
                }catch(Exception e){
                    continue;
                }

            }
        }
        return null;
    }

}

