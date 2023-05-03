import distributed.Server;
import distributed.ServerImpl;
import distributed.socket.middleware.ClientSkeleton;
import view.Color;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServerSocket  extends UnicastRemoteObject {


    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Server server;


    protected AppServerSocket() throws RemoteException {
    }

    public static Server getInstance() throws RemoteException {
        if (server == null) {
        }
        return server;
    }


    public static void main(String[] args) throws RemoteException {

        server = new ServerImpl();
        Thread socketThread = new Thread() {
            @Override
            public void run() {
                try {
                    startSocket();
                } catch (RemoteException e) {
                    System.out.print(Color.RED);
                    System.err.println("Cannot start socket. This protocol will be disabled.");
                    System.out.print(Color.RESET);
                }
            }
        };

        socketThread.start();

        try {
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }




        /*Server server = new ServerImpl();
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
        }*/
    }

    public static void startSocket() throws RemoteException {

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        //istanza di gamesManagerController -> assegnamento;
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }
}
