package distributed.local;

import distributed.Client;
import distributed.Server;
import model.GameView;
import utils.Event;
import view.TextualUI;

import java.util.ArrayList;

public class ClientImpl implements Client, Runnable {

    TextualUI view = new TextualUI();

    public ClientImpl(Server server){
        server.register(this);
        view.addObserver((o, arg, columnNumber, coords)-> server.update(this, (Event) arg, columnNumber, coords));
    }


    @Override
    public void update(GameView o, Event arg, Integer columnNumber, ArrayList coords) {
        view.update(o, arg, columnNumber, coords);
    }

    @Override
    public void run() {
        view.run();
    }
}
