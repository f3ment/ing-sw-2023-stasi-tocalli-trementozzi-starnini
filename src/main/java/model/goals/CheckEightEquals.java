package model.goals;

import model.Bookshelf;
import model.ScoringToken;
import model.Stack;
import model.Type;

import java.util.HashMap;
import java.util.Map;

public class CheckEightEquals extends CommonGoal{

    private int romanNumber;
    private Boolean completed;
    private Stack stack;

    public CheckEightEquals(){
        super();
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
        for(i=0; i < bookshelf.getLength(); i++){
            for(j=0; j < bookshelf.getHeight(); j++){
                Counter.put(bookshelf.getItem(i,j).getType() , Counter.get(bookshelf.getItem(i,j).getType())+1);
                for(Type key : Counter.keySet()){
                    if(Counter.get(key)>=8) return stack.pop();
                }
            }
        }
        return null;
    }
}
