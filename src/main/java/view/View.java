package view;


import model.Message;
import utils.Event;
import utils.Observable;

public abstract class View extends Observable<Event>{

    public abstract void update(Message message);

    public abstract void run();

    public abstract void close();

}
