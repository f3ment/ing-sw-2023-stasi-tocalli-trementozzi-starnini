package model;

import model.goals.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to generate the 12 common goals for the game.
 */
public class CommonGoalsGenerator {
    private final CommonGoal first, second;

    /**
     * This constructor generates the 12 common goals and randomly chooses 2 of them.
     * @param playerNumber the number of the player
     */
    public CommonGoalsGenerator(int playerNumber){
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add( new CheckAngles(0,playerNumber));
        commonGoals.add( new CheckColumnDifferent(0,playerNumber, 2, true));
        commonGoals.add( new CheckColumnDifferent(0,playerNumber, 3, false));
        commonGoals.add( new CheckColumnLength(0,playerNumber));
        commonGoals.add( new CheckCross(0,playerNumber));
        commonGoals.add( new CheckEightEquals(0,playerNumber));
        commonGoals.add( new CheckEqualsDiagonal(0,playerNumber));
        commonGoals.add( new CheckGroupsSameType(0,playerNumber, 2, 6));
        commonGoals.add( new CheckGroupsSameType(0,playerNumber, 4, 4));
        commonGoals.add( new CheckRowDifferent(0,playerNumber, 2, false));
        commonGoals.add( new CheckRowDifferent(0,playerNumber, 4, true));
        commonGoals.add( new CheckSquare(0,playerNumber));

        Random randomInt = new Random();

        int index = randomInt.nextInt(commonGoals.size());
        this.first = commonGoals.get(index);
        commonGoals.remove(index);
        this.first.setRomanNumber(1);

        index = randomInt.nextInt(commonGoals.size());
        this.second = commonGoals.get(index);
        this.second.setRomanNumber(2);

    }

    /**
     * @return the first common goal extracted
     */
    public CommonGoal getFirst() {
        return first;
    }

    /**
     * @return the second common goal extracted
     */
    public CommonGoal getSecond() {
        return second;
    }
}
