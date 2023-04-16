package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.board.Board;
import model.goals.CommonGoal;
import model.goals.PersonalGoal;
import utils.Event;
import utils.Observable;
import utils.Observer;
import view.TextualUI;

import java.awt.print.Book;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameView extends Observable<Event> implements Observer<Game,Event> {
    private final Game model;

    public GameView(Game model) {
        if (model == null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
        model.addObserver(this);
    }
    public Box[][] getBoard() {
        Board board=model.getBoard();
        Box[][] viewBoard= new Box[board.getMaxHeight()][board.getMaxLength()];
        for(int i=0;i< board.getMaxHeight();i++){
            for(int j=0;j< board.getMaxLength();j++){
                try{
                    viewBoard[i][j]= new Box(board.getBox(i, j).getValid(), board.getBox(i,j).getItemContained());
                }catch (Exception e){
                    viewBoard[i][j]=null;
                }
            }
        }
        return viewBoard;
    }

    public List<ItemTiles[][]> getListBookshelf(){
        List<Bookshelf> bookshelfList= model.getListBookshelf();
        List<ItemTiles[][]> viewBookshelfList= new ArrayList<ItemTiles[][]>();
        for(int i=0;i<bookshelfList.size();i++){
            viewBookshelfList.add(new ItemTiles[bookshelfList.get(i).getHeight()][bookshelfList.get(i).getLength()]);
            for(int k=0;k< bookshelfList.get(i).getHeight();k++){
                for(int j=0;j<bookshelfList.get(i).getLength();j++){
                    try{
                        viewBookshelfList.get(i)[k][j]= new ItemTiles(bookshelfList.get(i).getItem(k,j).getType(), bookshelfList.get(i).getItem(k,j).getId());
                    }catch (Exception e){
                        viewBookshelfList.get(i)[k][j]=null;
                    }
                }
            }
        }
        return viewBookshelfList;
    }

    public ItemTiles[][] getCurrentBookshelf(){
        Bookshelf bookshelf=model.getCurrentBookshelf();
        ItemTiles[][] viewCurrentBookshelf= new ItemTiles[bookshelf.getHeight()][bookshelf.getLength()];
        for(int i=0;i< bookshelf.getHeight();i++){
            for(int j=0;j< bookshelf.getLength();j++){
                try{
                    viewCurrentBookshelf[i][j]= new ItemTiles(bookshelf.getItem(i,j).getType(), bookshelf.getItem(i,j).getId());
                }catch (Exception e){
                    viewCurrentBookshelf[i][j]=null;
                }
            }
        }
        return viewCurrentBookshelf;
    }

    public ArrayList<ItemTiles> getPickedCards(){
        ArrayList<ItemTiles> hand= model.getPickedCards();
        ArrayList<ItemTiles> viewHand= new ArrayList<ItemTiles>();
        for(int i=0;i<hand.size();i++){
            viewHand.add(hand.get(i));
        }
        return viewHand;
    }

    public List<ScoringToken> getFirstCommonGoal(){
        Stack<ScoringToken> stack=model.getFirstCommonGoal().getStack();
        List<ScoringToken> viewStack= new ArrayList<ScoringToken>();
        for(int i=0;i<stack.size();i++){
            viewStack.add(new ScoringToken(stack.get(i).getScore(),stack.get(i).getNumber()));
        }
        return viewStack;
    }

    public List<ScoringToken> getSecondCommonGoal(){
        Stack<ScoringToken> stack=model.getSecondCommonGoal().getStack();
        List<ScoringToken> viewStack= new ArrayList<ScoringToken>();
        for(int i=0;i<stack.size();i++){
            viewStack.add(new ScoringToken(stack.get(i).getScore(),stack.get(i).getNumber()));
        }
        return viewStack;
    }

    public int getHeightBookshelf(){
        return model.getCurrentBookshelf().getHeight();
    }

    public int getLenghtBookshelf(){
        return model.getCurrentBookshelf().getLength();
    }

    public int getHeightBoard(){
        return model.getBoard().getMaxHeight();
    }

    public int getLenghtBoard(){
        return model.getBoard().getMaxLength();
    }

    @Override
    public void update(Game o, Enum arg, Integer columnNumber, ArrayList coords) {
        setChanged();
        notifyObservers((Event) arg,null,null);
    }

    public Player getCurrentPlayer(){
        Player res = new Player(model.getCurrentPosition(), model.getCurrentPosition().getPlayer().getUsername());
        return res;
    }

    public boolean getEndGame(){
        return model.getEndGame();
    }

    public String getFirstPlayer(){
        return new String(model.getFirstPlayer().getUsername());
    }

    public String getWinner(){
        return new String(model.getWinner());
    }
}

