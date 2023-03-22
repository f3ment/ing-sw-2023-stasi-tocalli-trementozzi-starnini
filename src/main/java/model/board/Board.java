package model.board;

import model.Bag;
import model.Box;
import model.ItemTiles;
import model.Token;

public interface Board {

    public Box getBox(int i, int j) throws IndexOutOfBoundsException;
    public ItemTiles draw(int i, int j);
    public void setToken(Token token);
    public boolean setBox(Bag bag);
    public int getMaxHeight();
    public int getMaxLength();

}
