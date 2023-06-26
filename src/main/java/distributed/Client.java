package distributed;


import model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Client interface for RMI and socket communication
 */
public interface Client extends Remote {
    /**
     * Method to update the client with a new message
     * @param message message to be sent
     * @throws RemoteException if there is a problem with the remote object
     */
    void update(Message message) throws RemoteException;
}
