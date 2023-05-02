package distributed;


import model.GameView;
import utils.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(GameView o, Event arg) throws RemoteException;
}
