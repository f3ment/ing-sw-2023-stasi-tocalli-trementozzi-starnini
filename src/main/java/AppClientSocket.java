import distributed.ClientImpl;
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
                while(true){
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Error while receiving message from server : " + e.getMessage());
                        try {
                            serverStub.close();
                        } catch (RemoteException ex) {
                            System.err.println("Cannot close connection with server. Halting...");
                        }
                        System.exit(1);
                    }
                }
            }
        }.start();
        client.run();
    }
}
