package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;
import model.Type;

import java.util.HashMap;

public class CheckEightEquals extends CommonGoal{
    /**
     * Constructor of the class CheckEightEquals that extends CommonGoal
     * @param romanNumber which is the number of the goal
     * @param numberPlayers which is the number of the players for creating the stack of the tokens
     */
    public CheckEightEquals(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    /**
     * Method that returns the description of the goal
     * @return the description of the goal
     */
    @Override
    public String toString() {
        return "Eight tiles of the same type, There's no restriction about the position of these tiles.";
    }

    /**
     * Method that returns the source of the image of the goal
     * @return path of the image of the goal
     */
    public String getSource(){
        return "9.jpg";
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * If the goal is satisfied, the top token of the stack of the goal is returned, otherwise null is returned.
     * The algorithm checks if there are eight tiles of the same type in the bookshelf.
     * @param bookshelf which is the bookshelf to check
     * @return the top token of the current stack of the goal if the goal is satisfied, null otherwise
     */
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        if (bookshelf == null) return null;
        HashMap<Type,Integer> Counter = new HashMap<>();
        Counter.put(Type.CATS , 0);
        Counter.put(Type.BOOKS , 0);
        Counter.put(Type.FRAMES , 0);
        Counter.put(Type.PLANTS , 0);
        Counter.put(Type.GAMES , 0);
        Counter.put(Type.TROPHIES , 0);
        int i , j;
        for (i = 0; i < bookshelf.getHeight(); i++) {
            for (j = 0; j < bookshelf.getLength(); j++) {
                try {
                    Type type = bookshelf.getItem(i, j).getType();
                    Counter.put(type, Counter.get(type) + 1);
                    for (Type key : Counter.keySet()) {
                        if (Counter.get(key) >= 8){
                            return getStack().pop();
                        }
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
        return null;
    }
}
