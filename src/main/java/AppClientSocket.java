import distributed.Server;
import distributed.rmi.ClientImpl;
import distributed.socket.middleware.ServerStub;

import java.rmi.RemoteException;

public class AppClientSocket {
    public static void main(String[] args) throws RemoteException {
        ServerStub serverStub = new ServerStub("localhost", 1234);

        ClientImpl client = new ClientImpl(serverStub);

        //serve per eseguire la receive dal server per eventi
        new Thread(){
            @Override
            public void run() {
                try{
                    serverStub.receive(client);
                }catch (RemoteException e){
                    System.err.println("Error while receiving message from server : " + e.getMessage());
                    // todo close socket
                    System.exit(1);
                }
            }
        }.start();
        client.run();
    }
}
