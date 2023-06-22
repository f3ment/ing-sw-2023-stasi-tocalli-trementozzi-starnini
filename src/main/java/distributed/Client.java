package distributed;


import model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(Message message) throws RemoteException;
}
