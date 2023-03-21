package model.goals;

import java.util.Map;

import com.sun.tools.javac.util.Pair;
import model.Type;

import java.security.GeneralSecurityException;

//todo implement windows
public class PersonalGoal {
    private Map<Type , Pair<int ,int>>;
    private int done;




    public PersonalGoal(int index){
        /*todo
            if a goal is obtained, done will be incremented by one, until 6
            in this way at the end of the game we know how many goals has been achieved.
        */
        done = 0;
    }

    public int getDone(){
        return this.done;
    }

    public void incrementDone() {
        this.done ++;
    }



    //creare una exception se done è maggiore di 6 o minore di 0
    public int getScore() throws IndexOutOfBoundsException {
        if(this.done < 0 || this.done >6) throw new IndexOutOfBoundsException();
        switch (this.done) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 6;
            case 5:
                return 9;
            case 6:
                return 12;
        }
        return 0;
    }
}
