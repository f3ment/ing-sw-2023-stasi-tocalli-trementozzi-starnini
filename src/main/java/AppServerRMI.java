import distributed.Server;
import distributed.rmi.ServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();

        //registro della propria macchina
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("server", server);
    }
}
