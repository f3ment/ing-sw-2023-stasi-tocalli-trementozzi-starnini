package model;

public class Token {
    private int score;
    private TablePosition position;

    public Token(int score){
        this.score = score;
        this.position = null;
    }

    public int getScore(){
        return this.score;
    }

    public TablePosition getPosition(){
        return this.position;
    }

    public void setPosition(TablePosition position){
        this.position = position;
    }
}
