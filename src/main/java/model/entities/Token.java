package model.entities;

public abstract class Token {
    private int score;
    private TablePosition position;

    public Token(int score){
<<<<<<< HEAD
        this.score=score;
        this.position=null;
=======
        this.score = score;
        this.position = null;
>>>>>>> 19fb0f635e50504eaeba6a20cf93f5c12fd225fd
    }

    public int getScore(){
        return this.score;
    }

    public TablePosition getPosition(){
        return this.position;
    }

    public void setPosition(TablePosition position){
<<<<<<< HEAD
        this.position=position;
=======
        this.position = position;
>>>>>>> 19fb0f635e50504eaeba6a20cf93f5c12fd225fd
    }
}
