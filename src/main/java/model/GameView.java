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

    public TablePosition getCurrentPosition() {
        return model.getCurrentPosition();
    }

    public Board getBoard() {
        return model.getBoard();
    }

    public List<Bookshelf> getListBookshelf(){
        return model.getListBookshelf();
    }

    public Bookshelf getCurrentBookshelf(){
        return model.getCurrentBookshelf();
    }

    public ArrayList<ItemTiles> getPickedCards(){
        return model.getPickedCards();
    }

    @Override
    public void update(Game o, Enum arg, int columnNumber, ArrayList coords) {
        setChanged();
        notifyObservers((Event) arg,null,null);
    }
}

