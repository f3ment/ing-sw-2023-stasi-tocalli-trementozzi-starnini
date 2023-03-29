package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;
import model.Type;

import java.util.HashMap;

public class CheckEightEquals extends CommonGoal{
    public CheckEightEquals(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
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
        try{
            for (i = 0; i < bookshelf.getLength(); i++) {
                for (j = 0; j < bookshelf.getHeight(); j++) {

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
        }catch (Exception e){
            return null;
        }
    }
}
