package distributed;

import model.views.GameView;
import utils.Event;
import view.Color;
import view.TextualUI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

//unicast remote object serve a comunnicare a rmi che tutte le istanze
//della classe sono esportate , sono raggiungibili tramite invocazioni remote.
public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {

    TextualUI view = new TextualUI();

    public ClientImpl(Server server) throws RemoteException {
        super();
        try {
            initialize(server);
        } catch (RemoteException e){
            System.err.println(Color.RED_BOLD + "Server unreachable!");
            System.err.println(Color.RED + "Client is unable to establish a connection to the server, either because the server is offline or there is an issue with the network connection.\n\n");
        }
    }

    public ClientImpl(Server server, int port) throws RemoteException {
        super(port);
        initialize(server);
    }

    public ClientImpl(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }

    private void initialize(Server server) throws RemoteException{
        server.register(this);
        view.addObserver((o, arg, columnNumber, coords , username)-> {
            try {
                server.update(this, (Event) arg, columnNumber, coords , username);
            } catch (RemoteException e) {
                System.err.println("Error while updating server : " + e.getMessage() + ". Skipping the update...");
            }
        });
    }

    @Override
    public void update(GameView o, Event arg) {
        view.update(o, arg);
    }

    @Override
    public void run() {
        view.run();
    }
}
