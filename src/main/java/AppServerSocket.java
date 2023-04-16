import distributed.Server;
import distributed.rmi.ServerImpl;
import distributed.socket.middleware.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class AppServerSocket {
    public static void main(String[] args) throws RemoteException {
        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while (true){
                try(Socket socket = serverSocket.accept()){
                    ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                    Server server = new ServerImpl();
                    while (true){
                        clientSkeleton.receive(server);
                    }

                }
            }
        }catch (IOException e){
            throw new RemoteException("Error while creating socket : " + e.getMessage());
        }
    }
}
