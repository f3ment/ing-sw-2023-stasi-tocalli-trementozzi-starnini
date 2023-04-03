package model;

import model.goals.*;

import java.util.ArrayList;
import java.util.Random;

//todo test
public class CommonGoalsGenerator {
    private final CommonGoal first, second;
    public CommonGoalsGenerator(int playerNumber){
        ArrayList<CommonGoal> commonGoals = new ArrayList<CommonGoal>();
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

        int index = randomInt.nextInt(commonGoals.size()-1);
        this.first = commonGoals.get(index);
        commonGoals.remove(index);
        this.first.setRomanNumber(1);

        index = randomInt.nextInt(commonGoals.size()-1);
        this.second = commonGoals.get(index);
        this.second.setRomanNumber(2);

    }

    public CommonGoal getFirst() {
        return first;
    }

    public CommonGoal getSecond() {
        return second;
    }
}
