package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckColumnDifferent extends CommonGoal{
    private final int repetitions;
    private final boolean strategy; //false -> 3 max different for 3 columns , true -> vertical 2


    /**
     * Constructor of the class CheckColumnDifferent that extends CommonGoal.
     * @param romanNumber which is the number of the goal
     * @param playerNumber which is the number of the players for creating the stack of the tokens
     * @param repetitions which is the number of columns that must satisfy the goal
     * @param strategy which is the strategy of the goal. If true, the goal is to fill two columns with all different Items Type ; otherwise the goal is to fill three columns
     *                 with at most three different Items Type
     */
    public CheckColumnDifferent(int romanNumber, int playerNumber,
                                int repetitions, boolean strategy){
        super(romanNumber, playerNumber);
        this.repetitions = repetitions;
        this.strategy = strategy;
    }

    /**
     * Method that returns the description of the goal based on the strategy
     * @return the description of the goal
     */
    @Override
    public String toString() {
        if(strategy){
            return "Two columns each formed by 6 different types. One column can show the same or a different combination of another column";
        }else{
            return "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column";
        }
    }

    /**
     * Method that returns the source of the image of the goal based on the strategy
     * @return path of the image of the goal
     */
    public String getSource(){
        if(strategy){
            return "2.jpg";
        }else
            return "5.jpg";
    }


    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks for each column if it has the number of repetitions of different types of tiles and if so, it increments the counter.
     * If the counter is greater or equal to the number of repetitions, the goal is satisfied.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        int flag;
        int rep = 0;
        int counterDiffTypes;
        int i,j;

        for( j=0; j< bookshelf.getLength(); j++){
            counterDiffTypes = 1;
            for( i=1; i < bookshelf.getHeight(); i++){

                flag = 0;
                try {
                    for(int k = 0; k < i; k++){
                        if (bookshelf.getItem(i, j).getType().equals(
                                bookshelf.getItem(k, j).getType())) {
                            flag = 1;
                        }
                    }
                    if(flag == 0){
                        counterDiffTypes++;
                    }
                }catch (Exception e){
                    break;
                }
            }
            if(counterDiffTypes <= 3 && !strategy && i == bookshelf.getHeight()){
                rep++;
            }else if(counterDiffTypes == 6 && strategy){
                rep++;
            }

        }

        if(rep >= repetitions){
            return getStack().pop();
        }
        else return null;
    }
}
