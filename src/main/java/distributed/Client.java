package distributed;


import model.Message;
import model.views.GameView;
//import model.Message;
import utils.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(Message message) throws RemoteException;
}
