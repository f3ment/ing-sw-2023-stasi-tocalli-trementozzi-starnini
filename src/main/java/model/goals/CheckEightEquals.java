package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;
import model.Type;

import java.util.HashMap;

public class CheckEightEquals extends CommonGoal{
    public CheckEightEquals(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers/*, "Eight tiles of the same type, There's no restriction about the position of these tiles."*/);
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        HashMap<Type,Integer> Counter = new HashMap<Type,Integer>();
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
                        if (Counter.get(key) >= 8) return getStack().pop();
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
        return null;
    }
}
