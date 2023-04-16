package distributed.local;

import controller.GameController;
import distributed.Client;
import distributed.Server;
import model.Game;
import model.GameView;
import utils.Event;

import java.io.IOException;
import java.util.ArrayList;

public class ServerImpl implements Server {

    private GameController controller;
    private Game model;


    //ci permette di acquisire un nuovo client
    // damiani fa un 1to1 client server e model, cioè ad ogni client è associato un nuovo model e un nuovo controller
    // la mia idea è di usare la lobby prima del model, il client si collega ad un server e con la funzione
    // register si collega alla lobby
    // todo metodo da rifare
    @Override
    public void register(Client client) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Michi");
        names.add("giovanni");
        try {
            this.model = new Game(names);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.model.addObserver((o,arg, columnNumber,coords) -> client.update(new GameView(model), (Event) arg, columnNumber, coords));
        this.controller = new GameController(model);
    }

    @Override
    public void update(Client client, Event event, Integer columnNumber, ArrayList coords) {
        this.controller.update(client,event,columnNumber, coords);
    }


}
