package distributed;

import utils.Event;

import java.util.ArrayList;

public interface Server {
    //modo per far connettere un client ad un server
    void register(Client client);

    //posso chiamare update passando argomenti
    void update(Client client, Event event, Integer columnNumber, ArrayList coords);
}
