package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckRowDifferent extends CommonGoal{
    private final int repetitions;
    private final Boolean strategy; // false->orizzontale 5 e repetitions=2 , true -> orizzontale 3 diff

    /**
     * Constructor for CheckRowDifferent class that extends CommonGoal class.
     * @param romanNumber which is the number of the goal.
     * @param playerNumber which is the number of the players for creating the stack of the tokens.
     * @param repetitions which is the number of rows that must satisfy the goal.
     * @param strategy which is the strategy of the goal. If true, the goal is to fill four rows with at most three different Items Type ; otherwise the goal is to fill two rows
     *                 with all different Items Type.
     */
    public CheckRowDifferent(int romanNumber, int playerNumber, int repetitions, Boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    /**
     * Method that returns the source of the image of the goal based on the strategy
     * @return path of the image of the goal
     */
    public String getSource(){
        if(strategy)
            return "7.jpg";
        else
            return "6.jpg";
    }

    /**
     * Method that returns the description of the goal based on the strategy
     * @return the description of the goal
     */
    @Override
    public String toString() {
        if(strategy){
            return "Four lines each formed by 5 tiles of maximum three different types. One lime can show the same or a different combination of another line.";
        }else{
            return "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.";
        }
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks for each row if it has the number of repetitions of different types of tiles and if so, it increments the counter.
     * If the counter of rows which respect the goal is greater or equal to the number of repetitions, the goal is satisfied.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        if (bookshelf == null) return null;
        int flag;
        int rep = 0;
        int counterDiffTypes;
        int i, j;

        for ( i=0; i < bookshelf.getHeight(); i++){
            counterDiffTypes = 1;
            for (j=1; j < bookshelf.getLength(); j++){
                flag = 0;
                try{
                    for (int k = 0; k < j; k++) {
                        if (bookshelf.getItem(i, j).getType().equals(
                                bookshelf.getItem(i, k).getType())) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        counterDiffTypes++;
                    }
                }catch (Exception e){
                    break;
                }
            }
            if(counterDiffTypes <= 3 && strategy && j == bookshelf.getLength() ){
                rep ++;
            }else if(counterDiffTypes == 5 && !strategy){
                rep ++;
            }

        }

        if(rep >= repetitions){
            return getStack().pop();
        }else{
            return null;
        }
    }
}
