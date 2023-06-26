package distributed;

import model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Server interface
 * This interface is used to define the methods that the server will use
 */
public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client Client object
     * @throws RemoteException RemoteException
     */
    void register(Client client) throws RemoteException;

    /**
     * update the stream channel with a new message to the server
     * @param client Client object
     * @param message Message object
     * @throws RemoteException RemoteException
     */
    void update(Client client, Message message) throws RemoteException;
}

