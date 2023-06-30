package view;


import model.Message;
import utils.Event;
import utils.Observable;

/**
 * View is an abstract class that represents the view of the MVC pattern.
 * The GraphicalUI and textualUI classes extend this class respectively to
 * represent the graphical and textual user interface
 */
public abstract class View extends Observable<Event>{

    /**
     * This method update the textual view when it receives a message from the server.
     * It handles all the possible events that can be received and notify the model
     * when a user interact and modify the view.
     * @param message is the message received from the server.
     */
    public abstract void update(Message message);

    /**
     * This is the run method that is called when starting the View.
     */
    public abstract void run();

    /**
     * This method is used to shut down the client instance when  a match is finished
     */
    public abstract void close();

}
