package model.goals;
import model.Type;

public class Triplet {
    private Type type;
    private int X;
    private int Y;

    public Triplet(Type type , int x, int y){
        this.type = type;
        this.X = x;
        this.Y = y;
    }

    public Type getType() {
        return type;
    }
    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }
}
