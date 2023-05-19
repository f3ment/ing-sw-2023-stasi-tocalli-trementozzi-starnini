import distributed.Server;
import distributed.ServerImpl;
import distributed.socket.middleware.ClientSkeleton;
import view.Color;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Server server;


    protected AppServer() throws RemoteException {
    }


    public static void main(String[] args) throws RemoteException {
        server = new ServerImpl();

        Thread rmiThread = new Thread() {
            @Override
            public void run() {
                try {
                    startRMI();
                } catch (RemoteException e) {
                    System.err.println("Cannot start RMI. This protocol will be disabled.");
                }
            }
        };

        rmiThread.start();

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

    private static void startRMI() throws RemoteException {
        System.setProperty("java.security.policy","./src/main/resources/tmp/test.policy");
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("server", server);
    }
}
