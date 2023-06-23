package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckGroupsSameType extends CommonGoal{
        private final int repetitions;
        private final int groupLength; //true -> 4 , false -> 2

    /**
     * Constructor of the class CheckGroupsSameType that initialize the super class CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for creating the stack of the tokens
     * @param groupLength which is the length of the group and the number of tiles of the same type for each group
     * @param repetitions which is the number of groups which have to satisfy the goal
     */
    public CheckGroupsSameType(int romanNumber, int numberPlayers, int groupLength, int repetitions){
        super(romanNumber, numberPlayers);
        this.groupLength = groupLength;
        this.repetitions = repetitions;
    }

    /**
     * Method that returns the path of the image of the goal based on the group length
     * @return path of the image of the goal
     */
    public String getSource(){
        if(groupLength==4)
            return "3.jpg";
        else if(groupLength==2)
            return "4.jpg";
        return null;
    }

    /**
     * Method that returns the description of the goal based on the group length
     * @return description of the goal
     */
    @Override
    public String toString() {
        if(groupLength==4){
             return  "Four groups each containing at least 4 tiles of the same type. The tiles of one group can be different from those of another group.";
        }else if (groupLength == 2){
             return "Six groups each containing at least 2 tiles of the same type. The tiles of one group can be different from those of another group.";
        }else{
            return null;
        }
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks for each column if it has the number of repetitions of different types of tiles and if so, it increments the counter.
     * If the counter is greater or equal to the number of repetitions, the goal is satisfied.
     * Then it checks for each row if it has the number of repetitions of different types of tiles and if so, it increments the counter.
     * If the counter is greater or equal to the number of repetitions, the goal is satisfied.
     * In both cases, the algorithm uses a matrix of boolean to check if a tile has already been counted as part of a group that satisfies the goal.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf)  {
        if (bookshelf == null) return null;
        boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
        int rep = 0;
        boolean flag;
        for (int i = 0; i < bookshelf.getHeight(); i++) {
            for (int j = 0; j < bookshelf.getLength(); j++) {
                flag = false;
                try{
                    if (j <= bookshelf.getLength() - groupLength) {
                        for (int k = 1; k < groupLength; k++) {
                            if (batrix[i][j] || batrix[i][j + k]) {
                                flag = true;
                                break;
                            } else {
                                if (!bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i, j + k).getType())) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            rep++;
                            for (int k = 0; k < groupLength; k++) {
                                batrix[i][j + k] = true;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }catch (Exception e){
                    continue;
                }
                if (rep == repetitions) {
                    return getStack().pop();
                }
            }
        }


        for (int i = 0; i < bookshelf.getHeight(); i++) {
            for (int j = 0; j < bookshelf.getLength(); j++) {
                flag = false;
                try {

                    if (i <= bookshelf.getHeight() - groupLength) {
                        for (int k = 1; k < groupLength; k++) {
                            if (batrix[i][j] || batrix[i + k][j]) {
                                flag = true;
                                break;
                            } else {
                                if (!bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + k, j).getType())) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            rep++;
                            for (int k = 0; k < groupLength; k++) {
                                batrix[i + k][j] = true;
                            }
                        } else {
                            flag = false;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
                if (rep == repetitions) {
                    return getStack().pop();
                }
            }
        }
        return null;
    }
}
