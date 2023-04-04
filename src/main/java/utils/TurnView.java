package utils;

import utils.Observable;
import utils.Observer;
public class TurnView extends Observable<Turn.Event> implements Observer<Turn, Turn.Event> {    private final Turn model;
    public TurnView(Turn model) {
        if (model == null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
        model.addObserver(this);
    }

    public Turn.Event getPlayerEvent(){
        return model.getPlayerEvent();
    }

}
