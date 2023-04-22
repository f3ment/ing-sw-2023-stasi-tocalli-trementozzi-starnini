package model;

import java.io.Serializable;

public class Token implements Serializable {
    final private int score;

    public Token(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

}
