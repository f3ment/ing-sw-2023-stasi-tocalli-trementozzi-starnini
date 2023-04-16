package distributed;


import model.GameView;
import utils.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client extends Remote {
    void update(GameView o, Event arg, Integer columnNumber, ArrayList coords) throws RemoteException;
}
