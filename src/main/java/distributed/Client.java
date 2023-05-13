package distributed;


import model.views.GameView;
import model.Message;
import utils.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(GameView o, Event arg) throws RemoteException;

//    void update(Message message) throws RemoteException;
}
