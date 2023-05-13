package distributed;

import model.Message;
import utils.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote {
    //modo per far connettere un client ad un server
    void register(Client client) throws RemoteException;

    //posso chiamare update passando argomenti
    void update(Client client, Event event, Integer columnNumber, ArrayList coords , String UserName) throws RemoteException;

    //void update(Message message) throws RemoteException;
}

