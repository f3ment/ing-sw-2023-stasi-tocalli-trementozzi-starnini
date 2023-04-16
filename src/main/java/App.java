import distributed.Server;
import distributed.rmi.ClientImpl;
import distributed.rmi.ServerImpl;

import java.rmi.RemoteException;


public class App {
    //tutto locale
    public static void main( String[] args ) throws RemoteException {
        Server server = new ServerImpl();
        ClientImpl client = new ClientImpl(server);
        client.run();
    }
}
