import distributed.Server;
import distributed.rmi.ServerImpl;
import distributed.socket.middleware.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServerSocket  extends UnicastRemoteObject {


    protected AppServerSocket() throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();
        ExecutorService executorService = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }

                    } catch (IOException e) {
                        System.err.println("Socket failed: " + e.getMessage() + ". Closing connection and waiting for a new one...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        }catch (IOException e){
            throw new RemoteException("Error while creating socket : " + e.getMessage());
        }
    }
}
