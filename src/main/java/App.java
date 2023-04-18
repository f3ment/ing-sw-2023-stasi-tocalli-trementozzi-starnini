import distributed.Server;
import distributed.rmi.ClientImpl;
import distributed.rmi.ServerImpl;

import java.rmi.RemoteException;


public class App {
    //tutto locale
    public static void main( String[] args ) throws RemoteException {
        System.out.println(".___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______");
        System.out.println("|   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|");
        System.out.println("|  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__");
        System.out.println("|  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|  ");
        System.out.println("|  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____");
        System.out.println("|__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|");
        System.out.println("");
            Server server = new ServerImpl();
            ClientImpl client = new ClientImpl(server);
            client.run();
    }
}
